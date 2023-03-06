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
import CollapsableBox from "../../../../interface/CollapsableBox/CollapsableBox";
import DetailsTable from "../../DetailsTable/DetailsTable";
import DetailsTableRow from "../../DetailsTable/DetailsTableRow";
import { MachinesOperationsLength } from "../../ResultsCharts/ResultsCharts";
import classes from "./MachineResultsBox.module.css";

const MachineResultsBox = (props) => {
    const data = [
        {
            name: "utilized",
            value: parseFloat(
                (props.machine.utilisation.percent.mean).toFixed(2)),
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
            // fill: "#3C7FD0",
            fill: "url(#idlePattern)",
        },
        // { name: "unutilized", value: parseFloat((1 - props.machine.utilisation.percent.mean - props.machine.breakdowns.percent.mean).toFixed(2)), fill: "#d4d002" },
        {
            name: "breakdown",
            value: props.machine.breakdowns.percent.mean,
            // fill: "#f55f4e",
            fill: "url(#breakdownPattern)",
        },
    ];

    // const renderCustomizedLabel = ({
    //     cx,
    //     cy,
    //     midAngle,
    //     innerRadius,
    //     outerRadius,
    //     percent,
    //     index,
    // }) => {
    //     console.log(index)
    //     return `${(percent * 100).toFixed(0)}%`;
    // };
    const renderCustomizedLabel = (value) => {
        //return `${(percent * 100).toFixed(0)}%`;
        let newValue = value;
        if(newValue.name === "breakdown"){
            newValue.fill = "red"
        }else if(newValue.name === "unutilized"){
            newValue.fill = "black"
        }
        return <text {...newValue}>{`${(newValue.value * 100).toFixed(0)}%`}</text>;
    };

    const renderPieChart = (
        <ResponsiveContainer width="100%" height={200}>
            <PieChart margin={{top:20}}>
                <defs>
                    <pattern
                        id="idlePattern"
                        patternUnits="userSpaceOnUse"
                        width="5"
                        height="5"
                    >
                        <path
                            d="M0 5 L5 0"
                            fill="none"
                            stroke="black"
                            strokeWidth="1"
                        />
                    </pattern>
                    <pattern
                        id="breakdownPattern"
                        patternUnits="userSpaceOnUse"
                        width="5"
                        height="5"
                    >
                        <path
                            d="M0 5 L5 0"
                            fill="none"
                            stroke="red"
                            strokeWidth="1"
                        />
                        <path
                            d="M0 0 L5 5"
                            fill="none"
                            stroke="red"
                            strokeWidth="1"
                        />
                    </pattern>
                </defs>
                <Pie
                    data={data}
                    dataKey="value"
                    cx="50%"
                    label={renderCustomizedLabel}
                    labelLine={{stroke:"black"}}
                    cy="50%"
                    // fill={data.color}
                />
                <Tooltip />
            </PieChart>
        </ResponsiveContainer>
    );
    return (
        <Box
            titleText={<p>{`Machine "${props.machine.id}" ${props.extraTitleText ? props.extraTitleText : ""}`}</p>}
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
                                name: "breakdowns downtime",
                                ...props.machine.breakdowns
                                    .total_downtime,
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
            <CollapsableBox titleText={"Machine Operations"}>
                <div className={classes.operationsChartBoxContent}>
                    <MachinesOperationsLength machine={props.machine} />
                </div>
            </CollapsableBox>
        </Box>
    );
};

export default MachineResultsBox;
