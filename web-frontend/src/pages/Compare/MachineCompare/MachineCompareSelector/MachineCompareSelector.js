import { faAngleDown, faAngleUp } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import React, { useState } from "react";
import Button from "../../../../interface/Button/Button";
import MachineResultsBox from "../../../Results/MachineResults/MachineResultsBox/MachineResultsBox";
import classes from "./MachineCompareSelector.module.css"

/**
 * A selector for cycling through Machine result boxes for comparison purposes
 * @param {Object} props - all props passed to the element
 * @param {String} props.simName - name of the simulation the machines belong to
 * @param {Array<Object>} props.machines - list of all the machines and their results of the simulation
 * @returns 
 */
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
                <MachineResultsBox machine={props.machines[selected]} extraTitleText={"in "+props.simName}/>
            </div>
            <Button onClick={plusSelected}>
                <FontAwesomeIcon icon={faAngleDown} />
            </Button>
        </div>
    );
};

export default MachineCompareSelector;
