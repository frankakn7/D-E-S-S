import React from 'react'
import Table from '../../../interface/Table/Table'

/**
 * displaying information about all jobs in a table format
 * @param {Object} props all values passed to the object
 * @param {Array<Object>} props.jobs a list of all jobs
 * @returns 
 */
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