import useHttp from "./use-http";

const useApiPlanHandler = (baseUrl, setPlans) => {
    const { sendRequest: sendHttpRequest } = useHttp();

    const handlePlanUpload = (newPlan) => {
        return new Promise((resolve, reject) => {
            sendHttpRequest({
                url: baseUrl + "/api/plan",
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: newPlan,
            }).then((response) => {
                newPlan.uuid = response.plan_id
                setPlans((prevState) => [...prevState, newPlan])
                resolve(response.plan_id)
            }).catch((error) => {
                reject(error)
            })
        })
    }

    const handlePlanSimulate = (planId) => {
        return new Promise((resolve, reject) => {
            sendHttpRequest({
                url: baseUrl + "/api/plan/" + planId + "/simulate",
                method: "GET"
            }).then((response) => {
                resolve(response.sim_case_id)
            }).catch((error) => {
                reject(error)
            })
        })
    }

    return { handlePlanUpload, handlePlanSimulate }
}

export default useApiPlanHandler;