import React from "react";
import { Link, useNavigate } from "react-router-dom";
import ListButton from "../../interface/ListButton/ListButton";

import classes from "./Plans.module.css";

const Plans = (props) => {
    const navigate = useNavigate();

    const checkIfDone = (simId) => {
        props
            .getSimCaseStatusHandler(simId)
            .then((result) => {
                if (result.state === "done") {
                    props
                        .getSimCaseResultHandler(simId)
                        .then((response) => navigate(`/results/${simId}`))
                        .catch((error) => console.log(error));
                } else {
                    const timer = setTimeout(() => {
                        checkIfDone(simId);
                        clearTimeout(timer);
                    }, 1000);
                }
            })
            .catch((error) => console.log(error));
    };

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
            <Link to="/" className="btn-default-style">
                Upload new Plan
            </Link>
        </div>
    );
};

export default Plans;
