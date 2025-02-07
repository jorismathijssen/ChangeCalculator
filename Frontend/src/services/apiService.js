const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;

/**
 * Fetches the change breakdown from the backend API.
 *
 * @param {number} purchaseAmount - The total purchase amount.
 * @param {number} cashGiven - The cash amount provided by the customer.
 * @param {string} currency - The selected currency (e.g., "EUR", "USD", "GBP").
 * @returns {Promise<Object>} - The API response containing the change breakdown.
 * @throws {Error} - Throws an error if the request fails.
 */
export async function fetchChange(purchaseAmount, cashGiven, currency) {
    try {
        const response = await fetch(`${API_BASE_URL}/change`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ 
                totalAmount: purchaseAmount, 
                cashGiven: cashGiven, 
                currency: currency 
            }),
        });

        if (!response.ok) {
            const errorData = await response.json().catch(() => ({}));
            throw new Error(errorData.message || "Failed to retrieve change. Please check your input.");
        }

        return await response.json();
    } catch (error) {
        console.error("API Fetch Error:", error.message);
        throw new Error("An error occurred while fetching change data. Please try again later.");
    }
}
