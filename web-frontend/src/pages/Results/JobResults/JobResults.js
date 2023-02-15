import React from "react";
import Box from "../../../interface/Box/Box";
import JobsTable from "../../FileDetails/tables/JobsTable";
import DetailsTable from "../DetailsTable/DetailsTable";
import DetailsTableRow from "../DetailsTable/DetailsTableRow";
import {
    JobCompletionTimeChart,
    JobCostChart,
    JobLatenessChart,
    JobsOperationsLength,
} from "../ResultsCharts/ResultsCharts";
import classes from "./JobResults.module.css";
import JobResultsBox from "./JobResultsBox/JobResultsBox";

const JobResults = (props) => {
    return (
        <div className={classes.content}>
            <Box titleText={<p>Jobs Summary</p>}>
                <div className={classes.summaryBox}>
                    <JobCompletionTimeChart allResults={props.allResults} />
                    <JobLatenessChart allResults={props.allResults} />
                    <JobCostChart allResults={props.allResults} />
                </div>
            </Box>
            <div className={classes.jobBoxes}>
                {props.allResults.jobs.map((job) => (
                    <JobResultsBox job={job} className={classes.jobBox}/>
                ))}
            </div>
        </div>
    );
};

export default JobResults;
