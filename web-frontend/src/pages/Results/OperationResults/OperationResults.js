import React from "react";
import Box from "../../../interface/Box/Box";
import DetailsTable from "../DetailsTable/DetailsTable";
import DetailsTableRow from "../DetailsTable/DetailsTableRow";
import { OperationsLengthChart } from "../ResultsCharts/ResultsCharts";
import classes from "./OperationResults.module.css";
import OperationsResultsBox from "./OperationsResultsBox";

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
