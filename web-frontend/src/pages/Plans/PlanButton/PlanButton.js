import {
    faFileCode,
    faPencil,
    faTrashCan,
} from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import React, { useState } from "react";
import Button from "../../../interface/Button/Button";
import ConfirmationModal from "../../../interface/Modal/ConfirmationModal/ConfirmationModal";
import classes from "./PlanButton.module.css";

/**
 * A button element looking like a file contianing opening and deleting functionalities
 * @param {Object} props all values passed to this element
 * @param {Function} props.onDelete function handling the deletion of a plan
 * @param {String} props.name name of the plan the button belongs to 
 * @param {String} props.id id of the plan the button belongs to 
 * @param {Function} props.doubleClick function to be executed when button is double clicked
 * @param {Date} props.createdOn date and time the plan was created 
 * @returns 
 */
const PlanButton = (props) => {
    const [selected, setSelected] = useState(false);
    const [deleting, setDeleting] = useState(false);

    const deleteHandler = () => {
        setDeleting(true);

    }

    return (
        <div className={classes.relative}>
            {deleting && 
                <ConfirmationModal onClose={() => setDeleting(false)} onContinue={props.onDelete}>
                    <div className={classes.confirmContent}>
                        <p>Are you sure you want to delete the plan named "{props.name}" with the ID ({props.id})? </p>
                        <p><span className={classes.warning}>Warning:</span> this will delete <u>all</u> the associated simulation results</p>
                    </div>
                </ConfirmationModal>}
            {selected && (
                <Button className={classes.delete} onClick={deleteHandler}>
                    <FontAwesomeIcon icon={faTrashCan} />
                </Button>
            )}
            <div
                className={`${classes.main} ${
                    selected ? classes.selected : ""
                }`}
                onClick={() => {
                    setSelected((prevState) => !prevState);
                }}
                onDoubleClick={props.doubleClick}
            >
                <div className={classes.fileIcon}>
                    <FontAwesomeIcon icon={faFileCode} />
                </div>
                <div className={classes.info}>
                    <p className={classes.title}>{props.name}</p>
                    <p className={classes.created}>
                        {new Date(props.createdOn).toLocaleDateString()}
                    </p>
                </div>
            </div>
            {selected && (
                <div className={classes.open}>
                    <Button onClick={props.doubleClick}>open</Button>
                </div>
            )}
        </div>
    );
};

export default PlanButton;
