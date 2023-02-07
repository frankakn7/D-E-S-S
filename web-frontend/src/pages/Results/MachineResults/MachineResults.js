import React from "react";
import Box from "../../../interface/Box/Box";
import Button from "../../../interface/Button/Button";
import DetailsTable from "../DetailsTable/DetailsTable";
import DetailsTableRow from "../DetailsTable/DetailsTableRow";
import classes from "./MachineResults.module.css";

const MachineResults = (props) => {
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
                </Box>
            ))}
        </div>
    );
};

export default MachineResults;
