import React from 'react';
import './App.css';

function App() {
  const singleOffice = {
    name: 'DBS Premium Office',
    rent: 55000,
    address: 'DLF Cyber City, Phase III, Sector 24, Gurugram, Haryana 122002'
  };
  const officesList = [
    { name: 'Coworking Hub', rent: 45000, address: 'OMR Road, Karapakkam, Chennai, Tamil Nadu 600097' },
    { name: 'DLF IT Park Corporate Space', rent: 85000, address: 'Mount Poonamallee Road, Manapakkam, Chennai, Tamil Nadu 600089' },
    { name: 'Indiranagar Startup Hub', rent: 58000, address: '100 Feet Road, Indiranagar, Bengaluru, Karnataka 560038' },
    { name: 'Prestige Tech Vista Office', rent: 120000, address: 'Kadubeesanahalli, Outer Ring Road, Bengaluru, Karnataka 560103' }
  ];
  return (
    <div className="app-container">
      <h1>Office Space Rental Dashboard</h1>
      <div className="featured-section">
        <h2>Featured Office Space</h2>
        <div className="office-card featured">
          <img src="/office.jpg" alt="Featured Office Space" className="office-image" />
          <div className="details">
            <h3>{singleOffice.name}</h3>
            <p><strong>Address:</strong> {singleOffice.address}</p>
            <p>
              <strong>Monthly Rent:</strong>{' '}
              <span style={{ color: singleOffice.rent < 60000 ? 'red' : 'green' }}>
                Rs. {singleOffice.rent.toLocaleString()}
              </span>
            </p>
          </div>
        </div>
      </div>
      <div className="list-section">
        <h2>Available Office Spaces</h2>
        <div className="office-grid">
          {officesList.map((office, idx) => (
            <div key={idx} className="office-card">
              <h3>{office.name}</h3>
              <p><strong>Address:</strong> {office.address}</p>
              <p>
                <strong>Rent:</strong>{' '}
                <span style={{ color: office.rent < 60000 ? 'red' : 'green' }}>
                  Rs. {office.rent.toLocaleString()}
                </span>
              </p>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}

export default App;
