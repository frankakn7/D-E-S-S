import React from "react";
import { faAngleRight } from "@fortawesome/free-solid-svg-icons";
import Button from "../../interface/Button/Button";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

import classes from "./SimButton.module.css";

const SimulationsButton = (props) => {
    return (
        <Button
            className={classes.button}
            onClick={() => props.handleSimulate(props.planId)}
        >
            <div>
                Plan: "{props.planName}" ({props.planId})
            </div>
            <FontAwesomeIcon icon={faAngleRight} />
        </Button>
    );
};

export default SimulationsButton;
