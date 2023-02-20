import React, { useEffect, useState } from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import useApiPlanHandler from "./hooks/use-api-plan-handler";
import useApiSimCaseHandler from "./hooks/use-api-sim-case-handler";
import useHttp from "./hooks/use-http";
import FourOFour from "./pages/404/FourOFour";
import Dashboard from "./pages/Dashboard/Dashboard";
import Layout from "./pages/Layout/Layout";
import PlanDetails from "./pages/PlanDetails/PlanDetails";
import Plans from "./pages/Plans/Plans";
import Results from "./pages/Results/Results";
import Simulations from "./pages/Simulations/Simulations";
import ErrorBoundary from "./ErrorBoundaries/ErrorBoundary";
import CompareSelection from "./pages/Compare/CompareSelection";
import Compare from "./pages/Compare/Compare";

function App() {
    const baseUrl = "http://localhost:8080";

    const { sendRequest: sendHttpRequest } = useHttp();

    const [plans, setPlans] = useState([]);
    const [simCases, setSimCases] = useState([]);
    const [notDoneSims, setNotDoneSims] = useState([]);

    const { handlePlanUpload, handlePlanSimulate, handlePlanDelete } =
        useApiPlanHandler(baseUrl, setPlans, setNotDoneSims);
    const {
        handleGetSimCase,
        handleGetSimStatus,
        checkIfDone,
        handleDeleteSimCase,
    } = useApiSimCaseHandler(baseUrl, setSimCases, setNotDoneSims);

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
            <ErrorBoundary>
                <ErrorBoundary allowChildren={true}>
                    <Routes>
                        <Route path="/" element={<Layout />}>
                            <Route
                                index
                                element={
                                    <Dashboard
                                        plans={plans}
                                        simCases={simCases}
                                        planUploadHandler={handlePlanUpload}
                                        planSimulateHandler={handlePlanSimulate}
                                        planDeleteHandler={handlePlanDelete}
                                        getSimCaseStatusHandler={
                                            handleGetSimStatus
                                        }
                                        getSimCaseResultHandler={
                                            handleGetSimCase
                                        }
                                        checkIfDoneHandler={checkIfDone}
                                    />
                                }
                            />
                            <Route
                                path="results/:id"
                                element={
                                    <Results
                                        simCases={simCases}
                                        plans={plans}
                                        simCaseDeleteHandler={
                                            handleDeleteSimCase
                                        }
                                    />
                                }
                            />
                            <Route
                                path="plans"
                                element={
                                    <Plans
                                        plans={plans}
                                        planSimulateHandler={handlePlanSimulate}
                                        planDeleteHandler={handlePlanDelete}
                                        getSimCaseStatusHandler={
                                            handleGetSimStatus
                                        }
                                        getSimCaseResultHandler={
                                            handleGetSimCase
                                        }
                                    />
                                }
                            />
                            <Route
                                path="plans/:id"
                                element={
                                    <PlanDetails
                                        plans={plans}
                                        simCases={simCases}
                                        planSimulateHandler={handlePlanSimulate}
                                        getSimCaseStatusHandler={
                                            handleGetSimStatus
                                        }
                                        getSimCaseResultHandler={
                                            handleGetSimCase
                                        }
                                        checkIfDoneHandler={checkIfDone}
                                    />
                                }
                            />
                            <Route
                                path="simulations"
                                element={
                                    <Simulations
                                        simCases={simCases}
                                        notDoneSims={notDoneSims}
                                        plans={plans}
                                        planSimulateHandler={handlePlanSimulate}
                                        getSimCaseStatusHandler={
                                            handleGetSimStatus
                                        }
                                        getSimCaseResultHandler={
                                            handleGetSimCase
                                        }
                                    />
                                }
                            />
                            <Route
                                path="compare"
                                element={
                                    <CompareSelection
                                        simCases={simCases}
                                        plans={plans}
                                    />
                                }
                            />
                            <Route
                                path="compare/:id1/:id2"
                                element={
                                    <Compare
                                        simCases={simCases}
                                        plans={plans}
                                    />
                                }
                            />
                            <Route path="*" element={<FourOFour />} />
                        </Route>
                    </Routes>
                </ErrorBoundary>
            </ErrorBoundary>
        </BrowserRouter>
    );
}

export default App;
