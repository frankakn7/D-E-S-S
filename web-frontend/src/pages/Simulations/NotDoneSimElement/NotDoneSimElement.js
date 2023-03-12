import { faTriangleExclamation } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import React from "react";
import classes from "./NotDoneSimElement.module.css";

/**
 * An element displaying the loading signal and bar for a currently running simulation case
 * @param {Object} props all values passed to the element
 * @param {String} props.planName name of the plan the simulation case belongs to
 * @param {Object} props.notDoneSim an object containing information for the currently running simulation case
 * @returns 
 */
const NotDoneSimElement = (props) => {
    // console.log(props.notDoneSim);
    return (
        <div className={classes.main}>
            <div className={classes.content}>
                <div className={classes.top}>
                    <div className={classes.left}>
                        <div>
                            Simulation of <b>"{props.planName}"</b>
                        </div>
                        <div className={classes.progressBar}>
                            <div
                                className={classes.progressFill}
                                style={{
                                    width: `${
                                        (props.notDoneSim.progress /
                                            props.notDoneSim.total) *
                                        100
                                    }%`,
                                }}
                            >
                                {parseFloat(
                                    (props.notDoneSim.progress /
                                        props.notDoneSim.total) *
                                        100
                                ).toFixed(0)}
                                %
                            </div>
                        </div>
                    </div>
                <div className={classes.right}>
                    {!(props.notDoneSim.state === "logical_error") && <div className={classes.loadingCircle}></div>}
                    {props.notDoneSim.state === "logical_error" && <div className={classes.errorSign} title={"Logical Error in Simulated Plan"}><FontAwesomeIcon icon={faTriangleExclamation}/></div>}
                </div>
                </div>
            </div>
        </div>
    );
};

export default NotDoneSimElement;
