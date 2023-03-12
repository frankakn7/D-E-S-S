import React from "react";
import { Link, useNavigate } from "react-router-dom";
import Button from "../../interface/Button/Button";
import ListButton from "../../interface/ListButton/ListButton";
import ConfirmationModal from "../../interface/Modal/ConfirmationModal/ConfirmationModal";
import PlanButton from "./PlanButton/PlanButton";

import classes from "./Plans.module.css";

/**
 * A page displaying all saved plans
 * @param {Object} props all values passed to the element
 * @param {Array<Object>} props.plans a list of all plans 
 * @param {Function} props.planDeleteHandler a function deleting plan by its id  
 * @returns 
 */
const Plans = (props) => {
    const navigate = useNavigate();

    // const deleteHandler = (planId) => {
    //     console.log("deletePlan: "+planId)
    // }

    return (
        <div className={classes.content}>
            <h2>All uploaded Plans</h2>
            <div className={classes.plans}>
                {props.plans
                    .sort(
                        (a, b) => new Date(b.createdOn) - new Date(a.createdOn)
                    )
                    .map((plan) => (
                        <PlanButton
                            key={plan.uuid}
                            name={plan.name}
                            id={plan.uuid}
                            createdOn={plan.createdOn}
                            doubleClick={() => {
                                navigate(`/plans/${plan.uuid}`);
                            }}
                            onDelete={() => props.planDeleteHandler(plan.uuid)}
                        />
                    ))}
            </div>
            <Button
                onClick={() => {
                    navigate(`/`);
                }}
                className="btn-default-style"
            >
                Upload new Plan
            </Button>
        </div>
    );
};

export default Plans;
