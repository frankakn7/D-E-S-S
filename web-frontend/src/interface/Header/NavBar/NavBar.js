import React from "react";
import classes from "./NavBar.module.css";
import { Link } from "react-router-dom";

const NavBar = () => {
    return (
        <nav className={classes.nav}>
            <Link to="/">Dashboard</Link>
            <Link to="/plans">Alle Pläne</Link>
            <Link to="/simulations">Alle Simulationen</Link>
            <Link to="/vergleich">Vergleichen</Link>
        </nav>
    );
};

export default NavBar;
