const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;

export async function fetchChange(purchaseAmount, cashGiven) {
    try {
        const response = await fetch(`${API_BASE_URL}/change`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ totalAmount: purchaseAmount, cashGiven: cashGiven }),
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
