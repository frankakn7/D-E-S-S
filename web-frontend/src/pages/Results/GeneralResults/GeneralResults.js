import React from 'react'
import classes from './GeneralResults.module.css'


const GeneralResults = (props) => {
  return (
    <div className={classes.mainContent}>
        <div>
            <DetailsTable>
                <DetailsTableRow stat={{name:"completion time", ...props.generalStats.completion_time}}/>
                <DetailsTableRow stat={{name:"completion time", ...props.generalStats.total_cost}}/>
                <DetailsTableRow stat={{name:"completion time", ...props.generalStats.total_ressource_utilization}}/>
            </DetailsTable>
        </div>
        {renderLineChart}
    </div>
  )
}

export default GeneralResults