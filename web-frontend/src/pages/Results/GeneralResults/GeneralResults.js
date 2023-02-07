import React from 'react'

const GeneralResults = (props) => {
  return (
    <div>
        <p>Mean Completion Time: {props.generalStats.completion_time.mean}</p>
        <p>Mean Total Cost: {props.generalStats.total_cost.mean}</p>
        <p>Mean Total ressource utilization: {props.generalStats.total_ressource_utilization.mean}</p>
    </div>
  )
}

export default GeneralResults