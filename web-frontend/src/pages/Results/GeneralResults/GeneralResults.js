import React from 'react'
import DetailsTable from '../DetailsTable/DetailsTable'
import DetailsTableRow from '../DetailsTable/DetailsTableRow'
import classes from './GeneralResults.module.css'


const GeneralResults = (props) => {
  return (
    <div className={classes.mainContent}>
        <div>
            <DetailsTable>
                <DetailsTableRow stat={{name:"completion time", ...props.generalStats.completion_time}}/>
                <DetailsTableRow stat={{name:"total cost", ...props.generalStats.total_cost}}/>
                <DetailsTableRow stat={{name:"total ressource utilization", ...props.generalStats.total_ressource_utilization}} percentage={true}/>
            </DetailsTable>
        </div>
    </div>
  )
}

export default GeneralResults