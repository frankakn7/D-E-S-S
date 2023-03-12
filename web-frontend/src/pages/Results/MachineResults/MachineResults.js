import React from "react";
import Box from "../../../interface/Box/Box";
import { MachineBreakdownIdleChart, MachineCostBarChart, MachineMakespanBarChart, MachineUtilisationBarChart } from "../ResultsCharts/ResultsCharts";
import classes from "./MachineResults.module.css";
import MachineResultsBox from "./MachineResultsBox/MachineResultsBox";

/**
 * Box containing all the machine results of a simulation case
 * @param {Object} props all values passed to the element
 * @param {Object} props.allResults object containing all the results of a simulation case 
 * @returns 
 */
const MachineResults = (props) => {
    return (
        <div>
            <Box className={classes.machineSummary} titleText={<p>Machines Summary</p>}>
                <div className={classes.summaryCharts}>
                    <MachineUtilisationBarChart allResults={props.allResults} />
                    <MachineMakespanBarChart allResults={props.allResults} className={classes.makeSpanChart}/>
                    <MachineBreakdownIdleChart allResults={props.allResults} />
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
