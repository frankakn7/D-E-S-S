import React from "react";
import Box from "../../../../interface/Box/Box";
import { MachineBreakdownIdleChart, MachineCostBarChart, MachineMakespanBarChart, MachineUtilisationBarChart } from "../../../Results/ResultsCharts/ResultsCharts";
import classes from "./MachineSummaryCompareHalf.module.css";

/**
 * A column display of different charts for summary of machine results data. (displayed inside Box element)
 * @param {Object} props - all props passed to the element
 * @param {String} props.simName - name of the displayed simulation
 * @param {String} props.className - custom css classes applied to the top level Box element
 * @param {Object} props.allResults - all results that should be used for displaying 
 * @returns 
 */
const MachineSummaryCompareHalf = (props) => {
    return (
        <Box titleText={<p>{props.simName}</p>} className={props.className}>
            <div className={classes.boxContent}>
                <MachineUtilisationBarChart
                    className={classes.graphs}
                    allResults={props.allResults}
                />
                <MachineMakespanBarChart
                    className={classes.graphs}
                    allResults={props.allResults}
                />
                <MachineBreakdownIdleChart
                    className={classes.graphs}
                    allResults={props.allResults}
                />
                <MachineCostBarChart
                    className={classes.graphs}
                    allResults={props.allResults}
                />
            </div>
        </Box>
    );
};

export default MachineSummaryCompareHalf;
