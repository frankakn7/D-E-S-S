import React from 'react'
import { Link, useNavigate } from "react-router-dom";
import SimulationsButton from './SimulationsButton'

const Simulations = (props) => {

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
    <div>
        {props.plans.map((plan) => (
          <SimulationsButton
            key={plan.uuid}
            planId={plan.uuid}
            handleSimulate={handleSimulate}
            planName={plan.name}
          />
        ))}
    </div>
  )
}

export default Simulations