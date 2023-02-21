import { faCheck, faXmark } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import React from "react";
import Button from "../../Button/Button";
import Modal from "../Modal";
import classes from "./NumOfSimulations.module.css";

/**
 * Modal for inputting the number of simulations that should be run when a plan is simulated.
 * Contains cancel and continue buttons as well as an input field for the number of simulations
 * @param {Object} props - All props passed to this element 
 * @param {Function} props.onClose - function to be executed when the close button or background is clicked (usually a function hiding the modal)
 * @param {Function} props.onContinue - function to be executed when continue button is pressed (usually function starting the simulation)
 * @returns 
 */
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
