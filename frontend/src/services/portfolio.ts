const PORTFOLIO_API_URL = "http://localhost:8080/portfolios";

export const getPortfolios = async () => {
    return await fetch(PORTFOLIO_API_URL).then(async (response) => {
        return await response.json();
    })
}

export const savePortfolio = async (portfolio: object) => {
    return await fetch(PORTFOLIO_API_URL, {
        method: "POST",
        headers: {
            "Authorization": `Basic ${btoa('user:password')}`,
            "Content-Type": "application/json",
        },
        body: JSON.stringify(portfolio),
    }).then(async (response) => {
        return await response.json();
    });
}
