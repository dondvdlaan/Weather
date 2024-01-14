import { ReactElement } from "react";
import { Navigate, Route, Routes } from "react-router-dom";
import App from "./App";



export default function Routing(): ReactElement {
    return (
        <Routes>
            <Route path="/dashboard" element={<App />} />

            <Route path="/" element={<Navigate to="/dashboard" />} />

        </Routes>
    );
}