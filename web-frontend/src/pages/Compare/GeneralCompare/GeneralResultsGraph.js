import React, { useState } from "react";
import {
  JobCompletionTimeChart,
  MachineUtilisationBarChart,
  MachineMakespanBarChart
} from "../../Results/ResultsCharts/ResultsCharts";
import classes from "./GeneralCompareGraph.module.css";
import Box from "/Users/matziol/web-app/web-frontend/src/interface/Box/Box.js";

const GeneralResultsGraph = (props) => {
  return (
    <Box
      className={classes.contentBox}
      titleClassName={classes.boxTitle}
      titleText={<p>Simualation</p>}
    >
      <div className={classes.boxContent}>
        <div className={classes.graphContainer}>
          <MachineUtilisationBarChart allResults={props.results} />
        </div>

        <div className={classes.graphContainer}>
          <JobCompletionTimeChart allResults={props.results} />
        </div>

        <div className={classes.graphContainer}>
            <MachineMakespanBarChart allResults={props.results} />
        </div>

      </div>
    </Box>
  );
};

export default GeneralResultsGraph;
