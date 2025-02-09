import { useState, useEffect } from "react";
import ChangeDisplay from "./ChangeDisplay";
import { fetchChange } from "../services/apiService";
import icon from "../assets/icon.webp";

export default function CashRegister() {
  const [purchaseAmount, setPurchaseAmount] = useState("");
  const [cashGiven, setCashGiven] = useState("");
  const [currency, setCurrency] = useState("EUR");
  const [loading, setLoading] = useState(false);
  const [changeData, setChangeData] = useState(null);
  const [error, setError] = useState(null);
  const [resetChecked, setResetChecked] = useState(false);
  const [inputError, setInputError] = useState({
    purchase: false,
    cash: false,
  });
  const [typingTimeout, setTypingTimeout] = useState(null);

  const handleCalculateChange = async () => {
    let newPurchaseAmount = parseFloat(purchaseAmount);
    let newCashGiven = parseFloat(cashGiven);

    let isValid = true;
    let newInputError = { purchase: false, cash: false };

    if (isNaN(newPurchaseAmount) || newPurchaseAmount <= 0) {
      newInputError.purchase = true;
      isValid = false;
    }

    if (isNaN(newCashGiven) || newCashGiven < newPurchaseAmount) {
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
    setResetChecked((prev) => !prev);

    try {
      const data = await fetchChange(newPurchaseAmount, newCashGiven, currency);
      setChangeData(data);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    const newPurchaseAmount = parseFloat(purchaseAmount);
    const newCashGiven = parseFloat(cashGiven);

    if (
      !isNaN(newPurchaseAmount) &&
      newPurchaseAmount > 0 &&
      !isNaN(newCashGiven) &&
      newCashGiven >= newPurchaseAmount
    ) {
      if (typingTimeout) clearTimeout(typingTimeout);

      setTypingTimeout(setTimeout(() => handleCalculateChange(), 500));
    }
  }, [cashGiven]);

  const handleReset = () => {
    setPurchaseAmount("");
    setCashGiven("");
    setChangeData(null);
    setError(null);
    setInputError({ purchase: false, cash: false });
    setResetChecked((prev) => !prev);
  };

  return (
    <div className="flex flex-col md:flex-row h-screen w-screen">
      <div className="w-full md:w-1/4 bg-gray-100 p-6 md:p-8 flex flex-col">
        <h1 className="text-lg md:text-xl font-bold text-gray-700 mb-6 text-center md:text-left">
          Wisselgeld Systeem
        </h1>

        {/* Currency Selection */}
        <div className="mb-4">
          <label className="block text-gray-600 font-medium mb-1">Valuta</label>
          <select
            value={currency}
            onChange={(e) => setCurrency(e.target.value)}
            className="w-full p-3 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
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
            min="0"
            value={purchaseAmount}
            onChange={(e) => {
              const value = e.target.value;
              setPurchaseAmount(
                value === "" ? "" : Math.max(0, parseFloat(value))
              );
            }}
            className={`w-full p-3 border rounded-lg focus:outline-none focus:ring-2 ${
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
            min="0"
            value={cashGiven}
            onChange={(e) => {
              const value = e.target.value;
              setCashGiven(value === "" ? "" : Math.max(0, parseFloat(value)));
            }}
            className={`w-full p-3 border rounded-lg focus:outline-none focus:ring-2 ${
              inputError.cash
                ? "border-red-500 ring-red-500"
                : "focus:ring-blue-500"
            }`}
            placeholder="Voer betaald bedrag in"
          />
        </div>

        {/* Error Message */}
        {error && <p className="text-red-500 mb-4 text-center">{error}</p>}

        {/* Action Buttons */}
        <div className="flex flex-col md:flex-row space-y-2 md:space-y-0 md:space-x-4">
          <button
            onClick={handleCalculateChange}
            className="w-full md:w-1/2 bg-blue-500 text-white font-semibold py-3 rounded-lg hover:bg-blue-600 transition"
          >
            Bereken
          </button>

          <button
            onClick={handleReset}
            className="w-full md:w-1/2 bg-gray-400 text-white font-semibold py-3 rounded-lg hover:bg-gray-500 transition"
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
        resetChecked={resetChecked}
        icon={icon}
      />
    </div>
  );
}
