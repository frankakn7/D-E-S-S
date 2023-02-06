import React, { Fragment, useState } from "react";
import classes from "./Modal.module.css";
import ReactDOM from 'react-dom';

const Backdrop = (props) => {
  return <div className={classes.backdrop} onClick={props.onClose}></div>
}

const ModalOverlay = props => {
  return <div className={`${classes.modal} ${props.classes}`}>
      <div className={classes.content}>{props.children}</div>
  </div>
}

const portalElement = document.getElementById('overlays')

const Modal = (props) => {
return (
  <Fragment>
      {ReactDOM.createPortal(<Backdrop onClose={props.onClose} />, portalElement)}
      {ReactDOM.createPortal(<ModalOverlay classes={props.classes}>{props.children}</ModalOverlay>, portalElement)}
  </Fragment>
)
}

export default Modal
