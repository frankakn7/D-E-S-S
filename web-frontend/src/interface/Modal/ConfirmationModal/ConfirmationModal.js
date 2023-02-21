import { faCheck, faX, faXmark } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import React from "react";
import Button from "../../Button/Button";
import Modal from "../Modal";
import classes from './ConfirmationModal.module.css'

/**
 * A modal to confirm actions with cancel and continue buttons
 * @param {Object} props - All props passed to this element
 * @param {Function} props.onClose - function to be executed when the modal cancel button or background is clicked (usually a function hiding the modal)
 * @param {Function} props.onContinue - function to be exectued when the continue button is pressed (action for which confirmation was needed e.g. delete)
 * @param {JSX.Element} props.children - Content to be placed above the buttons inside the modal (usually text asking if a certain action should be performed) 
 * @returns 
 */
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
