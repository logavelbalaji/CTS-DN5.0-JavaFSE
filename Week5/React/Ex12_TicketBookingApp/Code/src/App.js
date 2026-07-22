import React, { useState } from 'react';
import './App.css';

function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const flights = [
    { id: 'FL101', airline: 'IndiGo', route: 'Chennai (MAA) ➔ Delhi (DEL)', time: '08:30 AM', price: 5400 },
    { id: 'FL202', airline: 'Air India', route: 'Mumbai (BOM) ➔ Bengaluru (BLR)', time: '11:15 AM', price: 6200 },
    { id: 'FL303', airline: 'SpiceJet', route: 'Kolkata (CCU) ➔ Hyderabad (HYD)', time: '02:45 PM', price: 4900 },
    { id: 'FL404', airline: 'Vistara', route: 'Delhi (DEL) ➔ Mumbai (BOM)', time: '06:00 PM', price: 7800 }
  ];
  const handleBook = (flightId) => {
    alert(`Successfully Booked Flight ticket: ${flightId}! Bon Voyage!`);
  };
  const handleAuthAction = () => {
    setIsLoggedIn(!isLoggedIn);
  };
  return (
    <div className="app-container">
      <header className="app-header">
        <h1>Flight Booking Portal</h1>
        <button onClick={handleAuthAction} className={`auth-btn ${isLoggedIn ? 'logout' : 'login'}`}>
          {isLoggedIn ? 'Logout' : 'Login'}
        </button>
      </header>
      <div className="portal-banner">
        {isLoggedIn ? (
          <div className="user-welcome">
            <h2>Welcome Back, Traveler!</h2>
            <p>You are now logged in and have full booking access to all flights.</p>
          </div>
        ) : (
          <div className="guest-welcome">
            <h2>Welcome, Guest!</h2>
            <p>Please log in using the button above to enable flight ticket bookings.</p>
          </div>
        )}
      </div>
      <div className="flights-section">
        <h2>Available Flights</h2>
        <div className="flights-grid">
          {flights.map((flight) => (
            <div key={flight.id} className="flight-card">
              <div className="flight-header">
                <h3>{flight.airline}</h3>
                <span className="flight-id">{flight.id}</span>
              </div>
              <p className="route"><strong>Route:</strong> {flight.route}</p>
              <p className="time"><strong>Departure:</strong> {flight.time}</p>
              <div className="flight-footer">
                <span className="price">Rs. {flight.price.toLocaleString()}</span>
                {isLoggedIn ? (
                  <button onClick={() => handleBook(flight.id)} className="book-btn active">
                    Book Ticket
                  </button>
                ) : (
                  <button className="book-btn disabled" disabled title="Login to book tickets">
                    Login to Book
                  </button>
                )}
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}

export default App;
