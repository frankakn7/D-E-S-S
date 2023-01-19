import React, { useEffect, useState } from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import useApiPlanHandler from "./hooks/use-api-plan-handler";
import useApiSimCaseHandler from "./hooks/use-api-sim-case-handler";
import useHttp from "./hooks/use-http";
import FourOFour from "./pages/404/FourOFour";
import Layout from "./pages/Layout/Layout";
import Plans from "./pages/Plans/Plans";
import Results from "./pages/Results/Results";
import Upload from "./pages/Upload/Upload";
import Simulations from "./pages/Simulations/Simulations";

function App() {
  const baseUrl = "http://localhost:8080";

  const { sendRequest: sendHttpRequest } = useHttp();

  const [plans, setPlans] = useState([]);
  const [simCases, setSimCases] = useState([]);

  const { handlePlanUpload, handlePlanSimulate } = useApiPlanHandler(
    baseUrl,
    setPlans
  );
  const { handleGetSimResult, handleGetSimStatus } = useApiSimCaseHandler(
    baseUrl,
    setSimCases
  );

  useEffect(() => {
    const loadDataFromBackend = () => {
      const planPromise = sendHttpRequest({
        url: baseUrl + "/api/plan/all",
      });

      const resultPromise = sendHttpRequest({
        url: baseUrl + "/api/sim/all",
      });

      Promise.all([planPromise, resultPromise])
        .then((values) => {
          const [planData, resultData] = values;

          setPlans(planData.plans);
          setSimCases(resultData.sim_cases);
        })
        .catch((error) => console.log(error));
    };

    loadDataFromBackend();
  }, [sendHttpRequest]);

  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Layout />}>
          <Route
            index
            element={
              <Upload
                planUploadHandler={handlePlanUpload}
                planSimulateHandler={handlePlanSimulate}
                getSimCaseStatusHandler={handleGetSimStatus}
                getSimCaseResultHandler={handleGetSimResult}
              />
            }
          />
          <Route
            path="upload"
            element={
              <Upload
                planUploadHandler={handlePlanUpload}
                planSimulateHandler={handlePlanSimulate}
                getSimCaseStatusHandler={handleGetSimStatus}
                getSimCaseResultHandler={handleGetSimResult}
              />
            }
          />
          <Route path="results/:id" element={<Results simCases={simCases} />} />
          <Route
            path="plans"
            element={
              <Plans
                plans={plans}
                planSimulateHandler={handlePlanSimulate}
                getSimCaseStatusHandler={handleGetSimStatus}
                getSimCaseResultHandler={handleGetSimResult}
              />
            }
          />
          <Route
            path="simulations"
            element={
              <Simulations
                plans={plans}
                planSimulateHandler={handlePlanSimulate}
                getSimCaseStatusHandler={handleGetSimStatus}
                getSimCaseResultHandler={handleGetSimResult}
              />
            }
          />
          <Route path="*" element={<FourOFour />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
