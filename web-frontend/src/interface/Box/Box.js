import React from "react";
import classes from "./Box.module.css";

/**
 * A Box element with a header containing a title and a blue margin surrounding the content placed inside the Box element
 * @param {Object} props - all te props passed to the Box component
 * @param {String} props.className - custom classes added to the top div of the Box component
 * @param {String} props.titleClassName - custom classes added to the title div of the Box component
 * @param {JSX.Element} props.titleText - JSX element to be placed as title inside the title div of the box element
 * @param {JSX.Element} props.children - Content to be surroundend by the Box
 * @returns 
 */
const Box = (props) => {
  return (
    <div className={`${classes.box} ${props.className}`}>
      <div className={`${classes.title} ${props.titleClassName}`}>
        {props.titleText}
      </div>
      <div className={classes.content}>{props.children}</div>
    </div>
  );
};

export default Box;
