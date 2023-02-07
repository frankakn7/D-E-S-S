import React from 'react'
import Button from '../../Button/Button'
import Modal from '../Modal'
import classes from './ErrorModal.module.css'

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