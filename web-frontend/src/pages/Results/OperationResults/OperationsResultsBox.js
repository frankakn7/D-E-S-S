import React from "react";
import Box from "../../../interface/Box/Box";
import DetailsTable from "../DetailsTable/DetailsTable";
import DetailsTableRow from "../DetailsTable/DetailsTableRow";
import classes from "./OperationResults.module.css"

/**
 * Box diplaying detailed results of a singular operation
 * @param {Object} props all values passed to the element
 * @param {Object} props.operation object containing the results of the opertion
 * @returns 
 */
const OperationsResultsBox = (props) => {
    return (
        <Box
            key={props.operation.id}
            titleText={<p>{`Operation "${props.operation.id}"`}</p>}
            className={classes.operationsBox}
        >
            <DetailsTable>
                <DetailsTableRow
                    stat={{ name: "length", ...props.operation.length }}
                />
            </DetailsTable>
        </Box>
    );
};

export default OperationsResultsBox;
