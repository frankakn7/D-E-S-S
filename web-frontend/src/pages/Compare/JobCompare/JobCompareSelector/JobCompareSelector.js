import { faAngleDown, faAngleUp } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import React, { useState } from 'react'
import Button from '../../../../interface/Button/Button'
import JobResultsBox from '../../../Results/JobResults/JobResultsBox/JobResultsBox'
import classes from './JobCompareSelector.module.css'

const JobCompareSelector = (props) => {
    const [selected, setSelected] = useState(0)

    const minusSelected = () => {
        setSelected((prevState) => {
            if(prevState >= 1){
                return prevState-1;
            }
            return prevState;
        })
    }

    const plusSelected = () => {
        setSelected((prevState) => {
            if(prevState < props.jobs.length - 1){
                return prevState += 1;
            }
            return prevState;
        })
    }

    return (
        <div className={classes.selectorContainer}>
            <Button onClick={minusSelected}>
                <FontAwesomeIcon icon={faAngleUp} />
            </Button>
            <div className={classes.jobBoxContainer}>
                <JobResultsBox job={props.jobs[selected]} className={classes.jobBox} extraTitleText={"in "+props.simName} />
            </div>
            <Button onClick={plusSelected}>
                <FontAwesomeIcon icon={faAngleDown} />
            </Button>
        </div>
    );
}

export default JobCompareSelector