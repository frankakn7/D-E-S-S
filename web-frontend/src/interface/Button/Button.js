import React from 'react'
import classes from './Button.module.css'

/**
 * Default button styling for whole application. 
 * Includes styling for hovering, click interaction and disabling
 * @param {Object} props - all props passed to the button element
 * @param {String} props.className - custom classes added to the button element
 * @param {Function} props.onClick - function to be executed when button is clicked
 * @param {Boolean} props.disabled - sets the disabled status of the button (if true disabled styling is applied)
 * @param {JSX.Element} props.children - elements that can be put inside the button (e.g. <FontAwesomeIcon /> elements)
 * @returns 
 */
const Button = (props) => {
  return (
    <button className={`${classes.button} ${props.className}`} onClick={props.onClick} disabled={props.disabled}>
        {props.children}
    </button>
  )
}

export default Button