import { useCallback } from "react";

/**
 * Hook for easy requesting of urls
 * @returns 
 */
const useHttp = () => {
    /**
     * sends the request using the given request config
     * @param {Object} requestConfig - takes a request config object which includes the method, headers, body, and url of the request
     */
    const sendRequest = useCallback((requestConfig) => {
        return new Promise((resolve, reject) => {
            const requestData = {
                mode: "cors",
                method: requestConfig.method ? requestConfig.method : "GET",
                headers: requestConfig.headers ? requestConfig.headers : {},
                body: requestConfig.body
                    ? JSON.stringify(requestConfig.body)
                    : null,
            };

            fetch(requestConfig.url, requestData)
                .then((response) => {
                    if (!response.ok) {
                        response
                            .text()
                            .then((text) => reject(JSON.parse(text).Error));
                    } else {
                        response
                            .text()
                            .then((text) => {
                                try {
                                    const data = JSON.parse(text);
                                    resolve(data);
                                } catch (err) {
                                    resolve(text);
                                }
                            })
                            .catch((error) => {
                                throw error;
                            });
                    }
                })
                .catch((error) => {
                    reject(error || "Something went wrong whilst fetching!");
                });
        });
    }, []);

    return {
        sendRequest,
    };
};

export default useHttp;
