import React from 'react'
import Table from '../../../interface/Table/Table'

const OperationsTable = (props) => {
  return (
    <div>
        <Table headers={["Id","Job id", "Machine id", "Machine Predecessor id", "duration", "Conditional Operation Ids"]}>
            {props.operations.map(operation => (
                <tr key={operation.id}>
                    <td>{operation.id}</td>
                    <td>{operation.job_id}</td>
                    <td>{operation.machine_id}</td>
                    <td>{operation.machine_pred || "-"}</td>
                    <td>{operation.duration}</td>
                    <td>{operation.conditional_preds || "-"}</td>
                </tr>
            ))}
        </Table>
    </div>
  )
}

export default OperationsTable