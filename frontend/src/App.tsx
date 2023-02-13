import React from 'react';
import './App.css';
import ResponsiveDrawer from "./components/drawer";
import {BrowserRouter} from "react-router-dom";
import {AppRoutes} from "./components/routes";
import {QueryClientProvider} from "@tanstack/react-query";
import queryClient from "./config/client";
import './config/axios';
import {ToastContainer} from "react-toastify";
import 'react-toastify/dist/ReactToastify.css';

function App() {
    return (
        <QueryClientProvider client={queryClient}>
            <BrowserRouter>
                <ResponsiveDrawer>
                    <AppRoutes/>
                </ResponsiveDrawer>
            </BrowserRouter>
            <ToastContainer />
        </QueryClientProvider>
    );
}

export default App;
