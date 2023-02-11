import {Route, Routes} from "react-router-dom";
import FlagPage from "../pages/flags";
import EvaluatePage from "../pages/evaluate";


export const AppRoutes = () => {
    return (
        <div className="view-routes">
            <Routes>
                <Route path="/flags" element={<FlagPage/>}/>
                <Route path="/evaluate" element={<EvaluatePage/>}/>
            </Routes>
        </div>
    );
};
