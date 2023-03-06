import React from 'react'
import Table from '../../../interface/Table/Table'

const JobsTable = (props) => {
  return (
    <div>
        <Table headers={["Id","Release-time","Due Time"]}>
            {props.jobs.map((job) => (
                <tr key={job.id}>
                    <td>{job.id}</td>
                    <td>{job.release_date}</td>
                    <td>{job.due_date}</td>
                </tr>
            ))}
        </Table>
    </div>
  )
}

export default JobsTable