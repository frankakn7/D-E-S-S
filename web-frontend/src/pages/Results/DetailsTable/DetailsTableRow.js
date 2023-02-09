import { faAngleDown, faAngleUp } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import React, { Fragment, useState } from "react";
import classes from "./DetailsTableRow.module.css";

const DetailsTableRow = (props) => {
    const [isOpen, setIsOpen] = useState(false);

    const getValueText = (value) => {
        return props.percentage ? `${value * 100}%` : value
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
