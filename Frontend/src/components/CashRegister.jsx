import { useState } from "react";
import ChangeDisplay from "./ChangeDisplay";
import { fetchChange } from "../services/apiService";
import icon from "../assets/icon.webp";

export default function CashRegister() {
  const [purchaseAmount, setPurchaseAmount] = useState("");
  const [cashGiven, setCashGiven] = useState("");
  const [currency, setCurrency] = useState("EUR"); // Default currency
  const [loading, setLoading] = useState(false);
  const [changeData, setChangeData] = useState(null);
  const [error, setError] = useState(null);
  const [inputError, setInputError] = useState({
    purchase: false,
    cash: false,
  });

  const handleCalculateChange = async () => {
    let isValid = true;
    let newInputError = { purchase: false, cash: false };

    if (!purchaseAmount || purchaseAmount <= 0) {
      newInputError.purchase = true;
      isValid = false;
    }

    if (!cashGiven || cashGiven < purchaseAmount) {
      newInputError.cash = true;
      isValid = false;
    }

    if (!isValid) {
      setError("Ongeldige invoer.");
      setInputError(newInputError);
      return;
    }

    setLoading(true);
    setChangeData(null);
    setError(null);
    setInputError({ purchase: false, cash: false });

    try {
      const data = await fetchChange(purchaseAmount, cashGiven, currency); // Pass currency
      setChangeData(data);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  const handleReset = () => {
    setPurchaseAmount("");
    setCashGiven("");
    setChangeData(null);
    setError(null);
    setInputError({ purchase: false, cash: false });
  };

  return (
    <div className="flex h-screen w-screen">
      <div className="w-1/4 bg-gray-100 p-8 flex flex-col">
        <h1 className="text-xl font-bold text-gray-700 mb-6">
          Wisselgeld Systeem 1.1
        </h1>

        {/* Currency Selection */}
        <div className="mb-4">
          <label className="block text-gray-600 font-medium mb-1">Valuta</label>
          <select
            value={currency}
            onChange={(e) => setCurrency(e.target.value)}
            className="w-full p-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
          >
            <option value="EUR">Euro (€)</option>
            <option value="USD">US Dollar ($)</option>
            <option value="GBP">British Pound (£)</option>
          </select>
        </div>

        {/* Purchase Amount Input */}
        <div className="mb-4">
          <label className="block text-gray-600 font-medium mb-1">
            Aankoopbedrag (
            {currency === "EUR" ? "€" : currency === "USD" ? "$" : "£"})
          </label>
          <input
            type="number"
            value={purchaseAmount}
            onChange={(e) => setPurchaseAmount(Number(e.target.value))}
            className={`w-full p-2 border rounded-lg focus:outline-none focus:ring-2 ${
              inputError.purchase
                ? "border-red-500 ring-red-500"
                : "focus:ring-blue-500"
            }`}
            placeholder="Voer aankoopbedrag in"
          />
        </div>

        {/* Cash Given Input */}
        <div className="mb-4">
          <label className="block text-gray-600 font-medium mb-1">
            Contant ({currency === "EUR" ? "€" : currency === "USD" ? "$" : "£"}
            )
          </label>
          <input
            type="number"
            value={cashGiven}
            onChange={(e) => setCashGiven(Number(e.target.value))}
            className={`w-full p-2 border rounded-lg focus:outline-none focus:ring-2 ${
              inputError.cash
                ? "border-red-500 ring-red-500"
                : "focus:ring-blue-500"
            }`}
            placeholder="Voer betaald bedrag in"
          />
        </div>

        {/* Error Message */}
        {error && <p className="text-red-500 mb-4">{error}</p>}

        {/* Action Buttons */}
        <div className="flex space-x-4">
          <button
            onClick={handleCalculateChange}
            className="w-1/2 bg-blue-500 text-white font-semibold py-2 rounded-lg hover:bg-blue-600 transition"
          >
            Bereken
          </button>

          <button
            onClick={handleReset}
            className="w-1/2 bg-gray-400 text-white font-semibold py-2 rounded-lg hover:bg-gray-500 transition"
          >
            Reset
          </button>
        </div>
      </div>

      {/* Change Display Component */}
      <ChangeDisplay
        loading={loading}
        changeData={changeData}
        error={error}
        icon={icon}
      />
    </div>
  );
}
