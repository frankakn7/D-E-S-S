import React from "react";
import { Link, useNavigate } from "react-router-dom";
import PlanButton from "./PlanButton";

import classes from "./Plans.module.css";

const Plans = (props) => {
  const navigate = useNavigate();

  const checkIfDone = (simId) => {
    props
      .getSimCaseStatusHandler(simId)
      .then((result) => {
        if (result.state === "done") {
          console.log("done");
          props
            .getSimCaseResultHandler(simId)
            .then((response) => navigate(`/results/${simId}`))
            .catch((error) => console.log(error));
        } else {
          console.log("not done");
          const timer = setTimeout(() => {
            checkIfDone(simId);
            clearTimeout(timer);
          }, 1000);
        }
      })
      .catch((error) => console.log(error));
  };

  const handleSimulate = (planId) => {
    props
      .planSimulateHandler(planId)
      .then((simCaseId) => checkIfDone(simCaseId))
      .catch((error) => console.log(error));
  };

  return (
    <div className={classes.content}>
      <h2>All uploaded Plans</h2>
      <div className={classes.plans}>
        {props.plans.map((plan) => (
          <PlanButton
            key={plan.uuid}
            handleSimulate={handleSimulate}
            planId={plan.uuid}
            planName={plan.name}
          />
        ))}
      </div>
      <Link to="/upload" className="btn-default-style">
        Upload new Plan
      </Link>
    </div>
  );
};

export default Plans;
