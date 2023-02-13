import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import Box from "../../interface/Box/Box";
import Button from "../../interface/Button/Button";
import GoBack from "../../interface/GoBack/GoBack";
import GeneralResults from "../Results/GeneralResults/GeneralResults";
import JobResults from "../Results/JobResults/JobResults";
import MachineResults from "../Results/MachineResults/MachineResults";
import OperationResults from "../Results/OperationResults/OperationResults";
import classes from "./Compare.module.css";

const Tab = (props) => {
    const onClickHandler = () => {
        props.setView(props.newView);
    };

    const selected = props.newView === props.view;

    return (
        <Button
            className={`${!selected ? classes.unselectedTab : ""}`}
            onClick={onClickHandler}
        >
            {props.children}
        </Button>
    );
};

const Compare = (props) => {
    const { id1, id2 } = useParams();
    const [simCase1, setSimCase1] = useState();
    const [simCase2, setSimCase2] = useState();

    const [view, setView] = useState("general");

    useEffect(() => {
        const foundSimCase1 = props.simCases.find((simCase) => {
            return id1 === simCase.id;
        });

        const foundSimCase2 = props.simCases.find((simCase) => {
            return id2 === simCase.id;
        });

        setSimCase1(foundSimCase1);
        setSimCase2(foundSimCase2);
    }, [props.simCases, id1, id2]);

    return (
        <div className={classes.content}>
            <div className={classes.top}>
                <GoBack />
                <div>
                    {simCase1 && (
                        <h2>Comparing Results of Simulation: {simCase1.id}</h2>
                    )}
                    {simCase2 && <h2>and: {simCase2.id}</h2>}
                </div>
            </div>
            <div className={classes.tabs}>
                <Tab setView={setView} view={view} newView={"general"}>
                    General Results
                </Tab>
                <Tab setView={setView} view={view} newView={"machines"}>
                    Machines
                </Tab>
                <Tab setView={setView} view={view} newView={"jobs"}>
                    Jobs
                </Tab>
                <Tab setView={setView} view={view} newView={"operations"}>
                    Operations
                </Tab>
            </div>
            {simCase1 && (
                <Box
                    className={classes.contentBox}
                    titleClassName={classes.boxTitle}
                >
                    {view === "general" && (
                        <GeneralResults
                            generalStats={
                                JSON.parse(simCase1.results).general_stats
                            }
                        />
                    )}
                    {view === "machines" && (
                        <MachineResults
                            machines={JSON.parse(simCase1.results).machines}
                        />
                    )}
                    {view === "jobs" && (
                        <JobResults jobs={JSON.parse(simCase1.results).jobs} />
                    )}
                    {view === "operations" && (
                        <OperationResults
                            operations={JSON.parse(simCase1.results).operations}
                        />
                    )}
                </Box>
            )}
        </div>
    );
};

export default Compare;
