import React from 'react'
import Box from '../../../interface/Box/Box'
import DetailsTable from '../DetailsTable/DetailsTable'
import DetailsTableRow from '../DetailsTable/DetailsTableRow'

const OperationResults = (props) => {
  return (
    <div>
        {props.operations.map((operation) => (
            <Box key={operation.id} titleText={<p>{`Operation "${operation.id}"`}</p>}>
                <DetailsTable>
                    <DetailsTableRow stat={{name:"length", ...operation.length}}/>
                </DetailsTable>
            </Box>
        ))}
    </div>
  )
}

export default OperationResults