import { faAngleLeft, faAngleRight } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React from 'react'
import { useNavigate } from 'react-router-dom';
import Button from '../Button/Button';
import classes from './ListButton.module.css'

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