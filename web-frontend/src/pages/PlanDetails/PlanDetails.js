import React, { useEffect, useRef, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import Box from "../../interface/Box/Box";
import Button from "../../interface/Button/Button";
import CollapsableBox from "../../interface/CollapsableBox/CollapsableBox";
import GoBack from "../../interface/GoBack/GoBack";
import ListButton from "../../interface/ListButton/ListButton";
import NumOfSimulations from "../../interface/Modal/NumOfSimulations/NumOfSimulations";
import FileDetails from "../FileDetails/FileDetails";
import JobsTable from "../FileDetails/tables/JobsTable";
import MachinesTable from "../FileDetails/tables/MachinesTable";
import OperationsTable from "../FileDetails/tables/OperationsTable";
import classes from "./PlanDetails.module.css";

/**
 * A page for displaying the details of an uploaded plan
 * @param {Object} props contains all the values passed to this page
 * @param {Arrray<Object>} props.plans list of all plans
 * @param {Arrray<Object>} props.simCases list of all simulation cases
 * @param {Function} props.planSimulateHandler a function handling the api call to start a new simulation case of the plan
 * @param {Function} props.checkIfDoneHandler a function handling api call to check if simulation case is done simulating
 * @returns 
 */
const PlanDetails = (props) => {
    const { id } = useParams();
    const [plan, setPlan] = useState({ name: "", uuid: "", planJson: "{}" });
    const [simCases, setSimCases] = useState([]);
    const interpretedJson = JSON.parse(plan.planJson);
    const navigate = useNavigate();

    const [simulate, setSimulate] = useState(false);
    const numOfSimulationsRef = useRef();

    useEffect(() => {
        const foundPlan = props.plans.find((plan) => {
            return id === plan.uuid;
        });

        const foundSimCases = props.simCases.filter((simCase) => {
            return id === simCase.planId;
        });
        if (foundPlan) setPlan(foundPlan);
        if (foundSimCases) setSimCases(foundSimCases);
    }, [props.plans, props.simCases, id]);

    const handleSimulate = (planId) => {
        const numOfSimulations = numOfSimulationsRef.current.value;
        props
            .planSimulateHandler(planId, numOfSimulations)
            .then((simCaseId) => {
                props.checkIfDoneHandler(simCaseId);
                navigate('/simulations')
            })
            .catch((error) => {
                throw Error(error);
            });
    };

    return (
        <div className={classes.content}>
            {simulate && (
                <NumOfSimulations
                    onClose={() => setSimulate(false)}
                    onContinue={() => handleSimulate(plan.uuid)}
                    numOfSimulationsRef={numOfSimulationsRef}
                />
            )}
            <GoBack />
            <div className={classes.planTitle}>
                <p className={classes.titleInput}>
                    "{plan.name}" ({plan.uuid})
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
                        <Button onClick={() => setSimulate(true)}>
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
                                        onClick={() =>
                                            navigate("/results/" + simCase.id)
                                        }
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
