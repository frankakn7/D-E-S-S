import { faAngleLeft, faAngleRight } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import React, { useState, useEffect, Fragment } from "react";
import { useNavigate } from "react-router-dom";
import Box from "../../interface/Box/Box";
import Button from "../../interface/Button/Button";
import ListButton from "../../interface/ListButton/ListButton";
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
                            notSelectedIds.sort((a,b) => new Date(b.createdOn) - new Date(a.createdOn)).map((simCase) => (
                                <ListButton
                                    key={simCase.id}
                                    onClick={() => {
                                        removeItem(simCase);
                                    }}
                                    id={simCase.id}
                                    name={
                                        <Fragment>
                                            {" "}
                                            Simulation of{" "}
                                            <b>
                                                "
                                                {
                                                    props.plans.find(
                                                        (plan) =>
                                                            simCase.planId ===
                                                            plan.uuid
                                                    ).name
                                                }
                                                "
                                            </b>
                                        </Fragment>
                                    }
                                    createdOn={simCase.createdOn}
                                />
                            ))}
                    </div>
                </Box>
            </div>
            <div className={classes.half}>
                <Box titleText={<p>Selected Simulations</p>}>
                    <div className={classes.lists}>
                        {selectedIds &&
                            selectedIds.sort((a,b) => new Date(b.createdOn) - new Date(a.createdOn)).map((simCase) => (
                                <ListButton
                                    key={simCase.id}
                                    arrowLeft={true}
                                    onClick={() => {
                                        removeSelectedItem(simCase);
                                    }}
                                    id={simCase.id}
                                    name={
                                        <Fragment>
                                            {" "}
                                            Simulation of{" "}
                                            <b>
                                                "
                                                {
                                                    props.plans.find(
                                                        (plan) =>
                                                            simCase.planId ===
                                                            plan.uuid
                                                    ).name
                                                }
                                                "
                                            </b>
                                        </Fragment>
                                    }
                                    createdOn={simCase.createdOn}
                                />
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
