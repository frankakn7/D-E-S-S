import React from "react";
import Box from "../../../../interface/Box/Box";
import DetailsTable from "../../DetailsTable/DetailsTable";
import DetailsTableRow from "../../DetailsTable/DetailsTableRow";
import { JobsOperationsLength } from "../../ResultsCharts/ResultsCharts";
import classes from "./JobResultsBox.module.css";

/**
 * A box displaying the detailed results of a specific job of a simulation case
 * @param {Object} props all values passed to the element
 * @param {Object} props.job object containing all results for a job
 * @param {String} props.className classes passed to the top box element
 * @returns 
 */
const JobResultsBox = (props) => {
    return (
        <Box
            key={props.job.id}
            className={`${classes.jobBox} ${props.className}`}
            titleText={<p>{`Job "${props.job.id}" ${props.extraTitleText ? props.extraTitleText : ""}`}</p>}
        >
            <div className={classes.jobBoxContent}>
                <div>
                    <DetailsTable>
                        <DetailsTableRow
                            stat={{
                                name: "completion time",
                                ...props.job.completion_time,
                            }}
                        />
                        <DetailsTableRow
                            stat={{
                                name: "lateness",
                                ...props.job.lateness,
                            }}
                        />
                        <DetailsTableRow
                            stat={{
                                name: "lateness cost",
                                ...props.job.lateness_cost,
                            }}
                        />
                    </DetailsTable>
                </div>
                <JobsOperationsLength job={props.job} className={classes.rightData} />
            </div>
        </Box>
    );
};

export default JobResultsBox;
