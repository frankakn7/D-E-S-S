import React from "react";
import {
    LineChart,
    Line,
    Tooltip,
    PieChart,
    Pie,
    ResponsiveContainer,
} from "recharts";
import Box from "../../../../interface/Box/Box";
import DetailsTable from "../../DetailsTable/DetailsTable";
import DetailsTableRow from "../../DetailsTable/DetailsTableRow";
import classes from "./MachineResultsBox.module.css";

const MachineResultsBox = (props) => {
    const data = [
        {
            name: "utilized",
            value: props.machine.utilisation.percent.mean,
            fill: "#5a99e5",
        },
        // { name: "utilized", value: props.machine.utilisation.percent.mean, fill: "#50ba47" },
        {
            name: "unutilized",
            value: parseFloat(
                (
                    1 -
                    props.machine.utilisation.percent.mean -
                    props.machine.breakdowns.percent.mean
                ).toFixed(2)
            ),
            fill: "#3C7FD0",
        },
        // { name: "unutilized", value: parseFloat((1 - props.machine.utilisation.percent.mean - props.machine.breakdowns.percent.mean).toFixed(2)), fill: "#d4d002" },
        {
            name: "breakdown",
            value: props.machine.breakdowns.percent.mean,
            fill: "#f55f4e",
        },
    ];

    const renderCustomizedLabel = ({ cx, cy, midAngle, innerRadius, outerRadius, percent, index }) => {
        return `${(percent * 100).toFixed(0)}%`
    }

    const renderPieChart = (
        <ResponsiveContainer width="100%" height={200}>
            <PieChart>
                <Pie
                    data={data}
                    dataKey="value"
                    cx="50%"
                    label={renderCustomizedLabel}
                    cy="50%"
                    fill={data.color}
                />
                <Tooltip />
            </PieChart>
        </ResponsiveContainer>
    );
    return (
        <Box
            titleText={<p>{`Machine "${props.machine.id}"`}</p>}
            className={props.className}
        >
            <div className={classes.boxContent}>
                <div className={classes.tableContainer}>
                    <DetailsTable>
                        <DetailsTableRow
                            stat={{
                                name: "utilization percent",
                                ...props.machine.utilisation.percent,
                            }}
                            percentage={true}
                        />
                        <DetailsTableRow
                            stat={{
                                name: "utilization time",
                                ...props.machine.utilisation.time,
                            }}
                        />
                        <DetailsTableRow
                            stat={{
                                name: "repair cost",
                                ...props.machine.repair_cost,
                            }}
                        />
                        <DetailsTableRow
                            stat={{
                                name: "operational cost",
                                ...props.machine.operational_cost,
                            }}
                        />
                        <DetailsTableRow
                            stat={{
                                name: "breakdowns downtime per breakdown",
                                ...props.machine.breakdowns.downtime_per_breakdown,
                            }}
                        />
                        <DetailsTableRow
                            stat={{
                                name: "breakdowns occurance",
                                ...props.machine.breakdowns.occurrence,
                            }}
                        />
                        <DetailsTableRow
                            stat={{
                                name: "breakdowns percent",
                                ...props.machine.breakdowns.percent,
                            }}
                            percentage={true}
                        />
                    </DetailsTable>
                </div>
                <div className={classes.right}>
                    <div className={classes.PieChart}>
                        <p>Machine utilisation</p>
                        {renderPieChart}
                    </div>
                </div>
            </div>
                <p>OPERATIONS GRAPH</p>
        </Box>
    );
};

export default MachineResultsBox;
