import React from 'react'
import classes from './Button.module.css'

const Button = (props) => {
  return (
    <button className={`btn-default-style ${classes.button} ${props.className}`} onClick={props.onClick}>
        {props.children}
    </button>
  )
}

export default Button