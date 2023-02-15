import React from 'react'
import Box from '../../../../interface/Box/Box'
import { OperationsLengthChart } from '../../../Results/ResultsCharts/ResultsCharts'
import classes from './OperationChartCompareBox.module.css'

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