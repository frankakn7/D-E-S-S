import React from "react";
import Box from "../../../interface/Box/Box";
import { MachineCostBarChart, MachineMakespanBarChart, MachineUtilisationBarChart } from "../ResultsCharts/ResultsCharts";
import classes from "./MachineResults.module.css";
import MachineResultsBox from "./MachineResultsBox/MachineResultsBox";

const MachineResults = (props) => {
    return (
        <div>
            <Box className={classes.machineSummary} titleText={<p>Machines Summary</p>}>
                <div className={classes.summaryCharts}>
                    <MachineUtilisationBarChart allResults={props.allResults} />
                    <MachineMakespanBarChart allResults={props.allResults}/>
                    <MachineCostBarChart allResults={props.allResults}/>
                </div>
            </Box>
            <div className={classes.mainContainer}>
                {props.allResults.machines.map((machine) => (
                    <MachineResultsBox key={machine.id} machine={machine} className={classes.machineBox} />
                    ))}
            </div>
        </div>
    );
};

export default MachineResults;
