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
    <Button className={`${!selected ? classes.unselectedTab : ""}`} onClick={onClickHandler}>
        {props.children}
    </Button>
  )
}

const Results = (props) => {
    const { id } = useParams();
    const [simCase, setSimCase] = useState();
    const navigate = useNavigate();

    const [view, setView] = useState("general");

    useEffect(() => {
        const foundSimCase = props.simCases.find((simCase) => {
            return id === simCase.id;
        });

        setSimCase(foundSimCase);
    }, [props.simCases, id]);

    return (
        <div className={classes.main}>
            <div className={classes.top}>
                <GoBack />
                {simCase && <h2>Results of Simulation: {simCase.id}</h2>}
            </div>
            <div className={classes.tabs}>
                <Tab setView={setView} view={view} newView={"general"}>General Results</Tab>
                <Tab setView={setView} view={view} newView={"machines"}>Machines</Tab>
                <Tab setView={setView} view={view} newView={"jobs"}>Jobs</Tab>
                <Tab setView={setView} view={view} newView={"operations"}>Operations</Tab>
            </div>
            {simCase && (
                <Box
                    className={classes.contentBox}
                    titleClassName={classes.boxTitle}
                >
                    {/* <div className={classes.content}>
                        <div className={classes.json}>
                            <p>Event-Log of last simulation run:</p>
                            <pre>
                                {JSON.stringify(
                                    JSON.parse(simCase.results),
                                    null,
                                    4
                                )}
                            </pre>
                        </div>
                        <div className={classes.buttons}>
                            <Link to="/plans" className="btn-default-style">
                                View all Uploaded Plans
                            </Link>
                            <Link to="/" className="btn-default-style">
                                Upload new Plan
                            </Link>
                            <Link
                                to="/simulations"
                                className="btn-default-style"
                            >
                                View all Simulations
                            </Link>
                        </div>
                    </div> */}
                    {view === "general" && <GeneralResults generalStats={JSON.parse(simCase.results).general_stats} />}
                    {view === "machines" && <MachineResults machines={JSON.parse(simCase.results).machines} />}
                    {view === "jobs" && <JobResults jobs={JSON.parse(simCase.results).jobs} />}
                    {view === "operations" && <OperationResults operations={JSON.parse(simCase.results).operations} />}
                </Box>
            )}
        </div>
    );
};

export default Results;
