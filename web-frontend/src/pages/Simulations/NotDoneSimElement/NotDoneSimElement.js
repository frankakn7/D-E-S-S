import React from "react";
import classes from "./NotDoneSimElement.module.css";

const NotDoneSimElement = (props) => {
    console.log(props.notDoneSim);
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
                    <div className={classes.loadingCircle}></div>
                </div>
                </div>
            </div>
        </div>
    );
};

export default NotDoneSimElement;
