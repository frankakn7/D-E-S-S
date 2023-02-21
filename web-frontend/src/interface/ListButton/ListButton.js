import { faAngleLeft, faAngleRight } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React from 'react'
import { useNavigate } from 'react-router-dom';
import Button from '../Button/Button';
import classes from './ListButton.module.css'

/**
 * Button element styled specifically for the list of simulatons
 * Contains arrow icons on the left or right, a name and the created on date
 * @param {Object} props - All props passed to this element 
 * @param {Function} props.onClick - function to be executet when this element is clicked
 * @param {Boolean} props.arrowLeft - if true element displays an arrow on the left side otherwise element displays an arrow on the right (content is always pushed to the other side)
 * @param {JSX.Element} props.name - the name / text to be displayed at the top left / right of the button
 * @param {JSX.Element} props.createdOn - the display of the date that the represented element was created on
 * @returns 
 */
const ListButton = (props) => {
    const navigate = useNavigate();

    return (
        <Button
            className={classes.button}
            onClick={props.onClick}
        >
            {props.arrowLeft && <FontAwesomeIcon icon={faAngleLeft} />}
            <div className={classes.buttonText}>
                <div>
                    {props.name} <br />
                </div>
                <div className={classes.date}>created on {props.createdOn}</div>
            </div>
            {!props.arrowLeft && <FontAwesomeIcon icon={faAngleRight} />}
        </Button>
    );
}

export default ListButton