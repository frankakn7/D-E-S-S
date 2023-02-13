import React from 'react'
import Box from '../../../interface/Box/Box'
import JobsTable from '../../FileDetails/tables/JobsTable'
import DetailsTable from '../DetailsTable/DetailsTable'
import DetailsTableRow from '../DetailsTable/DetailsTableRow'
import { JobCompletionTimeChart, JobCostChart, JobLatenessChart } from '../ResultsCharts/ResultsCharts'
import classes from './JobResults.module.css'

const JobResults = (props) => {
  return (
    <div className={classes.content}>
      <Box titleText={<p>Jobs Summary</p>}>
        <div className={classes.summaryBox}>
            <JobCompletionTimeChart allResults={props.allResults}/>
            <JobLatenessChart allResults={props.allResults} />
            <JobCostChart allResults={props.allResults} />
        </div>
      </Box>
      <div className={classes.jobBoxes}>
          {props.allResults.jobs.map((job) => (
            <Box key={job.id} className={classes.jobBox} titleText={<p>{`Job "${job.id}`}</p>}>
                  <DetailsTable>
                      <DetailsTableRow stat={{name:"completion time", ...job.completion_time}}/>
                      <DetailsTableRow stat={{name:"lateness", ...job.lateness}}/>
                      <DetailsTableRow stat={{name:"lateness cost", ...job.lateness_cost}}/>
                  </DetailsTable>
              </Box>
          ))}
      </div>
          </div>
  )
}

export default JobResults