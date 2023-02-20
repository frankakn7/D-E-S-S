import { faCheck, faXmark } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import React from "react";
import Button from "../../Button/Button";
import Modal from "../Modal";
import classes from "./NumOfSimulations.module.css";

const NumOfSimulations = (props) => {
    return (
        <Modal onClose={props.onClose}>
            <div className={classes.top}>
                <h2>How Many Simulation runs?</h2>
            </div>
            <div className={classes.content}>
                <div className={classes.text}>
                    <label htmlFor="numOfSimulationsInput" className={classes.upload}>Number of simulations to run</label>
                    <br />
                    <input className={classes.numInput} id="numOfSimulationsInput" type="number" defaultValue={100000} ref={props.numOfSimulationsRef}></input>
                </div>
                <div className={classes.actions}>
                    <Button
                        onClick={props.onClose}
                        className={classes.cancelButton}
                    >
                        <FontAwesomeIcon icon={faXmark} /> Cancel
                    </Button>
                    <Button
                        onClick={() => {
                            props.onClose();
                            props.onContinue();
                        }}
                        className={classes.continueButton}
                    >
                        <FontAwesomeIcon icon={faCheck} /> Confirm
                    </Button>
                </div>
            </div>
        </Modal>
    );
};

export default NumOfSimulations;
