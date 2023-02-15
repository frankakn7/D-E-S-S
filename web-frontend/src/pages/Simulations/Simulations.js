import React, { Fragment } from "react";

import classes from "./Simulations.module.css";
import ListButton from "../../interface/ListButton/ListButton";
import { useNavigate } from "react-router-dom";

const Simulations = (props) => {

    const navigate = useNavigate();

    return (
        <div className={classes.content}>
            <h2>All Simulations</h2>
            <div className={classes.plans}>
                {props.simCases.sort((a,b) => new Date(b.createdOn) - new Date(a.createdOn)).map((simCase) => (
                    <ListButton
                        key={simCase.id}
                        onClick={() => {navigate(`/results/${simCase.id}`)}}
                        id={simCase.id}
                        name={<Fragment>Simulation of <b>"{props.plans.find((plan) => simCase.planId === plan.uuid).name}"</b></Fragment>}
                        createdOn={simCase.createdOn}
                    />
                ))}
            </div>
        </div>
    );
};

export default Simulations;
