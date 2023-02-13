import {
    Bar,
    BarChart,
    Cell,
    Label,
    Legend,
    Pie,
    PieChart,
    ResponsiveContainer,
    Tooltip,
    XAxis,
    YAxis,
} from "recharts";
import React from "react";
import classes from "./ResultsCharts.module.css";

const COLOR_DATABASE = [
    "#fa8072",
    "#c0d6e4",
    "#20b2aa",
    "#003366",
    "#3C7FD0",
    "#8884d8",
    "#5a918a",
    "#fccda6",
    "#8b8b6f",
    "#9c335b",
    "#4c5a91",
    "#BA7BA1",
    "#93E1D8",
    "#856084",
    "#C3DBC5",
    "#73A580",
];

// const randomColor = (() => {
//     "use strict";

//     const randomInt = (min, max) => {
//         return Math.floor(Math.random() * (max - min + 1)) + min;
//     };

//     return () => {
//         var h = randomInt(0, 360);
//         var s = randomInt(42, 98);
//         var l = randomInt(40, 90);
//         return hslToHex(h, s, l);
//     };
// })();

// function hslToHex(h, s, l) {
//     l /= 100;
//     const a = (s * Math.min(l, 1 - l)) / 100;
//     const f = (n) => {
//         const k = (n + h / 30) % 12;
//         const color = l - a * Math.max(Math.min(k - 3, 9 - k, 1), -1);
//         return Math.round(255 * color)
//             .toString(16)
//             .padStart(2, "0"); // convert to Hex and prefix "0" if needed
//     };
//     return `#${f(0)}${f(8)}${f(4)}`;
// }

// const BarWithBorder = () => {
//     return (props) => {
//         const { fill, x, y, width, height } = props;

//         return (
//             <g>
//                 <rect
//                     x={x}
//                     y={y}
//                     width={width}
//                     height={height}
//                     fill={fill}
//                 />
//             </g>
//         );
//     };
// };

const TotalRessourceUtilisationPieChart = (props) => {
    const utilisationData = [
        {
            name: "utilized",
            value: props.allResults.general_stats.total_ressource_utilization
                .mean,
            fill: "#62c46c",
        },
        {
            name: "unutilized",
            value:
                1 -
                props.allResults.general_stats.total_ressource_utilization.mean,
            fill: "#3C7FD0",
        },
    ];

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

    return (
        <div className={`${classes.chart} ${props.className}`}>
            <p>Total Ressource utilization</p>
            {renderUtilizationPieChart}
        </div>
    );
};

const TotalCostPieChart = (props) => {
    const costData = [
        {
            name: "lateness cost",
            value: props.allResults.jobs.reduce(
                (partialSum, job) => partialSum + job.lateness_cost.mean,
                0
            ),
            fill: "#e0cc68",
        },
        {
            name: "operational cost",
            value: props.allResults.machines.reduce(
                (partialSum, machine) =>
                    partialSum + machine.operational_cost.mean,
                0
            ),
            fill: "#3C7FD0",
        },
        {
            name: "repair cost",
            value: props.allResults.machines.reduce(
                (partialSum, machine) => partialSum + machine.repair_cost.mean,
                0
            ),
            fill: "#f55f4e",
        },
    ];

    const renderTotalCostPieChart = (
        <ResponsiveContainer width="100%" height={200}>
            <PieChart>
                <Pie
                    data={costData}
                    dataKey="value"
                    cx="50%"
                    cy="50%"
                    label
                    // fill={costData.color}
                />
                <Tooltip />
            </PieChart>
        </ResponsiveContainer>
    );

    return (
        <div className={`${classes.chart} ${props.className}`}>
            <p>Total Costs</p>
            {renderTotalCostPieChart}
        </div>
    );
};

const MachineUtilisationBarChart = (props) => {
    const machineUtilisationData = props.allResults.machines.map((machine) => ({
        name: `${machine.id}`,
        utilisation: machine.utilisation.percent.mean * 100,
    }));

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
                <YAxis domain={[0, 100]} />
                <Tooltip labelFormatter={(name) => "Machine " + name} />
                {/* <Legend /> */}
                {/* <Bar dataKey="utilisation" fill="#8884d8" stroke="black" /> */}
                <Bar dataKey="utilisation" fill="#8884d8" />
            </BarChart>
        </ResponsiveContainer>
    );

    return (
        <div className={`${classes.chart} ${props.className}`}>
            <p>Machine utilisation (%)</p>
            {renderMachineUtilizationBars}
        </div>
    );
};

const MachineMakespanBarChart = (props) => {
    const JOB_COLORS = Object.fromEntries(
        props.allResults.jobs.map((job, index) => [
            job.id,
            COLOR_DATABASE[job.id % COLOR_DATABASE.length],
        ])
    );

    const machineMakespanData = props.allResults.machines.map((machine) => {
        const dataObj = {
            name: `${machine.id}`,
            breakdown: machine.breakdowns.downtime_per_breakdown.mean,
            idle: machine.utilisation.idle_time.mean,
        };
        for (const op of machine.operations) {
            dataObj[op.id] = op.length.mean;
        }
        return dataObj;
    });

    const legendPayload = () => {
        let legend = props.allResults.jobs.map((job, index) => ({
            id: job.id,
            type: "square",
            value: `Job "${job.id}"`,
            color: JOB_COLORS[job.id],
        }));
        legend.push({
            id: "breakdown",
            type: "square",
            value: "Breakdown",
            color: "url(#breakdownPattern)",
        });
        legend.push({
            id: "idle",
            type: "square",
            value: "idle",
            color: "url(#idlePattern)",
        });
        return legend;
    };

    const renderMachineMakespanChart = (
        <ResponsiveContainer width="100%" height={200}>
            <BarChart
                data={machineMakespanData}
                margin={{
                    top: 20,
                    right: 0,
                    left: 0,
                    bottom: 5,
                }}

            >
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
                            stroke-width="1"
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
                <XAxis dataKey="name" />
                <YAxis />
                <Tooltip labelFormatter={(name) => "Machine " + name} />
                <Legend
                    payload={legendPayload()}
                    formatter={(value, entry) => (
                        <span style={{ color: "black" }}>{value}</span>
                    )}
                    align={"right"}
                    layout={"vertical"}
                    verticalAlign={"middle"}
                />
                <Bar
                    key={"breakdowns"}
                    dataKey={"breakdown"}
                    stackId="1"
                    fill="url(#breakdownPattern)"
                    // shape={BarWithBorder()}
                    // stroke="black"
                />
                <Bar
                    key={"idle"}
                    dataKey={"idle"}
                    stackId="1"
                    fill="url(#idlePattern)"
                    // shape={BarWithBorder()}
                    // stroke="black"
                />
                {props.allResults.machines.map((machine, mIndex) =>
                    machine.operations.map((op, opIndex) => (
                        <Bar
                            key={op.id}
                            dataKey={op.id}
                            stackId="1"
                            fill={JOB_COLORS[op.job_id]}
                            // stroke="black"
                        />
                    ))
                )}
            </BarChart>
        </ResponsiveContainer>
    );

    return (
        <div className={`${classes.chart} ${props.className}`}>
            <p>Machine Makespan</p>
            {renderMachineMakespanChart}
        </div>
    );
};

const MachineBreakdownIdleChart = (props) => {
    const machineBreakdownIdleData = props.allResults.machines.map(
        (machine) => {
            const dataObj = {
                name: `${machine.id}`,
                breakdown: machine.breakdowns.downtime_per_breakdown.mean,
                idle: machine.utilisation.idle_time.mean,
            };
            return dataObj;
        }
    );

    const legendPayload = () => {
        let legend = [
            {
                id: "breakdown",
                type: "square",
                value: "Breakdown",
                color: "url(#breakdownPattern)",
            },
            {
                id: "idle",
                type: "square",
                value: "idle",
                color: "url(#idlePattern)",
            },
        ];
        return legend;
    };

    const renderMachineBreakdownIdleChart = (
        <ResponsiveContainer width="100%" height={200}>
            <BarChart
                data={machineBreakdownIdleData}
                margin={{
                    top: 20,
                    right: 0,
                    left: 0,
                    bottom: 5,
                }}
            >
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
                            stroke-width="1"
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
                <XAxis dataKey="name" />
                <YAxis />
                <Tooltip labelFormatter={(name) => "Machine " + name} />
                <Legend
                    payload={legendPayload()}
                    formatter={(value, entry) => (
                        <span style={{ color: "black" }}>{value}</span>
                    )}
                    align={"right"}
                    layout={"vertical"}
                    verticalAlign={"middle"}
                />
                <Bar
                    key={"breakdowns"}
                    dataKey={"breakdown"}
                    barCategoryGap={"0"}
                    // stackId="1"
                    fill="url(#breakdownPattern)"
                    // shape={BarWithBorder()}
                    // stroke="black"
                />
                <Bar
                    key={"idle"}
                    dataKey={"idle"}
                    barGap={"0"}
                    // stackId="1"
                    fill="url(#idlePattern)"
                    // shape={BarWithBorder()}
                    // stroke="black"
                />
                {/* {props.allResults.machines.map((machine, mIndex) =>
                    machine.operations.map((op, opIndex) => (
                        <Bar
                            key={op.id}
                            dataKey={op.id}
                            stackId="1"
                            fill={JOB_COLORS[op.job_id]}
                            // stroke="black"
                        />
                    ))
                )} */}
            </BarChart>
        </ResponsiveContainer>
    );

    return (
        <div className={`${classes.chart} ${props.className}`}>
            <p>Machine Breakdowns / Idle</p>
            {renderMachineBreakdownIdleChart}
        </div>
    );
};

const MachineCostBarChart = (props) => {
    const machineCostData = props.allResults.machines.map((machine) => ({
        name: `${machine.id}`,
        "repair cost": machine.repair_cost.mean,
        "operational cost": machine.operational_cost.mean,
    }));

    const renderMachineCostBars = (
        <ResponsiveContainer width="100%" height={200}>
            <BarChart
                data={machineCostData}
                margin={{
                    top: 20,
                    right: 30,
                    left: 20,
                    bottom: 5,
                }}
            >
                <XAxis dataKey="name" />
                <YAxis />
                <Tooltip labelFormatter={(name) => "Machine " + name} />
                {/* <Legend /> */}
                <Bar
                    dataKey="repair cost"
                    fill="#f55f4e"
                    // stroke="black"
                    stackId="1"
                />
                <Bar
                    dataKey="operational cost"
                    fill="#3C7FD0"
                    // stroke="black"
                    stackId="1"
                />
            </BarChart>
        </ResponsiveContainer>
    );

    return (
        <div className={`${classes.chart} ${props.className}`}>
            <p>Machine Costs</p>
            {renderMachineCostBars}
        </div>
    );
};

const JobCompletionTimeChart = (props) => {
    const JOB_COLORS = Object.fromEntries(
        props.allResults.jobs.map((job, index) => [
            job.id,
            COLOR_DATABASE[job.id % COLOR_DATABASE.length],
        ])
    );

    const jobCompletionTimeData = props.allResults.jobs.map((job) => ({
        name: `${job.id}`,
        "Completion Time": job.completion_time.mean,
    }));

    const renderJobCompletionTimeChart = (
        <ResponsiveContainer width="100%" height={200}>
            <BarChart
                data={jobCompletionTimeData}
                margin={{
                    top: 20,
                    right: 30,
                    left: 20,
                    bottom: 5,
                }}
            >
                <XAxis dataKey="name" />
                <YAxis />
                <Tooltip labelFormatter={(name) => "job " + name} />
                {/* <Legend /> */}

                <Bar
                    dataKey="Completion Time"
                    // stroke="black"
                >
                    {props.allResults.jobs.map((job) => (
                        <Cell
                            key={`cell-${job.id}`}
                            fill={JOB_COLORS[job.id]}
                        />
                    ))}
                </Bar>
            </BarChart>
        </ResponsiveContainer>
    );

    return (
        <div className={`${classes.chart} ${props.className}`}>
            <p>Job Completion Time</p>
            {renderJobCompletionTimeChart}
        </div>
    );
};

const JobLatenessChart = (props) => {

    const jobLatenessData = props.allResults.jobs.map((job) => ({
        name: `${job.id}`,
        "Lateness": job.lateness.mean,
    }));

    const renderJobLatenessChart = (
        <ResponsiveContainer width="100%" height={200}>
            <BarChart
                data={jobLatenessData}
                margin={{
                    top: 20,
                    right: 30,
                    left: 20,
                    bottom: 5,
                }}
            >
                <XAxis dataKey="name" />
                <YAxis />
                <Tooltip labelFormatter={(name) => "job " + name} />
                {/* <Legend /> */}

                <Bar
                    dataKey="Lateness"
                    // stroke="black"
                    fill="#BA7BA1"
                >
                    {/* {props.allResults.jobs.map((job) => (
                        <Cell
                            key={`cell-${job.id}`}
                            fill={JOB_COLORS[job.id]}
                        />
                    ))} */}
                </Bar>
            </BarChart>
        </ResponsiveContainer>
    );

    return (
        <div className={`${classes.chart} ${props.className}`}>
            <p>Job Lateness</p>
            {renderJobLatenessChart}
        </div>
    );
};

const JobCostChart = (props) => {

    const jobCostData = props.allResults.jobs.map((job) => ({
        name: `${job.id}`,
        "Cost": job.lateness_cost.mean,
    }));

    const renderJobCostChart = (
        <ResponsiveContainer width="100%" height={200}>
            <BarChart
                data={jobCostData}
                margin={{
                    top: 20,
                    right: 30,
                    left: 20,
                    bottom: 5,
                }}
            >
                <XAxis dataKey="name" />
                <YAxis />
                <Tooltip labelFormatter={(name) => "job " + name} />
                {/* <Legend /> */}

                <Bar
                    dataKey="Cost"
                    // stroke="black"
                    fill="#3C7FD0"
                >
                    {/* {props.allResults.jobs.map((job) => (
                        <Cell
                            key={`cell-${job.id}`}
                            fill={JOB_COLORS[job.id]}
                        />
                    ))} */}
                </Bar>
            </BarChart>
        </ResponsiveContainer>
    );

    return (
        <div className={`${classes.chart} ${props.className}`}>
            <p>Job Cost</p>
            {renderJobCostChart}
        </div>
    );
};

const OperationsLengthChart = (props) => {

    const operationLengthData = props.allResults.operations.map((operation) => ({
        name: `${operation.id}`,
        "Length": operation.length.mean,
    }));

    const renderOperationsLengthChart = (
        <ResponsiveContainer width="100%" height={200}>
            <BarChart
                data={operationLengthData}
                margin={{
                    top: 20,
                    right: 30,
                    left: 20,
                    bottom: 5,
                }}
            >
                <XAxis dataKey="name" />
                <YAxis />
                <Tooltip labelFormatter={(name) => "Operation " + name} />
                {/* <Legend /> */}

                <Bar
                    dataKey="Length"
                    // stroke="black"
                    fill="#4c5a91"
                >
                    {/* {props.allResults.jobs.map((job) => (
                        <Cell
                            key={`cell-${job.id}`}
                            fill={JOB_COLORS[job.id]}
                        />
                    ))} */}
                </Bar>
            </BarChart>
        </ResponsiveContainer>
    );

    return (
        <div className={`${classes.chart} ${props.className}`}>
            <p>Operations Length</p>
            {renderOperationsLengthChart}
        </div>
    );
};

const MachinesOperationsLength = (props) => {

    const machineOperationLengthData = props.machine.operations.map((operation) => ({
        name: `${operation.id}`,
        "Length": operation.length.mean,
    }));

    const renderMachineOperationsLengthChart = (
        <ResponsiveContainer width="100%" height={200}>
            <BarChart
                data={machineOperationLengthData}
                layout="vertical"
                margin={{
                    top: 20,
                    right: 30,
                    left: 20,
                    bottom: 5,
                }}
            >
                <XAxis type="number" />
                <YAxis type="category" dataKey="name"/>
                {/* <XAxis dataKey="name" />
                <YAxis /> */}
                <Tooltip labelFormatter={(name) => "Operation " + name} />
                {/* <Legend /> */}

                <Bar
                    dataKey="Length"
                    // stroke="black"
                    fill="#4c5a91"
                >
                    {/* {props.allResults.jobs.map((job) => (
                        <Cell
                            key={`cell-${job.id}`}
                            fill={JOB_COLORS[job.id]}
                        />
                    ))} */}
                </Bar>
            </BarChart>
        </ResponsiveContainer>
    );

    return (
        <div className={`${classes.chart} ${props.className}`}>
            <p>Operations Length</p>
            {renderMachineOperationsLengthChart}
        </div>
    );
};

const JobsOperationsLength = (props) => {

    const jobOperationLengthData = props.job.operations.map((operation) => ({
        name: `${operation.id}`,
        "Length": operation.length.mean,
    }));

    const renderJobOperationsLengthChart = (
        <ResponsiveContainer width="100%" height={200}>
            <BarChart
                data={jobOperationLengthData}
                layout="vertical"
                margin={{
                    top: 20,
                    right: 30,
                    left: 20,
                    bottom: 5,
                }}
            >
                <XAxis type="number" />
                <YAxis type="category" dataKey="name"/>
                {/* <XAxis dataKey="name" />
                <YAxis /> */}
                <Tooltip labelFormatter={(name) => "Operation " + name} />
                {/* <Legend /> */}

                <Bar
                    dataKey="Length"
                    // stroke="black"
                    fill="#4c5a91"
                >
                    {/* {props.allResults.jobs.map((job) => (
                        <Cell
                            key={`cell-${job.id}`}
                            fill={JOB_COLORS[job.id]}
                        />
                    ))} */}
                </Bar>
            </BarChart>
        </ResponsiveContainer>
    );

    return (
        <div className={`${classes.chart} ${props.className}`}>
            <p>Operations Length</p>
            {renderJobOperationsLengthChart}
        </div>
    );
};

export {
    TotalRessourceUtilisationPieChart,
    TotalCostPieChart,
    MachineUtilisationBarChart,
    MachineMakespanBarChart,
    MachineBreakdownIdleChart,
    MachineCostBarChart,
    JobCompletionTimeChart,
    JobLatenessChart,
    JobCostChart,
    OperationsLengthChart,
    MachinesOperationsLength,
    JobsOperationsLength
};
