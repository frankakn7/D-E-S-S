import React from "react";
import classes from "./Box.module.css";

const Box = (props) => {
  return (
    <div className={`${classes.box} ${props.className}`}>
      <div className={classes.title}>
        {props.titleText}
      </div>
      <div className={classes.content}>{props.children}</div>
    </div>
  );
};

export default Box;
