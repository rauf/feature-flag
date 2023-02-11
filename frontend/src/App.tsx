import React from 'react';
import './App.css';
import ResponsiveDrawer from "./components/drawer";
import {BrowserRouter} from "react-router-dom";
import {AppRoutes} from "./components/routes";

function App() {
    return (
        <BrowserRouter>
            <ResponsiveDrawer>
                <AppRoutes/>
            </ResponsiveDrawer>
        </BrowserRouter>
    );
}

export default App;
