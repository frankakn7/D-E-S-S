import React from 'react'
import Table from '../../../../interface/Table/Table'

const JobsTable = (props) => {
  return (
    <div>
        <Table headers={["Id","Release-time","Priority"]}>
            {props.jobs.map((job) => (
                <tr key={job.id}>
                    <td>{job.id}</td>
                    <td>{job.releaseTime}</td>
                    <td>{job.priority}</td>
                </tr>
            ))}
        </Table>
    </div>
  )
}

export default JobsTable