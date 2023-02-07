import React from "react";
import Box from "../../../interface/Box/Box";
import Button from "../../../interface/Button/Button";
import classes from "./MachineResults.module.css";

const MachineResults = (props) => {
    return (
        <div className={classes.mainContainer}>
            {props.machines.map((machine) => (
                <Box
                    key={machine.id}
                    titleText={<p>{`Machine "${machine.id}"`}</p>}
                    className={classes.machineBox}
                >
                    <button>
                        utilization percent: {machine.utilisation.percent.mean}
                    </button>
                    <p>utilization time: {machine.utilisation.time.mean}</p>
                    <p>repair cost: {machine.repair_cost.mean}</p>
                    <p>operational cost: {machine.operational_cost.mean}</p>
                    <p>
                        Breakdowns downtime: {machine.breakdowns.downtime.mean}
                    </p>
                    <p>
                        Breakdowns occurance:{" "}
                        {machine.breakdowns.occurrence.mean}
                    </p>
                    <p>Breakdowns percent: {machine.breakdowns.percent.mean}</p>
                </Box>
            ))}
        </div>
    );
};

export default MachineResults;
