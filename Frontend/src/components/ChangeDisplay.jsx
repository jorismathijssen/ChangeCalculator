import { useState, useEffect } from "react";

export default function ChangeDisplay({ loading, changeData, error, resetChecked }) {
  const [checkedItems, setCheckedItems] = useState({});

  useEffect(() => {
    setCheckedItems({}); // Reset checked state when resetChecked changes
  }, [resetChecked]);

  const toggleCheck = (denomination) => {
    setCheckedItems((prev) => ({
      ...prev,
      [denomination]: !prev[denomination],
    }));
  };

  return (
    <div className="w-full md:w-3/4 h-1/2 md:h-full flex flex-col bg-white shadow-lg rounded-xl overflow-hidden">
      <div className="flex-grow overflow-y-auto scroll-smooth p-4 text-gray-700 text-center w-full max-w-lg mx-auto">
        {loading ? (
          <p className="text-xl font-semibold text-blue-500">Laden...</p>
        ) : error ? (
          <p className="text-red-500 text-xl font-semibold">{error}</p>
        ) : changeData ? (
          <>
            <h2 className="text-3xl font-bold mb-6">Wisselgeld:</h2>
            <p className="text-xl font-semibold bg-blue-100 text-blue-800 px-6 py-3 rounded-lg inline-block shadow-md">
              Totaal: {changeData.currency} {changeData.changeAmount.toFixed(2)}
            </p>
            <ul className="mt-6 text-lg space-y-2">
              {Object.entries(changeData.changeBreakdown).map(
                ([denomination, count]) => (
                  <li
                    key={denomination}
                    onClick={() => toggleCheck(denomination)}
                    className={`flex justify-between px-5 py-3 rounded-lg shadow-sm border border-gray-200 cursor-pointer transition 
                      ${
                        checkedItems[denomination]
                          ? "bg-green-500 text-white line-through"
                          : "bg-gray-50 text-blue-600"
                      }`}
                  >
                    <span className="font-medium">{denomination}</span>
                    <span className="font-semibold">{count}x</span>
                  </li>
                )
              )}
            </ul>
          </>
        ) : (
          <p className="text-gray-400 text-xl font-light">Geen wisselgeld berekend.</p>
        )}
      </div>
    </div>
  );
}
