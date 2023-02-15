import React from 'react'
import OperationChartCompareBox from './OperationChartCompareBox/OperationChartCompareBox'
import classes from './OperationCompare.module.css'
import OperationsListHalf from './OperationsListHalf/OperationsListHalf'

const OperationCompare = (props) => {
  return (
    <div>
      <OperationChartCompareBox allResults={props.resultsOne} simName={props.simName1}/>
      <OperationChartCompareBox allResults={props.resultsTwo} simName={props.simName2}/>
      <div className={classes.operationsLists}>
        <OperationsListHalf allResults={props.resultsOne} className={classes.half} simName={props.simName1}/>
        <OperationsListHalf allResults={props.resultsTwo} className={classes.half} simName={props.simName2}/>
      </div>
    </div>
  )
}

export default OperationCompare