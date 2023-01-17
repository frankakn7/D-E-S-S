import React, { useRef, useState } from "react";
import { Link, Navigate, useNavigate } from "react-router-dom";
import Button from "../../interface/Button/Button";
import FileDetails from "./FileDetails/FileDetails";
import classes from "./Upload.module.css";

const Upload = (props) => {
  const [selectedFile, setSelectedFile] = useState();
  const name = useRef();

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

  const checkIfDone = (simId) => {
    props
      .getSimCaseStatusHandler(simId)
      .then((result) => {
        if (result.state === "done") {
          console.log("done");
          props
            .getSimCaseResultHandler(simId)
            .then((response) => navigate(`/simulations`))
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

  const handleUploadAndSimulate = () => {
    const newPlan = {
      name: name.current.value,
      plan: JSON.parse(selectedFile.data),
    };
    props.planUploadHandler(newPlan).then((newPlanId) =>
      props
        .planSimulateHandler(newPlanId)
        .then((simCaseId) => checkIfDone(simCaseId))
        .catch((error) => console.log(error))
    );
  };

  return (
    <div className={classes.content}>
      <h2>Upload</h2>
      {selectedFile && <FileDetails file={selectedFile} nameRef={name} />}
      <div className={`${selectedFile ? classes.allButtons : ""}`}>
        <div
          className={`${classes.buttons} ${
            selectedFile ? classes.buttonsWhenSelected : ""
          }`}
        >
          <label htmlFor="jsonFileInput" className="btn-default-style">
            Select JSON file
          </label>
          <input
            type="file"
            id="jsonFileInput"
            onChange={onFileChange}
            accept=".json"
            style={{ display: "none" }}
          />
          <Link to="/plans" className="btn-default-style">
            View all Uploaded Plans
          </Link>
        </div>
        {selectedFile && (
          <div className={`${classes.buttons} ${classes.buttonsWhenSelected}`}>
            <Button onClick={handleUploadAndSimulate}>
              Upload and Simulate
            </Button>
          </div>
        )}
      </div>
    </div>
  );
};

export default Upload;
