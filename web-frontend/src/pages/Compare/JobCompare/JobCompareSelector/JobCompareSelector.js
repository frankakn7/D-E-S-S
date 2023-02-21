import { faAngleDown, faAngleUp } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import React, { useState } from 'react'
import Button from '../../../../interface/Button/Button'
import JobResultsBox from '../../../Results/JobResults/JobResultsBox/JobResultsBox'
import classes from './JobCompareSelector.module.css'

/**
 * A selector with up and down buttons to display a single job box at a time for comparison with another 
 * @param {Object} props - all props passed to this element
 * @param {Array<Object>} props.jobs - a list of all jobs of a simulaton 
 * @param {String} props.simName - the name of the simulation 
 * @returns 
 */
const JobCompareSelector = (props) => {
    //Saves the selected index in the list of jobs
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