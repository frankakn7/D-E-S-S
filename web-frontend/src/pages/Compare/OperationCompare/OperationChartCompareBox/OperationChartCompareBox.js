import React from 'react'
import Box from '../../../../interface/Box/Box'
import { OperationsLengthChart } from '../../../Results/ResultsCharts/ResultsCharts'
import classes from './OperationChartCompareBox.module.css'

/**
 * A wide box containing operations length charts. designed to compare 2 on top of eachother
 * @param {Object} props - all props passed to this element
 * @param {String} props.simName - the name of the simulation the chart belongs to
 * @param {Object} props.allResults - all the results of the simulation
 * @returns 
 */
const OperationChartCompareBox = (props) => {
  return (
    <Box titleText={<p>{props.simName}</p>}>
        <div className={classes.boxContent}>
            <OperationsLengthChart allResults={props.allResults}/>
        </div>
    </Box>
  )
}

export default OperationChartCompareBox