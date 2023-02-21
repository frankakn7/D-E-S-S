import React, { Fragment, useState } from "react";
import classes from "./Modal.module.css";
import ReactDOM from 'react-dom';

/**
 * Backdrop element which covers background with transparent dark color. When clicked will execute the onClose function
 * @param {Object} props - All props paassed to this element
 * @param {Function} props.onClose - function to be executed when backdrop is clicked (usually function closing the modal)
 * @returns 
 */
const Backdrop = (props) => {
  return <div className={classes.backdrop} onClick={props.onClose}></div>
}

/**
 * Simple white box with no styling (the actual visable modal)
 * @param {Object} props - all props passed to this element 
 * @param {String} props.classes - custom classes that should be applied to the top div of the modal 
 * @param {JSX.Element} props.children - elements to be placed inside the modal 
 * @returns 
 */
const ModalOverlay = props => {
  return <div className={`${classes.modal} ${props.classes}`}>
      <div className={classes.content}>{props.children}</div>
  </div>
}

const portalElement = document.getElementById('overlays')

/**
 * Whole modal element with backdrop and pure white box. 
 * Elements will be placed inside white box and modal Overlays *all* content on page
 * @param {Object} props - all props passed to this element
 * @param {Function} props.onClose - function to be executed when backdrop is clicked (usually function closing the modal)
 * @param {String} props.classes - custom classes that should be applied to the top div of the modal element
 * @param {JSX.Element} props.children - elements to be placed inside the modal 
 * @returns 
 */
const Modal = (props) => {
return (
  <Fragment>
      {ReactDOM.createPortal(<Backdrop onClose={props.onClose} />, portalElement)}
      {ReactDOM.createPortal(<ModalOverlay classes={props.classes}>{props.children}</ModalOverlay>, portalElement)}
  </Fragment>
)
}

export default Modal
