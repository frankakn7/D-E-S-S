import { faAngleDown, faAngleUp } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import React from "react";
import Box from "../../../interface/Box/Box";
import Button from "../../../interface/Button/Button";
import classes from "./MachineCompare.module.css";
import MachineCompareSelector from "./MachineCompareSelector/MachineCompareSelector";

const MachineCompare = (props) => {
    return (
        <div>
            <Box titleText={<p>Machines Summary</p>}>
                <p>Compare here</p>
            </Box>
            <div className={classes.compareMachinesContainer}>
                <div className={classes.half}>
                    <MachineCompareSelector
                        machines={props.resultsOne.machines}
                    />
                </div>
                <div className={classes.half}>
                    <MachineCompareSelector
                        machines={props.resultsTwo.machines}
                    />
                </div>
            </div>
        </div>
    );
};

export default MachineCompare;
