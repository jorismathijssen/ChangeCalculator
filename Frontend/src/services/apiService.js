export async function fetchChange(purchaseAmount, cashGiven) {
    const response = await fetch("http://localhost:8080/api/change", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ totalAmount: purchaseAmount, cashGiven: cashGiven }),
    });
  
    if (!response.ok) {
      throw new Error("Fout bij ophalen wisselgeld. Controleer invoerwaarden.");
    }
  
    return response.json();
  }
  