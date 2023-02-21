import React from "react";
import classes from "./NavBar.module.css";
import { Link } from "react-router-dom";

/**
 * Navigation bar element with links to all the available pages
 * @returns 
 */
const NavBar = () => {
    return (
        <nav className={classes.nav}>
            <Link to="/">Dashboard</Link>
            <Link to="/plans">All Plans</Link>
            <Link to="/simulations">All Simulations</Link>
            <Link to="/compare">Compare Simulations</Link>
        </nav>
    );
};

export default NavBar;
