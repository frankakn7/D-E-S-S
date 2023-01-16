import React from "react";
import Box from "../../interface/Box/Box";
import CollapsableBox from "../../interface/CollapsableBox/CollapsableBox";
import classes from "./FileDetails.module.css";
import JobsTable from "./tables/JobsTable";
import MachinesTable from "./tables/MachinesTable";
import OperationsTable from "./tables/OperationsTable";

const FileDetails = (props) => {
    const interpretedJson = JSON.parse(props.file.data);
    return (
        <div className={classes.content}>
            <div className={classes.planTitle}>
                <input
                    type="text"
                    defaultValue="New Plan"
                    className={classes.titleInput}
                    ref={props.nameRef}
                />
                <h4>Created from {props.file.name}</h4>
            </div>
            <div className={classes.infoContainers}>
                <div className={classes.half}>
                    <Box titleText={<p>Imported JSON</p>}>
                        <div className={classes.jsonString}>
                            <pre>{props.file.data}</pre>
                        </div>
                    </Box>
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
                </div>
            </div>
        </div>
    );
};

export default FileDetails;
