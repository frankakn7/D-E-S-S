import { faArrowLeft } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import React, { Fragment, useRef, useState } from "react";
import { Navigate, useNavigate } from "react-router-dom";
import Box from "../../interface/Box/Box";
import Button from "../../interface/Button/Button";
import ListButton from "../../interface/ListButton/ListButton";
import Modal from "../../interface/Modal/Modal";
import NumOfSimulations from "../../interface/Modal/NumOfSimulations/NumOfSimulations";
import FileDetails from "../FileDetails/FileDetails";
import PlanButton from "../Plans/PlanButton/PlanButton";

import classes from "./Dashboard.module.css";

/**
 * The main dashboard page. Contains 2 lists of the last 4 simulations and plans. Also handles the JSON upload
 * @param {Object} props all props passed to this object
 * @param {Function} props.planUploadHandler function handling the upload of a new plan
 * @param {Function} props.planSimulateHandler function that handles creating and starting a simulation case for a plan
 * @param {Array<Object>} props.plans a list of all plans
 * @param {Array<Object>} props.simCases a list of all simulation cases
 * @returns 
 */
const Dashboard = (props) => {
    const [selectedFile, setSelectedFile] = useState();
    const [dragActive, setDragActive] = useState(false);
    const fileInputRef = useRef();
    const nameRef = useRef();
    const navigate = useNavigate();

    const [simulate, setSimulate] = useState(false);
    const numOfSimulationsRef = useRef();

    const onFileChange = (event) => {
        const reader = new FileReader();
        const file = event.currentTarget.files[0];
        reader.readAsText(file);
        reader.onload = () => {
            file.data = reader.result;
            setSelectedFile(file);
        };
        reader.onerror = () => {
            throw Error("file could not be read");
        };
    };

    const handleSavePlan = () => {
        const newPlan = {
            name: nameRef.current.value,
            plan: JSON.parse(selectedFile.data),
        };
        props
            .planUploadHandler(newPlan)
            .then((newPlanId) => navigate(`/plans`))
            .catch((error) => console.log(error));
    };

    const handleUploadAndSimulate = () => {
        const newPlan = {
            name: nameRef.current.value,
            plan: JSON.parse(selectedFile.data),
        };
        const numOfSimulations = numOfSimulationsRef.current.value;
        props.planUploadHandler(newPlan).then((newPlanId) =>
            props
                .planSimulateHandler(newPlanId, numOfSimulations)
                .then((simCaseId) =>
                    {
                        props.checkIfDoneHandler(simCaseId);
                        navigate("/simulations");
                    }
                )
                .catch((error) => console.log(error))
        );
    };

    const handleDrag = function (e) {
        e.preventDefault();
        e.stopPropagation();
        if (e.type === "dragenter" || e.type === "dragover") {
            setDragActive(true);
        } else if (e.type === "dragleave") {
            setDragActive(false);
        }
    };

    const handleDrop = function (e) {
        e.preventDefault();
        e.stopPropagation();
        setDragActive(false);
        const event = { currentTarget: { files: e.dataTransfer.files } };
        onFileChange(event);
    };

    return (
        <div className={classes.content}>
            {simulate && (
                <NumOfSimulations
                    onClose={() => setSimulate(false)}
                    onContinue={() => handleUploadAndSimulate()}
                    numOfSimulationsRef={numOfSimulationsRef}
                />
            )}
            {!selectedFile && (
                <div className={classes.boxes}>
                    <Box
                        titleText={<p>Last 4 uploaded plans</p>}
                        className={classes.box}
                    >
                        <div className={classes.planBoxContent}>
                            {props.plans.length > 0 &&
                                props.plans
                                    .sort(
                                        (a, b) =>
                                            new Date(b.createdOn) -
                                            new Date(a.createdOn)
                                    )
                                    .slice(0, 4)
                                    .map((plan) => (
                                        <PlanButton
                                            key={plan.uuid}
                                            name={plan.name}
                                            id={plan.uuid}
                                            createdOn={plan.createdOn}
                                            doubleClick={() => {
                                                navigate(`/plans/${plan.uuid}`);
                                            }}
                                            onDelete={() =>
                                                props.planDeleteHandler(
                                                    plan.uuid
                                                )
                                            }
                                        />
                                    ))}
                            {!props.plans.length && (
                                <p>No plans uploaded yet</p>
                            )}
                        </div>
                    </Box>
                    <Box
                        titleText={<p>Last 4 simulations</p>}
                        className={classes.box}
                    >
                        {props.simCases.length > 0 &&
                            props.plans.length > 0 &&
                            props.simCases
                                .sort(
                                    (a, b) =>
                                        new Date(b.createdOn) -
                                        new Date(a.createdOn)
                                )
                                .slice(0, 4)
                                .map((simCase) => {
                                    const plan = props.plans.find(
                                        (plan) => simCase.planId === plan.uuid
                                    );
                                    return (
                                        <ListButton
                                            key={simCase.id}
                                            onClick={() => {
                                                navigate(
                                                    `/results/${simCase.id}`
                                                );
                                            }}
                                            id={simCase.id}
                                            name={
                                                <Fragment>
                                                    Simulation of{" "}
                                                    <b>
                                                        "
                                                        {plan
                                                            ? plan.name
                                                            : "[No Plan]"}
                                                        "
                                                    </b>
                                                </Fragment>
                                            }
                                            createdOn={simCase.createdOn}
                                        />
                                    );
                                })}
                        {!props.simCases.length && (
                            <p>No simulations run yet</p>
                        )}
                    </Box>
                </div>
            )}
            {!selectedFile && (
                <div>
                    <input
                        type="file"
                        id="jsonFileInput"
                        onChange={onFileChange}
                        accept=".json"
                        style={{ display: "none" }}
                        ref={fileInputRef}
                    />
                    <label htmlFor="jsonFileInput" className={classes.upload}>
                        <div
                            className={classes.dragAndDrop}
                            onDragEnter={handleDrag}
                            onDragLeave={handleDrag}
                            onDragOver={handleDrag}
                            onDrop={handleDrop}
                        >
                            {!dragActive && <p>Drag your JSON file here</p>}
                            {dragActive && <p>+</p>}
                        </div>
                        <Button onClick={() => fileInputRef.current.click()}>
                            Select JSON file
                        </Button>
                    </label>
                </div>
            )}
            {selectedFile && (
                <Button onClick={() => setSelectedFile(null)}>
                    <FontAwesomeIcon icon={faArrowLeft} /> Go Back
                </Button>
            )}
            {selectedFile && (
                <FileDetails
                    file={selectedFile}
                    nameRef={nameRef}
                    buttons={
                        <Fragment>
                            <Button onClick={handleSavePlan}>Save Plan</Button>
                            <Button onClick={() => setSimulate(true)}>
                                Save and Simulate
                            </Button>
                            <div>
                                <input
                                    type="file"
                                    id="jsonFileInput"
                                    onChange={onFileChange}
                                    accept=".json"
                                    style={{ display: "none" }}
                                    ref={fileInputRef}
                                />
                                <label htmlFor="jsonFileInput">
                                    <Button
                                        onClick={() =>
                                            fileInputRef.current.click()
                                        }
                                    >
                                        Select different JSON file
                                    </Button>
                                </label>
                            </div>
                        </Fragment>
                    }
                />
            )}
        </div>
    );
};

export default Dashboard;
