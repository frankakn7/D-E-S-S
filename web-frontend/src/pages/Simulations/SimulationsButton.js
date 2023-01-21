import React from "react";
import { Link, useNavigate } from "react-router-dom";
import { faAngleRight } from "@fortawesome/free-solid-svg-icons";
import Button from "../../interface/Button/Button";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

import classes from "./SimButton.module.css";

const SimulationsButton = (props) => {

    const navigate = useNavigate();

    return (
        <Button
            className={classes.button}
            onClick={() => {navigate(`/results/${props.simCasesID}`)}}
        >
            <div>
                Simulation: ({props.simCasesID})
            </div>
            <FontAwesomeIcon icon={faAngleRight} />
        </Button>
    );
};

export default SimulationsButton;
