import { useState } from "react";
import ChangeDisplay from "./ChangeDisplay";
import { fetchChange } from "../services/apiService";
import icon from "../assets/icon.webp";

export default function CashRegister() {
  const [purchaseAmount, setPurchaseAmount] = useState(10);
  const [cashGiven, setCashGiven] = useState(20);
  const [loading, setLoading] = useState(false);
  const [changeData, setChangeData] = useState(null);
  const [error, setError] = useState(null);

  const handleCalculateChange = async () => {
    if (cashGiven < purchaseAmount) {
      setError("Betaald bedrag moet hoger zijn dan het aankoopbedrag.");
      return;
    }

    setLoading(true);
    setChangeData(null);
    setError(null);

    try {
      const data = await fetchChange(purchaseAmount, cashGiven);
      setChangeData(data);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="flex h-screen w-screen">
      {/* Left Panel - Input Form (25%) */}
      <div className="w-1/4 bg-gray-100 p-8 flex flex-col">
        <h1 className="text-xl font-bold text-gray-700 mb-6">Wisselgeld Systeem 1.0</h1>

        {/* Aankoopbedrag Input */}
        <div className="mb-4">
          <label className="block text-gray-600 font-medium mb-1">Aankoopbedrag (€)</label>
          <input
            type="number"
            value={purchaseAmount}
            onChange={(e) => setPurchaseAmount(Number(e.target.value))}
            className="w-full p-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            placeholder="Voer aankoopbedrag in"
          />
        </div>

        {/* Contant Betaald Input */}
        <div className="mb-4">
          <label className="block text-gray-600 font-medium mb-1">Contant betaald (€)</label>
          <input
            type="number"
            value={cashGiven}
            onChange={(e) => setCashGiven(Number(e.target.value))}
            className="w-full p-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            placeholder="Voer betaald bedrag in"
          />
        </div>

        {/* Calculate Button */}
        <button
          onClick={handleCalculateChange}
          className="w-full bg-blue-500 text-white font-semibold py-2 rounded-lg hover:bg-blue-600 transition"
        >
          Wisselgeld berekenen
        </button>
      </div>

      {/* Right Panel - Output (75%) */}
      <ChangeDisplay loading={loading} changeData={changeData} error={error} icon={icon} />
    </div>
  );
}
