import React, { useState } from "react";
import Box from "../../../../interface/Box/Box";
import {
    JobCompletionTimeChart,
    MachineUtilisationBarChart,
    MachineMakespanBarChart,
    TotalCostPieChart,
    TotalResourceUtilisationPieChart,
} from "../../../Results/ResultsCharts/ResultsCharts";
import classes from "./GeneralResultsGraph.module.css";

/**
 * Presents the summary of results of a simulation in a column format inside a Box including different tables
 * @param {Object} props - all props passed to this element
 * @param {String} props.simName - name of the displayed simulation 
 * @param {Object} props.allResults - all the results of the simulation
 * @returns 
 */
const GeneralResultsGraph = (props) => {
    return (
        <Box
            className={classes.contentBox}
            titleClassName={classes.boxTitle}
            titleText={<p>{props.simName}</p>}
        >
            <div className={classes.boxContent}>
                <div className={classes.graphContainer}>
                    <TotalResourceUtilisationPieChart allResults={props.allResults} />
                </div>
                <div className={classes.graphContainer}>
                    <TotalCostPieChart allResults={props.allResults} />
                </div>
                <div className={classes.graphContainer}>
                    <MachineUtilisationBarChart allResults={props.allResults} />
                </div>

                <div className={classes.graphContainer}>
                    <JobCompletionTimeChart allResults={props.allResults} />
                </div>

                <div className={classes.graphContainer}>
                    <MachineMakespanBarChart allResults={props.allResults} />
                </div>
            </div>
        </Box>
    );
};

export default GeneralResultsGraph;
