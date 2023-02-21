import { faAngleDown, faAngleUp } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import React, { useState } from 'react'
import classes from "./CollapsableBox.module.css"

/**
 * Offers a Box component (similar to standard Box component) which is collapsable ad expandable
 * @param {Object} props - All props passed to this element
 * @param {JSX.Element} props.titleText - title text placed inside a <p> element inside the title part (always visiable)
 * @param {JSX.Element} props.children - elements to be contained inside the collapsable box
 * @returns 
 */
const CollapsableBox = (props) => {

    const [open, setOpen] = useState(false)


  return (
    <div className={classes.box}>
        <div className={classes.title} onClick={() => {setOpen((prevState) => !prevState)}}>
            <p>{props.titleText}</p>
            <div className={`${classes.arrowIcon} ${open ? classes.open : ""}`}>
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