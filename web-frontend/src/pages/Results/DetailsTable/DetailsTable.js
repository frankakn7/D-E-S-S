import React from 'react'
import classes from './DetailsTable.module.css'

const DetailsTable = (props) => {
  return (
    <table className={`${classes.table} ${props.className}`}>
        {props.children}
    </table>
  )
}

export default DetailsTable