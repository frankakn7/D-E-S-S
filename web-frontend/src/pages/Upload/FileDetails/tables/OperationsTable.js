import React from 'react'
import Table from '../../../../interface/Table/Table'

const OperationsTable = (props) => {
  return (
    <div>
        <Table headers={["Id","Job id", "Machine id", "Predecessor id", "Duration"]}>
            {props.operations.map(operation => (
                <tr key={operation.id}>
                    <td>{operation.id}</td>
                    <td>{operation.job_id}</td>
                    <td>{operation.machine_id}</td>
                    <td>{operation.predecessor || "-"}</td>
                    <td>{operation.duration}</td>
                </tr>
            ))}
        </Table>
    </div>
  )
}

export default OperationsTable