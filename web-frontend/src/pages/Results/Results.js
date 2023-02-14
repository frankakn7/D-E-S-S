import React, { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import Button from "../../interface/Button/Button";
import GoBack from "../../interface/GoBack/GoBack";
import Box from "../../interface/Box/Box";
import classes from "./Results.module.css";
import GeneralResults from "./GeneralResults/GeneralResults";
import MachineResults from "./MachineResults/MachineResults";
import JobResults from "./JobResults/JobResults";
import OperationResults from "./OperationResults/OperationResults";

const Tab = (props) => {

    const onClickHandler = () => {
        props.setView(props.newView)
    }

    const selected = props.newView === props.view;

  return (
    <Button className={`${classes.tab} ${!selected ? classes.unselectedTab : ""}`} onClick={onClickHandler}>
        {props.children}
    </Button>
  )
}

const Results = (props) => {
    const { id } = useParams();
    const [simCase, setSimCase] = useState();
    const [results, setResults] = useState();
    const navigate = useNavigate();

    const [view, setView] = useState("general");

    useEffect(() => {
        const foundSimCase = props.simCases.find((simCase) => {
            return id === simCase.id;
        });

        const results = (foundSimCase ? JSON.parse(foundSimCase.results) : {})

        if(results.general_stats){
            results.machines = results.machines.map((machine) => {
                return {operations: results.operations.filter((op) => op.machine_id === machine.id,), ...machine}
            })
            
            results.jobs = results.jobs.map((job) => {
                return {operations: results.operations.filter((op) => op.job_id === job.id,), ...job}
            })
        }
        
        setSimCase(foundSimCase);
        setResults(results)
    }, [props.simCases, id]);

    return (
        <div className={classes.main}>
            <div className={classes.top}>
                <GoBack />
                {simCase && <h2>Results of Simulation: {simCase.id}</h2>}
            </div>
            <div className={classes.tabs}>
                <Tab setView={setView} view={view} newView={"general"}>Summary</Tab>
                <Tab setView={setView} view={view} newView={"machines"}>Machines</Tab>
                <Tab setView={setView} view={view} newView={"jobs"}>Jobs</Tab>
                <Tab setView={setView} view={view} newView={"operations"}>Operations</Tab>
            </div>
            {simCase && (
                <Box
                    className={classes.contentBox}
                    titleClassName={classes.boxTitle}
                >
                    {view === "general" && <GeneralResults allResults={results} />}
                    {view === "machines" && <MachineResults allResults={results} />}
                    {view === "jobs" && <JobResults allResults={results} />}
                    {view === "operations" && <OperationResults allResults={results} />}
                </Box>
            )}
        </div>
    );
};

export default Results;
