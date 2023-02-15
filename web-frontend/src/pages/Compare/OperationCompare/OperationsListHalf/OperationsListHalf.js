import React from "react";
import CollapsableBox from "../../../../interface/CollapsableBox/CollapsableBox";
import OperationsResultsBox from "../../../Results/OperationResults/OperationsResultsBox";
import classes from "./OperationsListHalf.module.css";

const OperationsListHalf = (props) => {
    return (
        <div className={props.className}>
            <CollapsableBox titleText={props.simName}>
                <div className={classes.collapsableBoxContent}>

                    {props.allResults.operations.map((operation) => (
                        <OperationsResultsBox operation={operation} />
                        ))}
                        </div>
            </CollapsableBox>
        </div>
    );
};

export default OperationsListHalf;
