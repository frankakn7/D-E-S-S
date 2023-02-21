import React from "react";
import CollapsableBox from "../../../../interface/CollapsableBox/CollapsableBox";
import OperationsResultsBox from "../../../Results/OperationResults/OperationsResultsBox";
import classes from "./OperationsListHalf.module.css";

/**
 * A collapsable box containint small operation results boxes for side by side comparison
 * @param {Object} props - all props passed to the element
 * @param {String} props.simName - name of the simulation the data belongs to
 * @param {String} props.className - custom classes applied to the top level div of the element (over collapsable box) 
 * @param {Object} props.allResults - all results of the simulation of which the operations should be displayed
 * @returns 
 */
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
