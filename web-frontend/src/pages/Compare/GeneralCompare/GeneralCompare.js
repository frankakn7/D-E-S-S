import React from "react";
import classes from "./GeneralCompare.module.css";
import GeneralResultsGraph from "./GeneralResultsGraph";

const GeneralCompare = (props) => {
    return (
        <div className={classes.content}>
            <div style={{ width: "49%" }}>
                <GeneralResultsGraph allResults={props.results1} />
            </div>
            <div style={{ width: "49%" }}>
                <GeneralResultsGraph allResults={props.results2} />
            </div>
        </div>
    );
};

export default GeneralCompare;
