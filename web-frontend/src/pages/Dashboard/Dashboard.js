import React, { useRef, useState } from "react";
import { Navigate, useNavigate } from "react-router-dom";
import Box from "../../interface/Box/Box";
import Button from "../../interface/Button/Button";
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
            console.log("file could not be read");
        };
    };

    const handleSavePlan = () => {
        const newPlan = {
            name: nameRef.current.value,
            plan: JSON.parse(selectedFile.data),
        };
        props
            .planUploadHandler(newPlan)
            .then((newPlanId) => setSelectedFile(null))
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
                    <Box titleText={<p>Hochgeladene Pläne</p>} className={classes.box}>
                        <p>All plans</p>
                    </Box>
                    <Box
                        titleText={<p>Letzte Simulationen</p>}
                        className={classes.box}
                    >
                        <p>All simulations</p>
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
                    Go Back
                </Button>
            )}
            {selectedFile && (
                <FileDetails file={selectedFile} nameRef={nameRef} />
            )}
            {selectedFile && (
                <div className={classes.selectedFilebuttons}>
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
                                onClick={() => fileInputRef.current.click()}
                            >
                                Select different JSON file
                            </Button>
                        </label>
                    </div>
                </div>
            )}
        </div>
    );
};

export default Dashboard;
