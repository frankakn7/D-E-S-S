import { faAngleLeft, faAngleRight } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import Box from "../../interface/Box/Box";
import Button from "../../interface/Button/Button";
import classes from "./CompareButton.module.css";

const CompareSelection = (props) => {
    const [notSelectedIds, setNotSelectedIds] = React.useState([]);
    const [selectedIds, setSelectedIds] = React.useState([]);

    const navigate = useNavigate();

    useEffect(() => {
        setNotSelectedIds(props.simCases);
    }, [props.simCases]);

    const removeItem = (selectedSimCase) => {
        if (selectedIds.length == 2) {
            return;
        }
        setNotSelectedIds((previousList) =>
            previousList.filter((simCase) => simCase.id != selectedSimCase.id)
        );
        setSelectedIds((previousList) => [selectedSimCase, ...previousList]);
    };

    const removeSelectedItem = (selectedSimCase) => {
        setSelectedIds((previousList) =>
            previousList.filter((simCase) => simCase.id != selectedSimCase.id)
        );
        setNotSelectedIds((previousList) => [selectedSimCase, ...previousList]);
    };

    return (
        <div className={classes.content}>
            <div className={classes.half}>
                <Box titleText={<p>All Simulations</p>}>
                    <div className={classes.lists}>
                        {notSelectedIds &&
                            notSelectedIds.map((simCase) => (
                                <Button
                                    className={classes.button}
                                    onClick={() => removeItem(simCase)}
                                >
                                    Simulation of
                                    "{
                                        props.plans.find(
                                            (plan) =>
                                                simCase.planId === plan.uuid
                                        ).name
                                    }" {"   "}
                                    ({simCase.id})
                                    <FontAwesomeIcon icon={faAngleRight} />
                                </Button>
                            ))}
                    </div>
                </Box>
            </div>
            <div className={classes.half}>
                <Box titleText={<p>Selected Simulations</p>}>
                    <div className={classes.lists}>
                        {selectedIds &&
                            selectedIds.map((simCase) => (
                                <Button
                                    className={classes.button}
                                    onClick={() => removeSelectedItem(simCase)}
                                >
                                    <FontAwesomeIcon icon={faAngleLeft} />
                                    Simulation of
                                    "{
                                        props.plans.find(
                                            (plan) =>
                                                simCase.planId === plan.uuid
                                        ).name
                                    }" {"   "}
                                    ({simCase.id})
                                </Button>
                            ))}
                    </div>
                </Box>
                <Button
                    className={`${
                        selectedIds.length !== 2 ? classes.disabledButton : ""
                    }`}
                    disabled={selectedIds.length !== 2}
                    onClick={() =>
                        navigate(selectedIds[0].id + "/" + selectedIds[1].id)
                    }
                >
                    Compare
                </Button>
            </div>
        </div>
    );
};

export default CompareSelection;
