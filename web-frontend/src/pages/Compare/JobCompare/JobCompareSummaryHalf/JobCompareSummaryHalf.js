import React from "react";
import Box from "../../../../interface/Box/Box";
import { JobCompletionTimeChart, JobCostChart, JobLatenessChart } from "../../../Results/ResultsCharts/ResultsCharts";
import classes from "./JobCompareSummaryHalf.module.css";

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
