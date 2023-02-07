import React from 'react'
import Box from '../../../interface/Box/Box'

const JobResults = (props) => {
    console.log(props.jobs)
  return (
    <div>
        {props.jobs.map((job) => (
            <Box key={job.id} titleText={<p>{`Job "${job.id}`}</p>}>
                <p>Completion time: {job.completion_time.mean}</p>
                <p>lateness: {job.lateness.mean}</p>
                <p>lateness cost: {job.lateness_cost.mean}</p>
            </Box>
        ))}
    </div>
  )
}

export default JobResults