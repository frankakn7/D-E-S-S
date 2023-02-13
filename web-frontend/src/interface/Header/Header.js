import React from "react";
import { Link } from "react-router-dom";
import classes from "./Header.module.css";
import NavBar from "./NavBar/NavBar";
import { ReactComponent as Logo } from "../../Images/logo.svg";

const Header = () => {
    return (
        <div className={classes.main}>
            <Link to="/" className={classes.logo}>
                {/* <h1>D.E.S.S.</h1> */}
                <Logo width={100} height={100}/>
            </Link>
            <NavBar />
        </div>
    );
};

export default Header;
