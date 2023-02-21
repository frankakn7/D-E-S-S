import React from 'react'
import Button from '../../Button/Button'
import Modal from '../Modal'
import classes from './ErrorModal.module.css'

/**
 * A modal designed to display errors. Simply contains red text and a close button
 * @param {Object} props - All props passed to the element
 * @param {Function} props.onClose - function to be executed when the close button or background is clicked (usually a function hiding the modal)
 * @param {JSX.Element} props.children - elements to be placed inside the error modal above the cancel button (usually text describing the error) 
 * @returns 
 */
const ErrorModal = (props) => {
  return (
    <Modal onClose={props.onClose}>
        <div className={classes.top}>
            <h2>Error</h2>
        </div>
        <div className={classes.content}>
            <div className={classes.errorText}>
                {props.children}
            </div>
            <Button onClick={props.onClose}>Close</Button>
        </div>
    </Modal>
  )
}

export default ErrorModal