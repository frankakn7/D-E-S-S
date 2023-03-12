import React from "react";
import Box from "../../interface/Box/Box";
import CollapsableBox from "../../interface/CollapsableBox/CollapsableBox";
import classes from "./FileDetails.module.css";
import JobsTable from "./tables/JobsTable";
import MachinesTable from "./tables/MachinesTable";
import OperationsTable from "./tables/OperationsTable";

/**
 * A page displaying the information that was read out of a newly uploaded JSON file.
 * Contains JSON file on the left and table details on the right as well as actions at the bottom of the page
 * @param {Object} props all values passed to this object
 * @param {File} props.file the file that was uploaded
 * @param {JSX.Element} props.buttons a JSX element containing all the action buttons used on this page
 * @param {ref} props.nameRef a reference to the file name input field
 * @returns 
 */
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
                    <div className={classes.buttons}>
                        {props.buttons}
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
                </div>
            </div>
        </div>
    );
};

export default FileDetails;
