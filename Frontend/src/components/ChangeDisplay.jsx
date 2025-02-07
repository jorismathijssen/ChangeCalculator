import { motion } from "framer-motion";

export default function ChangeDisplay({ loading, changeData, error, icon }) {
  return (
    <motion.div 
      className="w-3/4 bg-white p-10 flex items-center justify-center shadow-lg rounded-xl"
      initial={{ opacity: 0, y: 10 }}
      animate={{ opacity: 1, y: 0 }}
      transition={{ duration: 0.5 }}
    >
      {loading ? (
        <motion.img 
          src={icon} 
          alt="Laden..." 
          className="w-12 h-12 animate-spin"
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          transition={{ duration: 0.5 }}
        />
      ) : error ? (
        <motion.p 
          className="text-red-500 text-2xl font-semibold"
          initial={{ opacity: 0, y: 10 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.5 }}
        >
          {error}
        </motion.p>
      ) : changeData ? (
        <motion.div
          className="text-gray-700 text-center w-full max-w-lg"
          initial={{ opacity: 0, y: 10 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.5 }}
        >
          <h2 className="text-3xl font-bold mb-6">Wisselgeld:</h2>

          {/* Total Change Display */}
          <motion.p 
            className="text-xl font-semibold bg-blue-100 text-blue-800 px-6 py-3 rounded-lg inline-block shadow-md"
            initial={{ opacity: 0, scale: 0.9 }}
            animate={{ opacity: 1, scale: 1 }}
            transition={{ duration: 0.3 }}
          >
            Totaal: {changeData.currency} {changeData.changeAmount.toFixed(2)}
          </motion.p>

          {/* Change Breakdown List */}
          <motion.ul className="mt-6 text-lg space-y-2">
            {Object.entries(changeData.changeBreakdown).map(([denomination, count], i) => (
              <motion.li 
                key={denomination} 
                className="flex justify-between bg-gray-50 px-5 py-3 rounded-lg shadow-sm border border-gray-200"
                initial={{ opacity: 0, y: 10 }}
                animate={{ opacity: 1, y: 0 }}
                transition={{ duration: 0.3, delay: 0.5 * i }}
              >
                <span className="font-medium">{denomination}</span>
                <span className="text-blue-600 font-semibold">{count}x</span>
              </motion.li>
            ))}
          </motion.ul>
        </motion.div>
      ) : (
        <motion.p 
          className="text-gray-400 text-2xl font-light"
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          transition={{ duration: 0.5 }}
        >
          Wisselgeld overzicht komt hier...
        </motion.p>
      )}
    </motion.div>
  );
}
