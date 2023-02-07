import React from "react";
import { LineChart, Line, Tooltip ,PieChart, Pie } from "recharts";
import Box from "../../../interface/Box/Box";
import Button from "../../../interface/Button/Button";
import classes from "./MachineResults.module.css";

const MachineResults = (props) => {

  const data = [
    { name: "utilized", value: 0.7, fill:'#5a99e5'},
    { name: "unutilized", value: 0.3, fill:'#3C7FD0'},
  ];
  
  const renderLineChart = (
    <PieChart width={300} height={300}>
      <Pie data={data} dataKey="value" cx="50%" label cy="50%" fill={data.color} />
      <Tooltip />
    </PieChart>
  );
  return (
    <div className={classes.mainContainer}>
      {props.machines.map((machine) => (
        <Box
          key={machine.id}
          titleText={<p>{`Machine "${machine.id}"`}</p>}
          className={classes.machineBox}
        >
          <button>
            utilization percent: {machine.utilisation.percent.mean}
          </button>
          <p>utilization time: {machine.utilisation.time.mean}</p>
          <p>repair cost: {machine.repair_cost.mean}</p>
          <p>operational cost: {machine.operational_cost.mean}</p>
          <p>Breakdowns downtime: {machine.breakdowns.downtime.mean}</p>
          <p>Breakdowns occurance: {machine.breakdowns.occurrence.mean}</p>
          <p>Breakdowns percent: {machine.breakdowns.percent.mean}</p>

          <div className={classes.PieChart}>{renderLineChart}</div>
        </Box>
      ))}
    </div>
  );
};

export default MachineResults;
