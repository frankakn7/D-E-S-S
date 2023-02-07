import React, { useState, useEffect } from "react";
import Button from "../../interface/Button/Button";
import classes from "./CompareButton.module.css";

const Compare = (props) => {
  const [notSelectedIds, setNotSelectedIds] = React.useState([]);
  const [selectedIds, setSelectedIds] = React.useState([]);

  useEffect(() => {
    setNotSelectedIds(props.simCases);
  }, [props.simCases]);

  const removeItem = (selectedSimCase) => {
    if(selectedIds.length == 2) {return;}
    setNotSelectedIds((previousList) =>
      previousList.filter((simCase) => simCase.id != selectedSimCase.id)
    );
    setSelectedIds((previousList) => [selectedSimCase, ...previousList]);
  };

  const removeSelectedItem = (selectedSimCase) => {
    setSelectedIds((previousList) =>
    previousList.filter((simCase) => simCase.id != selectedSimCase.id)
  );
  setNotSelectedIds((previousList) => [selectedSimCase, ...previousList]);
  }

  console.log(selectedIds);
  return (
    <div className={classes.content}>
      {notSelectedIds &&
        notSelectedIds.map((simCase) => (
          <Button className={classes.button} onClick={() => removeItem(simCase)}>{simCase.id}</Button>
        ))}
         {<h3>Selected Simulations</h3>}
      {selectedIds &&
        selectedIds.map((simCase) => (
          <Button className={classes.button} onClick={() => removeSelectedItem(simCase)}>{simCase.id}</Button>
        ))}
        <Button>Compare</Button>
    </div>
  );
};

export default Compare;
