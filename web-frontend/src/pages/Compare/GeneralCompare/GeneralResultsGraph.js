import React, { useState } from "react";
import Box from "../../../interface/Box/Box";
import {
  JobCompletionTimeChart,
  MachineUtilisationBarChart,
  MachineMakespanBarChart
} from "../../Results/ResultsCharts/ResultsCharts";
import classes from "./GeneralCompareGraph.module.css";

const GeneralResultsGraph = (props) => {
  return (
    <Box
      className={classes.contentBox}
      titleClassName={classes.boxTitle}
      titleText={<p>Simualation</p>}
    >
      <div className={classes.boxContent}>
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
