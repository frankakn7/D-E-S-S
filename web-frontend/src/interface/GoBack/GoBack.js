import { faArrowLeft } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import React from "react";
import { useNavigate } from "react-router-dom";
import Button from "../Button/Button";

/**
 * Button element that enables the navigating back to the previous web address
 * Includes styling with go back text and an arrow to the left
 * @returns 
 */
const GoBack = () => {

    const navigate = useNavigate();

    return (
        <Button onClick={() => navigate(-1)}>
            <FontAwesomeIcon icon={faArrowLeft}/> Go Back
        </Button>
    );
};

export default GoBack;
