import { faAngleDown, faAngleUp } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import React, { useState } from "react";
import Button from "../../../../interface/Button/Button";
import MachineResultsBox from "../../../Results/MachineResults/MachineResultsBox/MachineResultsBox";
import classes from "./MachineCompareSelector.module.css"

const MachineCompareSelector = (props) => {

    const [selected, setSelected] = useState(0)

    const minusSelected = () => {
        setSelected((prevState) => {
            if(prevState >= 1){
                return prevState-1;
            }
            return prevState;
        })
    }

    const plusSelected = () => {
        setSelected((prevState) => {
            if(prevState < props.machines.length - 1){
                return prevState += 1;
            }
            return prevState;
        })
    }

    return (
        <div className={classes.selectorContainer}>
            <Button onClick={minusSelected}>
                <FontAwesomeIcon icon={faAngleUp} />
            </Button>
            <div className={classes.machineBoxContainer}>
                <MachineResultsBox machine={props.machines[selected]} />
            </div>
            <Button onClick={plusSelected}>
                <FontAwesomeIcon icon={faAngleDown} />
            </Button>
        </div>
    );
};

export default MachineCompareSelector;
