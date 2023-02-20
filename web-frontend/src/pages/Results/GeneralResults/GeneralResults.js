import React from "react";
import DetailsTable from "../DetailsTable/DetailsTable";
import DetailsTableRow from "../DetailsTable/DetailsTableRow";
import { JobCompletionTimeChart, MachineMakespanBarChart, MachineUtilisationBarChart, TotalCostPieChart, TotalResourceUtilisationPieChart } from "../ResultsCharts/ResultsCharts";
import classes from "./GeneralResults.module.css";

const GeneralResults = (props) => {

    return (
        <div className={classes.mainContent}>
            <div className={classes.leftData}>
                <DetailsTable>
                    <DetailsTableRow
                        stat={{
                            name: "completion time",
                            ...props.allResults.general_stats.total_completion_time,
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
                            name: "total resource utilization",
                            ...props.allResults.general_stats
                                .total_resource_utilization,
                        }}
                        percentage={true}
                    />
                </DetailsTable>
            </div>
            <div className={classes.rightData}>
                <div className={classes.charts}>
                    <TotalResourceUtilisationPieChart allResults={props.allResults}/>
                    <TotalCostPieChart allResults={props.allResults} />
                    <MachineUtilisationBarChart allResults={props.allResults} />
                </div>
                <div className={classes.charts}>
                    <JobCompletionTimeChart allResults={props.allResults} />
                    <MachineMakespanBarChart allResults={props.allResults} />
                </div>
            </div>
        </div>
    );
};

export default GeneralResults;
