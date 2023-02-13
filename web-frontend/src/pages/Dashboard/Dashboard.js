import { faArrowLeft } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import React, { Fragment, useRef, useState } from "react";
import { Navigate, useNavigate } from "react-router-dom";
import Box from "../../interface/Box/Box";
import Button from "../../interface/Button/Button";
import ListButton from "../../interface/ListButton/ListButton";
import Modal from "../../interface/Modal/Modal";
import FileDetails from "../FileDetails/FileDetails";

import classes from "./Dashboard.module.css";

const Dashboard = (props) => {
    const [selectedFile, setSelectedFile] = useState();
    const [dragActive, setDragActive] = useState(false);
    const fileInputRef = useRef();
    const nameRef = useRef();
    const navigate = useNavigate();

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
        props.planUploadHandler(newPlan).then((newPlanId) =>
            props
                .planSimulateHandler(newPlanId)
                .then((simCaseId) => checkIfDone(simCaseId))
                .catch((error) => console.log(error))
        );
    };

    const checkIfDone = (simId) => {
        props
            .getSimCaseStatusHandler(simId)
            .then((result) => {
                if (result.state === "done") {
                    console.log("done");
                    props
                        .getSimCaseResultHandler(simId)
                        .then((response) => navigate(`/results/${simId}`))
                        .catch((error) => console.log(error));
                } else {
                    console.log("not done");
                    const timer = setTimeout(() => {
                        checkIfDone(simId);
                        clearTimeout(timer);
                    }, 1000);
                }
            })
            .catch((error) => console.log(error));
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
            {!selectedFile && (
                <div className={classes.boxes}>
                    <Box
                        titleText={<p>Last 4 uploaded plans</p>}
                        className={classes.box}
                    >
                        {props.plans.length > 0 &&
                            props.plans.sort((a,b) => new Date(b.createdOn) - new Date(a.createdOn)).slice(0, 4).map((plan) => (
                                <ListButton
                                    key={plan.uuid}
                                    onClick={() => {
                                        navigate(`/plans/${plan.uuid}`);
                                    }}
                                    id={plan.uuid}
                                    name={<b>"{plan.name}"</b>}
                                    createdOn={plan.createdOn}
                                />
                            ))}
                        {!props.plans.length && <p>No plans uploaded yet</p>}
                    </Box>
                    <Box
                        titleText={<p>Last 4 simulations</p>}
                        className={classes.box}
                    >
                        {props.simCases.length > 0 && props.plans.length > 0 &&
                            props.simCases.sort((a,b) => new Date(b.createdOn) - new Date(a.createdOn)).slice(0, 4).map((simCase) => {
                                const plan = props.plans.find(
                                    (plan) => simCase.planId === plan.uuid
                                );
                                return (
                                    <ListButton
                                        key={simCase.id}
                                        onClick={() => {
                                            navigate(`/results/${simCase.id}`);
                                        }}
                                        id={simCase.id}
                                        name={
                                            <Fragment>Simulation of <b>"{plan ? plan.name : "[No Plan]"}"</b></Fragment>
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
                            <Button onClick={handleUploadAndSimulate}>
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
