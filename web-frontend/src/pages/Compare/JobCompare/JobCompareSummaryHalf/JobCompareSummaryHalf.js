import React from "react";
import Box from "../../../../interface/Box/Box";
import { JobCompletionTimeChart, JobCostChart, JobLatenessChart } from "../../../Results/ResultsCharts/ResultsCharts";
import classes from "./JobCompareSummaryHalf.module.css";

/**
 * Simulation results of jobs in a summary format displayed as a column inside a Box for side by side comparison
 * @param {Object} props - all props passed to the element
 * @param {String} props.simName - name of the displayed simulation 
 * @param {String} props.className - custom css classes applied to the top level Box element
 * @param {Object} props.allResults - all results of the simulation
 * @returns 
 */
const JobCompareSummaryHalf = (props) => {
    return (
        <Box titleText={<p>{props.simName}</p>} className={props.className}>
            <div className={classes.boxContent}>
                <JobCompletionTimeChart className={classes.graphs} allResults={props.allResults} />
                <JobLatenessChart className={classes.graphs} allResults={props.allResults} />
                <JobCostChart className={classes.graphs} allResults={props.allResults}/>
            </div>
        </Box>
    );
};

export default JobCompareSummaryHalf;
