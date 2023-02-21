import useHttp from "./use-http";

/**
 * Hook for handling API interactions concerning plans as well as updating plans state when necessary
 * @param {String} baseUrl - base url for all API calls (e.g. https://localhost:8080/api/)
 * @param {Function} setPlans - function to set the list State of all plans
 * @param {Function} setSimCases - function to set the list State all simulation cases
 * @param {Function} setNotDoneSims - function to set the list of all the not done simulation cases (loading bars)
 * @returns 
 */
const useApiPlanHandler = (baseUrl, setPlans, setSimCases, setNotDoneSims) => {
    const { sendRequest: sendHttpRequest } = useHttp();

    /**
     * Handles the API call for uploading a new Plan. 
     * Also updated the plans state to include the newly uploaded plan 
     * @param {Object} newPlan - the new plan to be uploaded
     * @returns 
     */
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

    /**
     * Handles the API call to start the simulation of a new plan. 
     * Also updates the notDoneSims to include the newly created simulation
     * @param {String} planId - the ID of the plan to be simulated
     * @param {number} numOfSimulations - number of simulations that should be run for the plan
     * @returns 
     */
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

    /**
     * Handles the API call to delete a specific plan by ID.
     * Removes the deleted plan from the plans state
     * @param {String} planId - ID of the plan to be deleted
     * @returns 
     */
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
