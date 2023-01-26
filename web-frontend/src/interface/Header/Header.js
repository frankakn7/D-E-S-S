import React from "react";
import { Link } from "react-router-dom";
import classes from "./Header.module.css";
import NavBar from "./NavBar/NavBar";

const Header = () => {
    return (
        <div className={classes.main}>
            <Link to="/">
                <h1>D.E.S.S.</h1>
            </Link>
            <NavBar />
        </div>
    );
};

export default Header;
