export default function ChangeDisplay({ loading, changeData, error, icon }) {
    return (
      <div className="w-3/4 bg-white p-8 flex items-center justify-center">
        {loading ? (
          <img src={icon} alt="Laden..." className="w-8 h-8 animate-spin" />
        ) : error ? (
          <p className="text-red-500 text-xl">{error}</p>
        ) : changeData ? (
          <div className="text-gray-700">
            <h2 className="text-2xl font-semibold mb-4">Wisselgeld:</h2>
            <p className="text-lg font-medium">Totaal: €{changeData.changeAmount.toFixed(2)}</p>
            <ul className="mt-2 text-lg">
              {Object.entries(changeData.changeBreakdown).map(([denomination, count]) => (
                <li key={denomination}>
                  {count}x {denomination}
                </li>
              ))}
            </ul>
          </div>
        ) : (
          <p className="text-gray-400 text-xl">Wisselgeld overzicht komt hier...</p>
        )}
      </div>
    );
  }
  