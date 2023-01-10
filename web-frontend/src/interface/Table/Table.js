import React from 'react'
import classes from './Table.module.css'

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