import useHttp from "./use-http";

const useApiPlanHandler = (baseUrl, setPlans, setSimCases, setNotDoneSims) => {
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
            })
                .then((response) => {
                    newPlan = {
                        name: newPlan.name,
                        uuid: response.plan.uuid,
                        planJson: JSON.stringify(newPlan.plan),
                        createdOn: response.plan.createdOn
                    };
                    setPlans((prevState) => [newPlan, ...prevState]);
                    resolve(response.plan.uuid);
                })
                .catch((error) => {
                    reject(error);
                });
        });
    };

    const handlePlanSimulate = (planId, numOfSimulations) => {
        return new Promise((resolve, reject) => {
            sendHttpRequest({
                url: baseUrl + "/api/plan/" + planId + "/simulate/"+numOfSimulations,
                method: "GET",
            })
                .then((response) => {
                    const newNotDone = {
                        planId: planId,
                        simCaseId: response.sim_case_id 
                    }
                    setNotDoneSims((prevState) => [newNotDone,...prevState])
                    resolve(response.sim_case_id);
                })
                .catch((error) => {
                    reject(error);
                });
        });
    };

    const handlePlanDelete = (planId) => {
        return new Promise((resolve, reject) => {
            sendHttpRequest({
                url: baseUrl + "/api/plan/" + planId,
                method: "DELETE"
            }).then((response) => {
                setPlans((prevState) => prevState.filter((plan) => plan.uuid !== planId))
                setSimCases((prevState) => prevState.filter((simCase) => simCase.planId  !== planId))
                resolve();
            }).catch((error) => {
                reject(error);
            })
        })
    }

    return { handlePlanUpload, handlePlanSimulate, handlePlanDelete};
};

export default useApiPlanHandler;
