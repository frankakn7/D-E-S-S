import React from 'react'
import SimulationsButton from './SimulationsButton'

import classes from "./Simulations.module.css"

const Simulations = (props) => {


  return (
    <div className={classes.content}>
        <h2>All Simulations</h2>
        <div className={classes.plans}>
          {props.simCases.map((simCases) => (
            <SimulationsButton
            key={simCases.id}
            simCasesID={simCases.id}
            />
            ))}
        </div>
    </div>
  )
}

export default Simulations