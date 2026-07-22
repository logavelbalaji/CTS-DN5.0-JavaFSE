import React, { useState } from 'react';

const CurrencyConverter = () => {
  const [rupees, setRupees] = useState('');
  const [euros, setEuros] = useState(null);
  const handleSubmit = (e) => {
    e.preventDefault();
    if (rupees) {
      const converted = (Number(rupees) / 90).toFixed(2);
      setEuros(converted);
    }
  };
  return (
    <div className="event-card">
      <h2>Currency Converter (INR to EUR)</h2>
      <form onSubmit={handleSubmit} className="converter-form">
        <div className="input-group">
          <label>Amount in Indian Rupees (INR)</label>
          <input
            type="number"
            value={rupees}
            onChange={(e) => setRupees(e.target.value)}
            placeholder="Enter Rupees"
            required
          />
        </div>
        <button type="submit" className="action-btn">Convert</button>
      </form>
      {euros !== null && (
        <div className="conversion-result">
          <h3>Equivalent Amount:</h3>
          <p className="result-value">€ {euros} EUR</p>
        </div>
      )}
    </div>
  );
};

export default CurrencyConverter;
