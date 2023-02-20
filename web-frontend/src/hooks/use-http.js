import { useCallback } from "react";

const useHttp = () => {
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
