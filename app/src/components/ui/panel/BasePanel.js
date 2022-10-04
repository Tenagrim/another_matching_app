import React from 'react';


const BasePanel = (props) => {
    return (
        <div className="mask-custom w-full px-6 py-4 mt-6 overflow-hidden  sm:max-w-md">
            {props.children}
        </div>
    );
};

export default BasePanel;