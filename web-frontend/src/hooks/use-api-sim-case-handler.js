import useHttp from "./use-http"

const useApiSimCaseHandler = (baseUrl, setSimCases) => {
    const { sendRequest: sendHttpRequest } = useHttp();

    const handleGetSimCase = (simCaseId) => {
        return new Promise((resolve, reject) => {
            sendHttpRequest({
                url: baseUrl + "/api/sim/" + simCaseId,
                method: "GET",
            }).then((response) => {
                const simCase = {id: simCaseId, results: response.results, plan_id: response.plan_id}
                setSimCases((prevState) => [...prevState, simCase])
                resolve(response.results)
            }).catch((error) => {
                reject(error)
            })
        })
    }

    const handleGetSimStatus = (simCaseId) => {
        return new Promise((resolve, reject) => {
            sendHttpRequest({
                url: baseUrl + "/api/sim/" + simCaseId + "/status",
                method: "GET",
            }).then((response) => {
                resolve(response)
            }).catch((error) => {
                reject(error)
            })
        })
    }

    return { handleGetSimCase, handleGetSimStatus }
}

export default useApiSimCaseHandler;