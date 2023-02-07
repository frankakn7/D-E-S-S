import React, { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import Box from "../../interface/Box/Box";
import Button from "../../interface/Button/Button";
import CollapsableBox from "../../interface/CollapsableBox/CollapsableBox";
import GoBack from "../../interface/GoBack/GoBack";
import ListButton from "../../interface/ListButton/ListButton";
import FileDetails from "../FileDetails/FileDetails";
import JobsTable from "../FileDetails/tables/JobsTable";
import MachinesTable from "../FileDetails/tables/MachinesTable";
import OperationsTable from "../FileDetails/tables/OperationsTable";
import classes from "./PlanDetails.module.css";

const PlanDetails = (props) => {
    const { id } = useParams();
    const [plan, setPlan] = useState({ name: "", uuid: "", planJson: "{}" });
    const [simCases, setSimCases] = useState([]);
    const interpretedJson = JSON.parse(plan.planJson);
    const navigate = useNavigate();

    useEffect(() => {
        const foundPlan = props.plans.find((plan) => {
            console.log(plan);
            return id === plan.uuid;
        });

        const foundSimCases = props.simCases.filter((simCase) => {
            return id === simCase.planId;
        });
        if (foundPlan) setPlan(foundPlan);
        if (foundSimCases) setSimCases(foundSimCases);
    }, [props.plans, props.simCases, id]);

    return (
        <div className={classes.content}>
            <GoBack />
            <div className={classes.planTitle}>
                <p className={classes.titleInput}>
                    {plan.name} ({plan.uuid})
                </p>
            </div>
            <div className={classes.infoContainers}>
                <div className={classes.half}>
                    <Box titleText={<p>Imported JSON</p>}>
                        <div className={classes.jsonString}>
                            <pre>
                                {JSON.stringify(interpretedJson, null, 4)}
                            </pre>
                        </div>
                    </Box>
                    <div className={classes.buttons}>
                        <Button onClick={() => props.planSimulateHandler(plan.uuid)}>
                            Simulate
                        </Button>
                    </div>
                </div>
                <div className={classes.half}>
                    <CollapsableBox titleText="Jobs">
                        <div className={classes.tableBox}>
                            <JobsTable jobs={interpretedJson.jobs} />
                        </div>
                    </CollapsableBox>
                    <CollapsableBox titleText="Machines">
                        <div className={classes.tableBox}>
                            <MachinesTable
                                machines={interpretedJson.machines}
                            />
                        </div>
                    </CollapsableBox>
                    <CollapsableBox titleText="Operations">
                        <div className={classes.tableBox}>
                            <OperationsTable
                                operations={interpretedJson.operations}
                            />
                        </div>
                    </CollapsableBox>
                    <CollapsableBox titleText="Simulations">
                        <div className={classes.simulations}>
                            {simCases.length > 0 &&
                                simCases.map((simCase) => (
                                    <ListButton
                                        key={simCase.id}
                                        id={simCase.id}
                                        name="Simulation"
                                        createdOn={simCase.createdOn}
                                    />
                                ))}
                            {!simCases.length && <p>No Simulations yet</p>}
                        </div>
                    </CollapsableBox>
                </div>
            </div>
        </div>
    );
};

export default PlanDetails;
