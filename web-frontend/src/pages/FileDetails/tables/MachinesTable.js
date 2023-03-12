import React from 'react'
import Table from '../../../interface/Table/Table'

/**
 * displaying information about all machines in a table format
 * @param {Object} props all values passed to the object
 * @param {Array<Object>} props.machines a list of all machines
 * @returns 
 */
const MachinesTable = (props) => {
  return (
    <div>
        <Table headers={["Id","Breakdown probability","Mean breakdown time", "Standard deviation","cost / time", "Repair cost / time"]}>
            {props.machines.map((machine) => (
                <tr key={machine.id}>
                    <td>{machine.id}</td>
                    <td>{machine.breakdown_probability}</td>
                    <td>{machine.mean}</td>
                    <td>{machine.standard_deviation}</td>
                    <td>{machine.cost_per_time}</td>
                    <td>{machine.repair_cost_per_time}</td>
                </tr>
            ))}
        </Table>
    </div>
  )
}

export default MachinesTable