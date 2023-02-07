import React from "react";
import classes from "./MachineResults.module.css";
import MachineResultsBox from "./MachineResultsBox/MachineResultsBox";

const MachineResults = (props) => {
    return (
        <div className={classes.mainContainer}>
            {props.machines.map((machine) => (
                <MachineResultsBox key={machine.id} machine={machine} className={classes.machineBox} />
            ))}
        </div>
    );
};

export default MachineResults;
