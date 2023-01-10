import React from 'react'
import CollapsableBox from '../../../interface/CollapsableBox/CollapsableBox'
import classes from './FileDetails.module.css'
import JobsTable from './tables/JobsTable'
import MachinesTable from './tables/MachinesTable'
import OperationsTable from './tables/OperationsTable'

const FileDetails = (props) => {

    const interpretedJson = JSON.parse(props.file.data)
    console.log(interpretedJson)
    return (
        <div className={classes.content}>
            <div className={classes.planTitle}>
                <input type="text" defaultValue="New Plan" className={classes.titleInput} ref={props.nameRef}/>
                <h4>Created from {props.file.name}</h4>
            </div>
            <div className={classes.containers}>
                <CollapsableBox titleText="Imported JSON">
                    <div className={classes.jsonString}>
                        <pre>
                            {props.file.data}
                        </pre>
                    </div>
                </CollapsableBox>
                <CollapsableBox titleText="Jobs">
                    <div className={classes.tableBox}>
                        <JobsTable jobs={interpretedJson.jobs}/>
                    </div>
                </CollapsableBox>
                <CollapsableBox titleText="Machines">
                    <div className={classes.tableBox}>
                        <MachinesTable machines={interpretedJson.machines} />
                    </div>
                </CollapsableBox>
                <CollapsableBox titleText="Operations">
                    <div className={classes.tableBox}>
                        <OperationsTable operations={interpretedJson.operations} />
                    </div>
                </CollapsableBox>
            </div>
        </div>
    )
}

export default FileDetails