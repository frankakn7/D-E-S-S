import React from "react";
import { LineChart, Line, Tooltip ,PieChart, Pie } from "recharts";
import Box from "../../../interface/Box/Box";
import Button from "../../../interface/Button/Button";
import DetailsTable from "../DetailsTable/DetailsTable";
import DetailsTableRow from "../DetailsTable/DetailsTableRow";
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
                    <DetailsTable className={classes.detailsTable}>
                        <DetailsTableRow stat={{name:"utilization percent", ...machine.utilisation.percent}}/>
                        <DetailsTableRow stat={{name:"utilization time", ...machine.utilisation.time}}/>
                        <DetailsTableRow stat={{name:"repair cost", ...machine.repair_cost}}/>
                        <DetailsTableRow stat={{name:"operational cost", ...machine.operational_cost}}/>
                        <DetailsTableRow stat={{name:"breakdowns downtime", ...machine.breakdowns.downtime}}/>
                        <DetailsTableRow stat={{name:"breakdowns occurance", ...machine.breakdowns.occurrence}}/>
                        <DetailsTableRow stat={{name:"breakdowns percent", ...machine.breakdowns.percent}}/>
                    </DetailsTable>

                    <div className={classes.PieChart}>{renderLineChart}</div>
                </Box>
            ))}
        </div>
    );
};

export default MachineResults;
