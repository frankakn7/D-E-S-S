import React, { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import classes from "./Results.module.css";

const Results = (props) => {
  const { id } = useParams();
  const [result, setResult] = useState();

  useEffect(() => {
    console.log(props.results);
    console.log(id);
    const foundResult = props.results.find((result) => {
      console.log(result.id);
      console.log(id);
      return id === result.id;
    });

    setResult(foundResult);
  }, [props.results, id]);

  return (
    <div className={classes.content}>
      {result && <h2>Results of Simulation: {result.id}</h2>}
      {result && (
        <div className={classes.json}>
          <p>Event-Log of last simulation run:</p>
          <pre>{result.results}</pre>
        </div>
      )}
      <div className={classes.buttons}>
        <Link to="/plans" className="btn-default-style">
          View all Uploaded Plans
        </Link>
        <Link to="/upload" className="btn-default-style">
            Upload new Plan
        </Link>
      </div>
    </div>
  );
};

export default Results;
