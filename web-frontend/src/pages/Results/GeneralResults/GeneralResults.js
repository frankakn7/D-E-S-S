import React from 'react'
import { LineChart, Line, PieChart, Pie } from 'recharts';
import classes from './GeneralResults.module.css'


const GeneralResults = (props) => {
    const data = [{name: 'Page A', uv: 400, pv: 2400, amt: 2400},{name: 'Page B', uv: 200, pv: 2400, amt: 2400},{name: 'Page C', uv: 500, pv: 2400, amt: 2400}];
    const data02 = [
        { name: 'A1', value: 100 },
        { name: 'A2', value: 300 },
        { name: 'B1', value: 100 },
        { name: 'B2', value: 80 }
      ];
    const renderLineChart = (
        <PieChart width={300} height={300}>
        {/* <Line type="monotone" dataKey="uv" stroke="#8884d8" /> */}
        <Pie data={data02} dataKey="value" cx="50%" cy="50%" fill="#8884d8" />
      </PieChart>
    );
  return (
    <div className={classes.mainContent}>
        {renderLineChart}
        <div>
        <p>Mean Completion Time: {props.generalStats.completion_time.mean}</p>
        <p>Mean Total Cost: {props.generalStats.total_cost.mean}</p>
        <p>Mean Total ressource utilization: {props.generalStats.total_ressource_utilization.mean}</p>
        </div>
    </div>
  )
}

export default GeneralResults