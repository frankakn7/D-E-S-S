import React from 'react'
import Box from '../../../interface/Box/Box'
import DetailsTable from '../DetailsTable/DetailsTable'
import DetailsTableRow from '../DetailsTable/DetailsTableRow'

const JobResults = (props) => {
  return (
    <div>
        {props.jobs.map((job) => (
            <Box key={job.id} titleText={<p>{`Job "${job.id}`}</p>}>
                <DetailsTable>
                    <DetailsTableRow stat={{name:"completion time", ...job.completion_time}}/>
                    <DetailsTableRow stat={{name:"lateness", ...job.lateness}}/>
                    <DetailsTableRow stat={{name:"lateness cost", ...job.lateness_cost}}/>
                </DetailsTable>
            </Box>
        ))}
    </div>
  )
}

export default JobResults