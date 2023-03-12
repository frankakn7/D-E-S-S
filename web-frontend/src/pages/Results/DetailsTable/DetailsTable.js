import React from 'react'
import classes from './DetailsTable.module.css'

/**
 * Simple table element with preconfigured styling
 * @param {Object} props all values passed to the element
 * @param {String} props.className extra css classes to be applied to the top table element 
 * @param {JSX.Element} props.children elements (table rows) to be placed inside the table
 * @returns 
 */
const DetailsTable = (props) => {
  return (
    <table className={`${classes.table} ${props.className}`}>
        {props.children}
    </table>
  )
}

export default DetailsTable