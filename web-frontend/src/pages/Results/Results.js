import React, { Fragment, useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import Button from "../../interface/Button/Button";
import GoBack from "../../interface/GoBack/GoBack";
import Box from "../../interface/Box/Box";
import classes from "./Results.module.css";
import GeneralResults from "./GeneralResults/GeneralResults";
import MachineResults from "./MachineResults/MachineResults";
import JobResults from "./JobResults/JobResults";
import OperationResults from "./OperationResults/OperationResults";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTrashCan } from "@fortawesome/free-solid-svg-icons";
import ConfirmationModal from "../../interface/Modal/ConfirmationModal/ConfirmationModal";
import ErrorModal from "../../interface/Modal/ErrorModal/ErrorModal";

const Tab = (props) => {
    const onClickHandler = () => {
        props.setView(props.newView);
    };

    const selected = props.newView === props.view;

    return (
        <Button
            className={`${classes.tab} ${
                !selected ? classes.unselectedTab : ""
            }`}
            onClick={onClickHandler}
        >
            {props.children}
        </Button>
    );
};

const Results = (props) => {
    const { id } = useParams();
    const [simCase, setSimCase] = useState();
    const [results, setResults] = useState();
    const [deleting, setDeleting] = useState();
    const navigate = useNavigate();

    const [view, setView] = useState("general");

    const [resultError, setResultError] = useState(false);

    useEffect(() => {
        const foundSimCase = props.simCases.find((simCase) => {
            return id === simCase.id;
        });

        try {
            const results = foundSimCase
                ? JSON.parse(foundSimCase.results)
                : {};

            if (!results.general_stats) {
                throw new Error("No Results");
            }

            if (results.general_stats) {
                results.machines = results.machines.map((machine) => {
                    return {
                        operations: results.operations.filter(
                            (op) => op.machine_id === machine.id
                        ),
                        ...machine,
                    };
                });

                results.jobs = results.jobs.map((job) => {
                    return {
                        operations: results.operations.filter(
                            (op) => op.job_id === job.id
                        ),
                        ...job,
                    };
                });
            }
        } catch (e) {
            setResultError(true);
        }

        setSimCase(foundSimCase);
        setResults(results);
    }, [props.simCases, id]);

    const handleDelete = () => {
        setDeleting(false);
        navigate(`/simulations`);
        props.simCaseDeleteHandler(id);
    };

    return (
        <div className={classes.main}>
            {resultError && (
                <ErrorModal>
                    <div className={classes.errorModal}>
                        <p>
                            Logical Errror in the simulation. Delete this
                            simulation case here:
                        </p>
                        <Button
                            className={classes.delete}
                            onClick={() => setDeleting(true)}
                        >
                            <FontAwesomeIcon icon={faTrashCan} />
                        </Button>
                    </div>
                </ErrorModal>
            )}
            {deleting && (
                <ConfirmationModal
                    onClose={() => setDeleting(false)}
                    onContinue={handleDelete}
                >
                    <div className={classes.confirmContent}>
                        <p>
                            Are you sure you want to delete the Simulation of{" "}
                            <b>
                                "
                                {
                                    props.plans.find(
                                        (plan) => simCase.planId === plan.uuid
                                    ).name
                                }
                                "
                            </b>{" "}
                            with the simulation ID ({simCase.id})?{" "}
                        </p>
                        <p>
                            <span className={classes.warning}>Warning:</span>{" "}
                            The calculated results will be <u>lost</u>
                        </p>
                    </div>
                </ConfirmationModal>
            )}
            {!resultError && (
                <Fragment>
                    <div className={classes.top}>
                        <GoBack />
                        {simCase && (
                            <div className={classes.topRight}>
                                <Button
                                    className={classes.delete}
                                    onClick={() => setDeleting(true)}
                                >
                                    <FontAwesomeIcon icon={faTrashCan} />
                                </Button>
                                <h2>Results of Simulation: {simCase.id}</h2>
                            </div>
                        )}
                    </div>
                    <div className={classes.tabs}>
                        <Tab setView={setView} view={view} newView={"general"}>
                            Summary
                        </Tab>
                        <Tab setView={setView} view={view} newView={"machines"}>
                            Machines
                        </Tab>
                        <Tab setView={setView} view={view} newView={"jobs"}>
                            Jobs
                        </Tab>
                        <Tab
                            setView={setView}
                            view={view}
                            newView={"operations"}
                        >
                            Operations
                        </Tab>
                    </div>
                    {simCase && (
                        <Box
                            className={classes.contentBox}
                            titleClassName={classes.boxTitle}
                        >
                            {view === "general" && (
                                <GeneralResults allResults={results} />
                            )}
                            {view === "machines" && (
                                <MachineResults allResults={results} />
                            )}
                            {view === "jobs" && (
                                <JobResults allResults={results} />
                            )}
                            {view === "operations" && (
                                <OperationResults allResults={results} />
                            )}
                        </Box>
                    )}
                </Fragment>
            )}
        </div>
    );
};

export default Results;
