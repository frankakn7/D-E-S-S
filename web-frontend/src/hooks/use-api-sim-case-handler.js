import { useNavigate } from "react-router-dom";
import useHttp from "./use-http";

const useApiSimCaseHandler = (baseUrl, setSimCases) => {
    const { sendRequest: sendHttpRequest } = useHttp();

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

    const handleGetSimStatus = (simCaseId) => {
        return new Promise((resolve, reject) => {
            sendHttpRequest({
                url: baseUrl + "/api/sim/" + simCaseId + "/status",
                method: "GET",
            })
                .then((response) => {
                    console.log(response);
                    resolve(response);
                })
                .catch((error) => {
                    reject(error);
                });
        });
    };

    const checkIfDone = (simId, executeIfDone, count=0) => {

        handleGetSimStatus(simId)
            .then((result) => {
                if(count > 200){
                    throw Error("Simulation Timeout")
                }
                count++;
                if (result.state === "done") {
                    console.log("done");
                    handleGetSimCase(simId)
                        .then((response) => executeIfDone())
                        .catch((error) => console.log(error));
                } else {
                    console.log("not done");
                    const timer = setTimeout(() => {
                        checkIfDone(simId,executeIfDone,count);
                        clearTimeout(timer);
                    }, 1000);
                }
            })
            .catch((error) => console.log(error));
    };

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
