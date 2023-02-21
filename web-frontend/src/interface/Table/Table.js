import React from 'react'
import classes from './Table.module.css'

/**
 * Default Table element with headers defined in props and elements to be placed inside the tbody as children
 * @param {Object} props - all props passed to this element 
 * @param {Array<String>} props.headers - list of strings for headers to be displayed
 * @param {JSX.Element} props.children - elements to be displayed inside the table (best: <tr><td>...</tr>)
 * @returns 
 */
const Table = (props) => {
    return (
        <table className={classes.table}>
            <thead>
                <tr>
                    {props.headers.map((header) => (
                        <th key={header}>{header}</th>
                    ))}
                </tr>
            </thead>
            <tbody>
                {props.children}
            </tbody>
        </table>
    )
}

export default Table