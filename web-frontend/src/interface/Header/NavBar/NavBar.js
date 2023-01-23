import React from "react";
import classes from "./NavBar.module.css";
import { Link } from "react-router-dom";

const NavBar = () => {
    return (
        <nav className={classes.nav}>
            <Link to="/">Dashboard</Link>
            <Link to="/plans">All Plans</Link>
            <Link to="/simulations">All Simulations</Link>
            <Link to="/vergleich">Compare Simulations</Link>
        </nav>
    );
};

export default NavBar;
