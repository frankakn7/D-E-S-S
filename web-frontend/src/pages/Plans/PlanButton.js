import { faAngleRight } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import React from "react";
import Button from "../../interface/Button/Button";

import classes from "./PlanButton.module.css";

const PlanButton = (props) => {
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

export default PlanButton;
