import React from 'react';
import './App.css';
import ResponsiveDrawer from "./components/drawer";
import {BrowserRouter} from "react-router-dom";
import {AppRoutes} from "./components/routes";
import {QueryClientProvider} from "@tanstack/react-query";
import queryClient from "./config/client";
import './config/axios';

function App() {
    return (
        <QueryClientProvider client={queryClient}>
            <BrowserRouter>
                <ResponsiveDrawer>
                    <AppRoutes/>
                </ResponsiveDrawer>
            </BrowserRouter>
        </QueryClientProvider>
    );
}

export default App;
