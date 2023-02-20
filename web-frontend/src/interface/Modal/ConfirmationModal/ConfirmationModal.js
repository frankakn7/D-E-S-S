import { faCheck, faX, faXmark } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import React from "react";
import Button from "../../Button/Button";
import Modal from "../Modal";
import classes from './ConfirmationModal.module.css'

const ConfirmationModal = (props) => {
    return (
        <Modal onClose={props.onClose}>
            <div className={classes.top}>
                <h2>Are you sure?</h2>
            </div>
            <div className={classes.content}>
                <div className={classes.text}>{props.children}</div>
                <div className={classes.actions}>
                    <Button onClick={props.onClose} className={classes.cancelButton}><FontAwesomeIcon icon={faXmark}/> Cancel</Button>
                    <Button onClick={() => {props.onClose(); props.onContinue()}} className={classes.continueButton}><FontAwesomeIcon icon={faCheck} /> Continue</Button>
                </div>
            </div>
        </Modal>
    );
};

export default ConfirmationModal;
