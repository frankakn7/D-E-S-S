import React, { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import Button from "../../interface/Button/Button";
import GoBack from "../../interface/GoBack/GoBack";
import classes from "./Results.module.css";

const Results = (props) => {
    const { id } = useParams();
    const [simCase, setSimCase] = useState();
    const navigate = useNavigate();

    useEffect(() => {
        const foundSimCase = props.simCases.find((simCase) => {
            return id === simCase.id;
        });

        setSimCase(foundSimCase);
    }, [props.simCases, id]);

    return (
        <div className={classes.main}>
            <GoBack />
            <div className={classes.content}>
                {simCase && <h2>Results of Simulation: {simCase.id}</h2>}
                {simCase && (
                    <div className={classes.json}>
                        <p>Event-Log of last simulation run:</p>
                        <pre>{simCase.results}</pre>
                    </div>
                )}
                <div className={classes.buttons}>
                    <Link to="/plans" className="btn-default-style">
                        View all Uploaded Plans
                    </Link>
                    <Link to="/" className="btn-default-style">
                        Upload new Plan
                    </Link>
                    <Link to="/simulations" className="btn-default-style">
                        View all Simulations
                    </Link>
                </div>
            </div>
        </div>
    );
};

export default Results;
