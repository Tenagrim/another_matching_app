import React from 'react';
import {Link} from "react-router-dom";

const Logo = () => {
    return (
        <div>
            <Link to="/">
                <h3 className={"text-7xl font-logo drop-shadow-lg text-purple-500  "}>
                    Matcha
                </h3>
            </Link>
        </div>
    );
};

export default Logo;