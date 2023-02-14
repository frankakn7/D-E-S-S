import React from "react";
import { Link, useNavigate } from "react-router-dom";
import Button from "../../interface/Button/Button";
import ListButton from "../../interface/ListButton/ListButton";

import classes from "./Plans.module.css";

const Plans = (props) => {
    const navigate = useNavigate();

    return (
        <div className={classes.content}>
            <h2>All uploaded Plans</h2>
            <div className={classes.plans}>
                {props.plans
                    .sort(
                        (a, b) => new Date(b.createdOn) - new Date(a.createdOn)
                    )
                    .map((plan) => (
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
