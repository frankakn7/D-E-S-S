import React from "react";
import Box from "../../../interface/Box/Box";
import CollapsableBox from "../../../interface/CollapsableBox/CollapsableBox";
import classes from "./JobCompare.module.css";
import JobCompareSelector from "./JobCompareSelector/JobCompareSelector";
import JobCompareSummaryHalf from "./JobCompareSummaryHalf/JobCompareSummaryHalf";

const JobCompare = (props) => {
    return (
        <div>
            <CollapsableBox titleText="Jobs Summary">
                <div className={classes.collapsableContent}>
                    <JobCompareSummaryHalf
                        allResults={props.resultsOne}
                        className={classes.summaryHalf}
                        simName={props.simName1}
                    />
                    <JobCompareSummaryHalf
                        allResults={props.resultsTwo}
                        className={classes.summaryHalf}
                        simName={props.simName2}
                    />
                </div>
            </CollapsableBox>
            <div className={classes.compareJobsContainer}>
                <div className={classes.half}>
                    <JobCompareSelector
                        jobs={props.resultsOne.jobs}
                        simName={props.simName1}
                    />
                </div>
                <div className={classes.half}>
                    <JobCompareSelector
                        jobs={props.resultsTwo.jobs}
                        simName={props.simName2}
                    />
                </div>
            </div>
        </div>
    );
};

export default JobCompare;
