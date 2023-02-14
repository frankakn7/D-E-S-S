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

const Tab = (props) => {
  const onClickHandler = () => {
    props.setView(props.newView);
  };

  const selected = props.newView === props.view;

    return (
        <Button
            className={`${classes.tab} ${!selected ? classes.unselectedTab : ""}`}
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
  const [results1, setResults1] = useState();
  const [results2, setResults2] = useState();

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

        if(results1.general_stats){
            results1.machines = results1.machines.map((machine) => {
                return {operations: results1.operations.filter((op) => op.machine_id === machine.id,), ...machine}
            })
            
            results1.jobs = results1.jobs.map((job) => {
                return {operations: results1.operations.filter((op) => op.job_id === job.id,), ...job}
            })
        }

        if(results2.general_stats){
            results2.machines = results2.machines.map((machine) => {
                return {operations: results2.operations.filter((op) => op.machine_id === machine.id,), ...machine}
            })
            
            results2.jobs = results2.jobs.map((job) => {
                return {operations: results2.operations.filter((op) => op.job_id === job.id,), ...job}
            })
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
                        <GeneralCompare results1={results1} results2={results2}
                            // generalStats={
                            //     results1.general_stats
                            // }
                        />
                    )}
                    {view === "machines" && (
                        <MachineCompare resultsOne={results1} resultsTwo={results2}
                            // machines={results1.machines}
                        />
                    )}
                    {view === "jobs" && (
                        // <JobCompare jobs={JSON.parse(simCase1.results).jobs} />
                        <JobCompare />
                    )}
                    {view === "operations" && (
                        <OperationCompare
                            // operations={JSON.parse(simCase1.results).operations}
                        />
                    )}
                </Box>
            )}
        </div>
  );
};

export default Compare;
