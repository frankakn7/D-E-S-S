import React from "react";
import classes from "./GeneralCompare.module.css";
import GeneralResultsGraph from "./GeneralResultsGraphs/GeneralResultsGraph";

/**
 * A page split in 2 parts (left and right) to compare the summary result graphs of 2 simulations
 * @param {Object} props - all props passed to this element
 * @param {Object} props.results1 - results object of the first simulation
 * @param {Object} props.results2 - results object of the second simulation
 * @param {String} props.simName1 - name of the first simulation
 * @param {String} props.simName2 - name of the second simulation
 * @returns 
 */
const GeneralCompare = (props) => {
    return (
        <div className={classes.content}>
            <div style={{ width: "49%" }}>
                <GeneralResultsGraph allResults={props.results1} simName={props.simName1} />
            </div>
            <div style={{ width: "49%" }}>
                <GeneralResultsGraph allResults={props.results2} simName={props.simName2}/>
            </div>
        </div>
    );
};

export default GeneralCompare;
