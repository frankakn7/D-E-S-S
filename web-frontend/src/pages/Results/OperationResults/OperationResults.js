import React from "react";
import Box from "../../../interface/Box/Box";
import DetailsTable from "../DetailsTable/DetailsTable";
import DetailsTableRow from "../DetailsTable/DetailsTableRow";
import { OperationsLengthChart } from "../ResultsCharts/ResultsCharts";
import classes from "./OperationResults.module.css";
import OperationsResultsBox from "./OperationsResultsBox";

/**
 * Box displaying all the operation results of a simulation case
 * @param {Object} props all values passed to the element 
 * @param {Object} props.allResults object containing all the results of a simulation case
 * @returns 
 */
const OperationResults = (props) => {
    return (
        <div>
            <Box titleText={<p>Operations Summary</p>}>
                <div className={classes.operationsSummary}>
                    <OperationsLengthChart allResults={props.allResults} />
                </div>
            </Box>
            <div className={classes.operationBoxes}>
                {props.allResults.operations.map((operation) => (
                    <OperationsResultsBox operation={operation} />
                ))}
            </div>
        </div>
    );
};

export default OperationResults;
