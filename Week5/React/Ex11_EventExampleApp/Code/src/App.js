import React, { useState } from 'react';
import CurrencyConverter from './Components/CurrencyConverter';
import './App.css';

function App() {
  const [counter, setCounter] = useState(0);
  const [message, setMessage] = useState('');
  const sayHello = () => {
    setMessage('Hello! The counter has been updated successfully.');
  };
  const handleIncrement = () => {
    setCounter(prev => prev + 1);
    sayHello();
  };
  const handleDecrement = () => {
    setCounter(prev => prev - 1);
    setMessage('Counter decremented.');
  };
  const handleWelcome = (arg) => {
    alert('Received argument: ' + arg);
  };
  const handleSyntheticClick = (e) => {
    alert('I was clicked! Synthetic event type: ' + e.type);
  };
  return (
    <div className="app-container">
      <h1>Event Examples App</h1>
      <div className="events-grid">
        <div className="event-card">
          <h2>Counter Control</h2>
          <div className="counter-display">{counter}</div>
          <div className="btn-group">
            <button onClick={handleIncrement} className="action-btn inc">Increment</button>
            <button onClick={handleDecrement} className="action-btn dec">Decrement</button>
          </div>
          {message && <p className="status-message">{message}</p>}
        </div>
        <div className="event-card">
          <h2>Welcome Trigger</h2>
          <button onClick={() => handleWelcome('welcome')} className="action-btn welcome">
            Say Welcome
          </button>
        </div>
        <div className="event-card">
          <h2>Synthetic Event</h2>
          <button onClick={handleSyntheticClick} className="action-btn synthetic">
            Trigger Synthetic Click
          </button>
        </div>
        <CurrencyConverter />
      </div>
    </div>
  );
}

export default App;
