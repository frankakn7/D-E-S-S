import React from 'react'
import Table from '../../../../interface/Table/Table'

const MachinesTable = (props) => {
  return (
    <div>
        <Table headers={["Id","Breakdown probability","Mean breakdown time", "Standard deviation"]}>
            {props.machines.map((machine) => (
                <tr key={machine.id}>
                    <td>{machine.id}</td>
                    <td>{machine.breakdown_probability}</td>
                    <td>{machine.mean}</td>
                    <td>{machine.standard_deviation}</td>
                </tr>
            ))}
        </Table>
    </div>
  )
}

export default MachinesTable