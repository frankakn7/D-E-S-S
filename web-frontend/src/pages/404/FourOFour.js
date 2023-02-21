import React from 'react'
import { Link } from 'react-router-dom'
import classes from './FourOFour.module.css'

/**
 * A default page displaying a 404 message for pages that dont exist
 * @returns 
 */
const FourOFour = () => {
  return (
    <div className={classes.content}>
        <h2>404: Page not Found</h2>
        <p>This page does not seem to exist... </p>
        <Link to="/">Return to Homepage</Link>
    </div>
  )
}

export default FourOFour