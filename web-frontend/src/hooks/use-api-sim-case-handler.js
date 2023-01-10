import useHttp from "./use-http"

const useApiSimCaseHandler = (baseUrl, setResults) => {
    const { sendRequest: sendHttpRequest } = useHttp();

    const handleGetSimResult = (simCaseId) => {
        return new Promise((resolve, reject) => {
            sendHttpRequest({
                url: baseUrl + "/api/sim/" + simCaseId + "/results",
                method: "GET",
            }).then((response) => {
                const result = {id: simCaseId, results: response.results}
                console.log(result)
                setResults((prevState) => [...prevState, result])
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

    return { handleGetSimResult, handleGetSimStatus }
}

export default useApiSimCaseHandler;