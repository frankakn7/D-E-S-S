import React from "react";
import SimulationsButton from "./SimulationsButton";

import classes from "./Simulations.module.css";

const Simulations = (props) => {
    return (
        <div className={classes.content}>
            <h2>All Simulations</h2>
            <div className={classes.plans}>
                {props.simCases.map((simCase) => (
                    <SimulationsButton
                        key={simCase.id}
                        simCasesID={simCase.id}
                        planName={props.plans.find((plan) => simCase.plan_id === plan.uuid).name}
                    />
                ))}
            </div>
        </div>
    );
};

export default Simulations;
