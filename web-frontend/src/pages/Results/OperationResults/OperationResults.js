import React from 'react'
import Box from '../../../interface/Box/Box'

const OperationResults = (props) => {
  return (
    <div>
        {props.operations.map((operation) => (
            <Box key={operation.id} titleText={<p>{`Operation "${operation.id}"`}</p>}>
                <p>Length: {operation.length.mean}</p>
            </Box>
        ))}
    </div>
  )
}

export default OperationResults