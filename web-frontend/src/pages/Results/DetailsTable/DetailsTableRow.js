import { faAngleDown, faAngleUp } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import React, { Fragment, useState } from "react";
import classes from "./DetailsTableRow.module.css";

/**
 * A table row element displaying detailed statistical values (mean, min, max standard_deviation, variance)
 * @param {Object} props all values passed to the element 
 * @param {Boolean} props.percentage if value is a percentage
 * @param {Object} props.stat object containing the statistical values
 * @param {String} props.stat.name name of the statistical value being displayed
 * @param {Double} props.stat.min minimum value
 * @param {Double} props.stat.max max value
 * @param {Double} props.stat.mean mean value
 * @param {Double} props.stat.standard_deviation standard deviation value
 * @param {Double} props.stat.variance variance value
 * @returns 
 */
const DetailsTableRow = (props) => {
    const [isOpen, setIsOpen] = useState(false);

    const getValueText = (value) => {
        return props.percentage ? `${parseFloat(value * 100).toFixed(0)}%` : parseFloat(value).toFixed(2)
    }

    return (
        // <tbody className={classes.allStatData}>
        <Fragment>
            <tbody>
                <tr
                    className={`${classes.clickableRow} ${isOpen ? classes.openClickableRow : ""}`}
                    onClick={() => setIsOpen((prevState) => !prevState)}
                >
                    <td>mean {props.stat.name}</td>
                    <td>{getValueText(props.stat.mean)}</td>
                    <td className={classes.angleIcon}>
                        {!isOpen && <FontAwesomeIcon icon={faAngleDown} />}
                        {isOpen && <FontAwesomeIcon icon={faAngleUp} />}
                    </td>
                </tr>
            </tbody>
            {isOpen && (
                <tbody className={classes.dataRows}>
                    <tr>
                        <td>min {props.stat.name}</td>
                        <td>{getValueText(props.stat.min)}</td>
                    </tr>
                    <tr>
                        <td>max {props.stat.name}</td>
                        <td>{getValueText(props.stat.max)}</td>
                    </tr>
                    <tr>
                        <td>standard deviation {props.stat.name}</td>
                        <td>{getValueText(props.stat.standard_deviation)}</td>
                    </tr>
                    <tr>
                        <td>variance {props.stat.name}</td>
                        <td>{getValueText(props.stat.variance)}</td>
                    </tr>
                </tbody>
            )}
        {/* // </tbody> */}
        </Fragment>
    );
};

export default DetailsTableRow;
