import { faAngleDown, faAngleUp } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import React from "react";
import Box from "../../../interface/Box/Box";
import Button from "../../../interface/Button/Button";
import CollapsableBox from "../../../interface/CollapsableBox/CollapsableBox";
import classes from "./MachineCompare.module.css";
import MachineCompareSelector from "./MachineCompareSelector/MachineCompareSelector";
import MachineSummaryCompareHalf from "./MachineSummaryCompareHalf/MachineSummaryCompareHalf";

const MachineCompare = (props) => {
    return (
        <div>
            <CollapsableBox titleText="Machines Summary">
              <div className={classes.collapsableContent}>
                  <MachineSummaryCompareHalf allResults={props.resultsOne} className={classes.summaryHalf}  simName={props.simName1}/>
                  <MachineSummaryCompareHalf allResults={props.resultsTwo} className={classes.summaryHalf} simName={props.simName2}/>
              </div>
            </CollapsableBox>
            <div className={classes.compareMachinesContainer}>
                <div className={classes.half}>
                    <MachineCompareSelector
                        machines={props.resultsOne.machines}
                        simName={props.simName1}
                    />
                </div>
                <div className={classes.half}>
                    <MachineCompareSelector
                        machines={props.resultsTwo.machines}
                        simName={props.simName2}
                    />
                </div>
            </div>
        </div>
    );
};

export default MachineCompare;
