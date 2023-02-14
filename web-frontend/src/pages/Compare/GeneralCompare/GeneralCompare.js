import React from "react";
import classes from "./GeneralCompare.module.css";
import DetailsTable from "../../Results/DetailsTable/DetailsTable";
import DetailsTableRow from "/Users/matziol/web-app/web-frontend/src/pages/Results/DetailsTable/DetailsTable.js";
import GeneralResultsGraph from "./GeneralResultsGraph";
import {
  MachineUtilisationBarChart,
  JobCompletionTimeChart,
} from "/Users/matziol/web-app/web-frontend/src/pages/Results/ResultsCharts/ResultsCharts.js";

const GeneralCompare = (props) => {
  return (
   <div className={classes.content}>
      <GeneralResultsGraph results={props.results1}/>
      <GeneralResultsGraph results={props.results2}/>
   </div>
  );
}

export default GeneralCompare;
