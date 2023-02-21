import React from "react";
import Box from "../../../interface/Box/Box";
import CollapsableBox from "../../../interface/CollapsableBox/CollapsableBox";
import classes from "./JobCompare.module.css";
import JobCompareSelector from "./JobCompareSelector/JobCompareSelector";
import JobCompareSummaryHalf from "./JobCompareSummaryHalf/JobCompareSummaryHalf";

/**
 * Job compare tab for comparing simulation results of 2 simulations focused on the jobs elements
 * @param {Object} props - all props passed to the element 
 * @param {Object} props.resultsOne - all the results of simulation number 1 of 2 
 * @param {Object} props.resultsTow - all the results of simulation number 2 of 2 
 * @param {String} props.simName1 - the name of the simulation number 1 of 2 
 * @param {String} props.simName2 - the name of the simulation number 2 of 2 
 * @returns 
 */
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
