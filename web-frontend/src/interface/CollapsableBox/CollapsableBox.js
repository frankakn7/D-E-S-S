import { faAngleDown, faAngleUp } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import React, { useState } from 'react'
import classes from "./CollapsableBox.module.css"

const CollapsableBox = (props) => {

    const [open, setOpen] = useState(false)


  return (
    <div className={classes.box}>
        <div className={classes.title} onClick={() => {setOpen((prevState) => !prevState)}}>
            <p>{props.titleText}</p>
            <div className={classes.arrowIcon}>
                {!open && <FontAwesomeIcon icon={faAngleDown} />}
                {open && <FontAwesomeIcon icon={faAngleUp} />}
            </div>
        </div>
        {open && <div className={classes.content}>
            {props.children}
        </div>}
    </div>
  )
}

export default CollapsableBox