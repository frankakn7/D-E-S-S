import React, { Fragment } from "react";

import classes from "./Simulations.module.css";
import ListButton from "../../interface/ListButton/ListButton";
import { useNavigate } from "react-router-dom";
import NotDoneSimElement from "./NotDoneSimElement/NotDoneSimElement";

/**
 * A page displaying all the simulations running and done
 * @param {Object} props all values passed to the element
 * @param {Array<Object>} props.notDoneSims a list of objects representing the not done simulations
 * @param {Array<Object>} props.simCases a list of all the simcases 
 * @param {Array<Object>} props.plans a list of all the plans
 * @returns 
 */
const Simulations = (props) => {

    const navigate = useNavigate();

    return (
        <div className={classes.content}>
            <h2>All Simulations</h2>
            <div className={classes.plans}>
                {props.notDoneSims.map((notDoneSim) => (
                    <NotDoneSimElement notDoneSim={notDoneSim} planName={props.plans.find((plan) => notDoneSim.planId === plan.uuid).name}/>
                ))}
                {props.simCases.sort((a,b) => new Date(b.createdOn) - new Date(a.createdOn)).map((simCase) => (
                    <ListButton
                        key={simCase.id}
                        onClick={() => {navigate(`/results/${simCase.id}`)}}
                        id={simCase.id}
                        name={<Fragment>Simulation of <b>"{props.plans.find((plan) => simCase.planId === plan.uuid).name}"</b></Fragment>}
                        createdOn={simCase.createdOn}
                    />
                ))}
            </div>
        </div>
    );
};

export default Simulations;
