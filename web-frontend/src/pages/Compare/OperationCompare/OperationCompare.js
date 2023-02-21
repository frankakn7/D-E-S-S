import React from 'react'
import OperationChartCompareBox from './OperationChartCompareBox/OperationChartCompareBox'
import classes from './OperationCompare.module.css'
import OperationsListHalf from './OperationsListHalf/OperationsListHalf'

/**
 * Comparison Tab for operation data of 2 simulations
 * @param {Object} props - all props passed to the element 
 * @param {Object} props.resultsOne - all the results of simulation number 1 of 2 
 * @param {Object} props.resultsTow - all the results of simulation number 2 of 2 
 * @param {String} props.simName1 - the name of the simulation number 1 of 2 
 * @param {String} props.simName2 - the name of the simulation number 2 of 2 
 * @returns 
 */
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