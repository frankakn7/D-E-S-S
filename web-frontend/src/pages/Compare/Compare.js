import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import Box from "../../interface/Box/Box";
import Button from "../../interface/Button/Button";
import GoBack from "../../interface/GoBack/GoBack";
import classes from "./Compare.module.css";
import GeneralCompare from "./GeneralCompare/GeneralCompare";
import JobCompare from "./JobCompare/JobCompare";
import MachineCompare from "./MachineCompare/MachineCompare";
import OperationCompare from "./OperationCompare/OperationCompare";

/**
 * Button as tab element for the comparison page
 * @param {Object} props - all props passed to Tab
 * @returns 
 */
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

/**
 * Main Compare page implementing all other compare pages. Takes the simulaiont id1 and id2 out of the url 
 * @param {Object} props - all props passed to page
 * @param {Array<Object>} props.simCases - all simulation cases
 * @param {Array<Object>} props.plans - all plan objects
 * @returns 
 */
const Compare = (props) => {
    const { id1, id2 } = useParams();
    const [simCase1, setSimCase1] = useState();
    const [simCase2, setSimCase2] = useState();
    const [results1, setResults1] = useState();
    const [results2, setResults2] = useState();
    const [simName1, setSimName1] = useState("Simulation1");
    const [simName2, setSimName2] = useState("Simulation2");

    const [view, setView] = useState("general");

    useEffect(() => {
        const foundSimCase1 = props.simCases.find((simCase) => {
            return id1 === simCase.id;
        });

        const foundSimCase2 = props.simCases.find((simCase) => {
            return id2 === simCase.id;
        });

        const results1 = foundSimCase1 ? JSON.parse(foundSimCase1.results) : {};
        const results2 = foundSimCase2 ? JSON.parse(foundSimCase2.results) : {};

        if (results1.general_stats) {
            results1.machines = results1.machines.map((machine) => {
                return {
                    operations: results1.operations.filter(
                        (op) => op.machine_id === machine.id
                    ),
                    ...machine,
                };
            });

            results1.jobs = results1.jobs.map((job) => {
                return {
                    operations: results1.operations.filter(
                        (op) => op.job_id === job.id
                    ),
                    ...job,
                };
            });
        }

        if (results2.general_stats) {
            results2.machines = results2.machines.map((machine) => {
                return {
                    operations: results2.operations.filter(
                        (op) => op.machine_id === machine.id
                    ),
                    ...machine,
                };
            });

            results2.jobs = results2.jobs.map((job) => {
                return {
                    operations: results2.operations.filter(
                        (op) => op.job_id === job.id
                    ),
                    ...job,
                };
            });
        }

        if (foundSimCase1 && foundSimCase2) {
            const plan1Name = props.plans.find(
                (plan) => foundSimCase1.planId === plan.uuid
            ).name;

            const plan2Name = props.plans.find(
                (plan) => foundSimCase2.planId === plan.uuid
            ).name;

            setSimName1("Simulation 1 of \”"+plan1Name+"\"("+foundSimCase1.createdOn+")");
            setSimName2("Simulation 2 of \”"+plan2Name+"\"("+foundSimCase2.createdOn+")");
        }

        setSimCase1(foundSimCase1);
        setSimCase2(foundSimCase2);
        setResults1(results1);
        setResults2(results2);
    }, [props.simCases, id1, id2]);

    return (
        <div className={classes.content}>
            <div className={classes.top}>
                <GoBack />
                <div>
                <h2>Comparing Results of:</h2>
                    {simCase1 && (
                         <h3>{simName1}</h3>
                    )}
                    {simCase2 && <h3>{simName2}</h3>}
                </div>
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
                        <GeneralCompare
                            results1={results1}
                            results2={results2}
                            simName1={simName1}
                            simName2={simName2}
                            // generalStats={
                            //     results1.general_stats
                            // }
                        />
                    )}
                    {view === "machines" && (
                        <MachineCompare
                            resultsOne={results1}
                            resultsTwo={results2}
                            simName1={simName1}
                            simName2={simName2}
                            // machines={results1.machines}
                        />
                    )}
                    {view === "jobs" && (
                        // <JobCompare jobs={JSON.parse(simCase1.results).jobs} />
                        <JobCompare
                            resultsOne={results1}
                            resultsTwo={results2}
                            simName1={simName1}
                            simName2={simName2}
                        />
                    )}
                    {view === "operations" && (
                        <OperationCompare
                            resultsOne={results1}
                            resultsTwo={results2}
                            simName1={simName1}
                            simName2={simName2}
                            // operations={JSON.parse(simCase1.results).operations}
                        />
                    )}
                </Box>
            )}
        </div>
    );
};

export default Compare;
