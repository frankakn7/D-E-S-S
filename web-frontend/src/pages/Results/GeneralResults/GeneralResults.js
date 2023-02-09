import React from "react";
import { Bar, BarChart, Pie, PieChart, ResponsiveContainer, Tooltip, XAxis, YAxis } from "recharts";
import DetailsTable from "../DetailsTable/DetailsTable";
import DetailsTableRow from "../DetailsTable/DetailsTableRow";
import classes from "./GeneralResults.module.css";

const GeneralResults = (props) => {
    const utilisationData = [
        {
            name: "utilized",
            value: props.allResults.general_stats.total_ressource_utilization.mean,
            fill: "#62c46c",
        },
        {
            name: "unutilized",
            value: 1 - props.allResults.general_stats.total_ressource_utilization.mean,
            fill: "#3C7FD0",
        },
    ];

    const machineUtilisationData = props.allResults.machines.map((machine) => 
      ({
        name: `${machine.id}`,
        value: machine.utilisation.percent.mean * 100,
      })
    )

    const renderCustomizedLabel = ({
        cx,
        cy,
        midAngle,
        innerRadius,
        outerRadius,
        value,
        index,
    }) => {
        return `${(value * 100).toFixed(0)}%`;
    };

    const renderUtilizationPieChart = (
        <ResponsiveContainer width="100%" height={200}>
            <PieChart>
                <Pie
                    data={utilisationData}
                    dataKey="value"
                    cx="50%"
                    label={renderCustomizedLabel}
                    cy="50%"
                    fill={utilisationData.color}
                />
                <Tooltip />
            </PieChart>
        </ResponsiveContainer>
    );

    const renderMachineUtilizationBars = (
      <ResponsiveContainer width="100%" height={200}>
        <BarChart
          data={machineUtilisationData}
          margin={{
            top: 20,
            right: 30,
            left: 20,
            bottom: 5,
          }}
        >
          <XAxis dataKey="name" />
          <YAxis domain={[0,100]}/>
          <Tooltip />
          {/* <Legend /> */}
          <Bar dataKey="value" fill="#8884d8" barSize={30}/>
        </BarChart>
      </ResponsiveContainer>
    )

    return (
        <div className={classes.mainContent}>
            <div className={classes.leftData}>
                <DetailsTable>
                    <DetailsTableRow
                        stat={{
                            name: "completion time",
                            ...props.allResults.general_stats.completion_time,
                        }}
                    />
                    <DetailsTableRow
                        stat={{
                            name: "total cost",
                            ...props.allResults.general_stats.total_cost,
                        }}
                    />
                    <DetailsTableRow
                        stat={{
                            name: "total ressource utilization",
                            ...props.allResults.general_stats.total_ressource_utilization,
                        }}
                        percentage={true}
                    />
                </DetailsTable>
            </div>
            <div className={classes.charts}>
              <div className={classes.chart}>
                  <p>Total Ressource utilization</p>
                  {renderUtilizationPieChart}
              </div>
              <div className={classes.chart} style={{width:300}}>
                  <p>Machine utilisation (%)</p>
                  {renderMachineUtilizationBars}
              </div>
            </div>
        </div>
    );
};

export default GeneralResults;
