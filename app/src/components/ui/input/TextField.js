import React from 'react';

const TextField = (props) => {
    return (
        <div className="mt-4">
            <label
                htmlFor={props.htmlFor}
                className="block text-sm font-medium text-gray-200 undefined"
            >
                {props.label}
            </label>
            <div className="flex flex-col items-start">
                <input
                    type={props.type}
                    name={props.name}
                    className="block w-full mt-1 border border-gray-200 rounded-md bg-transparent shadow-sm focus:border-indigo-300 focus:ring focus:ring-indigo-200 focus:ring-opacity-50"
                />
            </div>
        </div>
    );
};

export default TextField;