import React from 'react';

import {
    Link
} from "react-router-dom";
import BasePanel from "./ui/panel/BasePanel";
import TextField from "./ui/input/TextField";
import Logo from "./icons/Logo";

export default function SignInForm() {
    return (
        <div>
            <div className="flex flex-col items-center min-h-screen pt-6 sm:justify-center sm:pt-0 ">
                <Logo/>
                <BasePanel>
                    <form>
                        <TextField label="Email" name="email" type="email"/>
                        <TextField label="Password" name="password" type="password"/>
                        <div className="flex items-center justify-end mt-4">
                            <Link
                                to="/signup"
                                className="text-sm text-gray-200 underline hover:text-gray-900"
                            >
                                Create an account
                            </Link>
                            <button
                                type="submit"
                                className="inline-flex items-center px-4 py-2 ml-4 text-xs font-semibold tracking-widest text-white uppercase transition duration-150 ease-in-out bg-gray-900 border border-transparent rounded-md active:bg-gray-900 false"
                            >
                                Sign in
                            </button>
                        </div>
                    </form>
                </BasePanel>
            </div>
        </div>
    );
}