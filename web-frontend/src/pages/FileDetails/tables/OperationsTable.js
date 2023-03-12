import React from 'react'
import Table from '../../../interface/Table/Table'

/**
 * displaying information about all operations in a table format
 * @param {Object} props all values passed to the object
 * @param {Array<Object>} props.operations a list of all operations
 * @returns 
 */
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
                    <td>{operation.conditional_preds ? operation.conditional_preds.map((pred) => pred+", ") : "-"}</td>
                </tr>
            ))}
        </Table>
    </div>
  )
}

export default OperationsTable