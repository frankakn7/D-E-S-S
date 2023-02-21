import { useNavigate } from "react-router-dom";
import useHttp from "./use-http";

/**
 * Hook for handling API interactions concerning simCases as well as updating simCases state when necessary
 * @param {String} baseUrl - base url for all API calls (e.g. https://localhost:8080/api/)
 * @param {Function} setSimCases - function to set the list State all simulation cases
 * @param {Function} setNotDoneSims - function to set the list of all the not done simulation cases (loading bars)
 * @returns 
 */
const useApiSimCaseHandler = (baseUrl, setSimCases, setNotDoneSims) => {
    const { sendRequest: sendHttpRequest } = useHttp();

    /**
     * Handles the API call to get a specific simulation case
     * Updated the simCases to include the newly fetched simulation
     * @param {String} simCaseId - the ID of the requested simulation case
     * @returns 
     */
    const handleGetSimCase = (simCaseId) => {
        return new Promise((resolve, reject) => {
            sendHttpRequest({
                url: baseUrl + "/api/sim/" + simCaseId,
                method: "GET",
            })
                .then((response) => {
                    const simCase = {
                        id: simCaseId,
                        results: response.results,
                        planId: response.planId,
                        createdOn: response.createdOn,
                    };
                    setSimCases((prevState) => [...prevState, simCase]);
                    resolve(response.results);
                })
                .catch((error) => {
                    reject(error);
                });
        });
    };

    /**
     * Handles API call to get the status of a currently running simulation
     * @param {String} simCaseId - the ID of the currently running simulation
     * @returns the response object of the fetch
     */
    const handleGetSimStatus = (simCaseId) => {
        return new Promise((resolve, reject) => {
            sendHttpRequest({
                url: baseUrl + "/api/sim/" + simCaseId + "/status",
                method: "GET",
            })
                .then((response) => {
                    resolve(response);
                })
                .catch((error) => {
                    reject(error);
                });
        });
    };

    /**
     * Checks every second if a simulation is done and updates the notDoneSims objects accordingly 
     * @param {String} simId - ID of the running simulation to be checked
     * @param {number} count - counter of the amount of times that a simulation was checked (used to enforce maximum checking up to 200 seconds)
     */
    const checkIfDone = (simId, count=0) => {

        handleGetSimStatus(simId)
            .then((result) => {
                if(count > 200){
                    throw Error("Simulation Timeout")
                }
                count++;
                if (result.state === "done") {
                    handleGetSimCase(simId)
                        .then((response) => {
                            setNotDoneSims((prevState) => prevState.filter((notDoneSim) => notDoneSim.simCaseId !== simId))
                        })
                        .catch((error) => console.log(error));
                }else if(result.state === "logical_error"){
                    setNotDoneSims((prevState) => prevState.map((notDoneSim) => notDoneSim.simCaseId === simId ? {...notDoneSim, ...result} : notDoneSim))
                }else {
                    setNotDoneSims((prevState) => prevState.map((notDoneSim) => notDoneSim.simCaseId === simId ? {...notDoneSim, ...result} : notDoneSim))
                    const timer = setTimeout(() => {
                        checkIfDone(simId,count);
                        clearTimeout(timer);
                    }, 1000);
                }
            })
            .catch((error) => console.log(error));
    };

    /**
     * Handles the API call to delete a specific simulation
     * Also updates simCases state to remove the deleted simulation
     * @param {String} simCaseId - ID of the simulation case to be deleted
     * @returns 
     */
    const handleDeleteSimCase = (simCaseId) => {
        return new Promise((resolve, reject) => {
            sendHttpRequest({
                url: baseUrl + "/api/sim/" + simCaseId,
                method: "DELETE"
            }).then((response) => {
                setSimCases((prevState) => prevState.filter((simCase) => simCase.id !== simCaseId))
                resolve();
            }).catch((error) => {
                reject(error);
            })
        })
    }

    return { handleGetSimCase, handleGetSimStatus, checkIfDone, handleDeleteSimCase };
};

export default useApiSimCaseHandler;
