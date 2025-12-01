const PORTFOLIO_API_URL = "http://localhost:8080/portfolios";

interface PortFolio {
    id: number,
    name: string,
    type: string,
}

export const getPortfolios = async () => {
    return await fetch(PORTFOLIO_API_URL).then(async (response) => {
        return await response.json();
    })
}

export const findPortfolio = async (id: number) => {
    return await fetch(`${PORTFOLIO_API_URL}/${id}`).then(async (response) => {
        return await response.json();
    })
}

export const getPortfoliosWithHoldings = async () => {
    return await fetch(`${PORTFOLIO_API_URL}/with-holdings`).then(async (response) => {
        return await response.json();
    })
}

export const savePortfolio = async (portfolio: PortFolio) => {
    let url = PORTFOLIO_API_URL;
    let method = "POST";

    if (portfolio.id) {
        url += `/${portfolio.id}`;
        method = "PUT";
    }

    return await fetch(url, {
        method: method,
        headers: {
            "Authorization": `Basic ${btoa('user:password')}`,
            "Content-Type": "application/json",
        },
        body: JSON.stringify(portfolio),
    }).then(async (response) => {
        return await response.json();
    });
}

export const deletePortfolio = async (id: number) => {
    return await fetch(`${PORTFOLIO_API_URL}/${id}`, {
        method: "DELETE",
        headers: {
            "Authorization": `Basic ${btoa('user:password')}`,
            "Content-Type": "application/json",
        }
    });
}
