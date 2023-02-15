import React from "react";
import Box from "../../../../interface/Box/Box";
import { MachineBreakdownIdleChart, MachineCostBarChart, MachineMakespanBarChart, MachineUtilisationBarChart } from "../../../Results/ResultsCharts/ResultsCharts";
import classes from "./MachineSummaryCompareHalf.module.css";

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
