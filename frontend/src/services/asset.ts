export const getAssets = async () => {
    return await fetch("http://localhost:8080/assets").then(async (response) => {
        return await response.json();
    });
}
