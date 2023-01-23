import { faAngleRight } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import React from "react";
import { useNavigate } from "react-router-dom";
import Button from "../../interface/Button/Button";

import classes from "./PlanButton.module.css";

const PlanButton = (props) => {

  const navigate = useNavigate();

  return (
    <Button
      className={classes.button}
      onClick={() => {navigate(`/plans/${props.planId}`)}}
    >
      <div>
        Plan: "{props.planName}" ({props.planId})
      </div>
      <FontAwesomeIcon icon={faAngleRight} />
    </Button>
  );
};

export default PlanButton;
