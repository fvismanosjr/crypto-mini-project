const HOLDING_API_URL = "http://localhost:8080/holdings";

export const getHoldings = async () => {
    return await fetch(HOLDING_API_URL).then(async (response) => {
        return await response.json();
    })
}

export const findHolding = async (id: number) => {
    return await fetch(`${HOLDING_API_URL}/${id}`).then(async (response) => {
        return await response.json();
    })
}

export const saveHolding = async (holding: object) => {
    return await fetch(HOLDING_API_URL, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(holding),
    }).then(async (response) => {
        return await response.json();
    });
}
