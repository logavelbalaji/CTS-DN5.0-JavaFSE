import React from 'react';
import CohortDetails from './CohortDetails';
import './App.css';

function App() {
  const cohorts = [
    { name: 'React Developer', code: 'CH_RE_01', startDate: '01-Jul-2026', endDate: '31-Aug-2026', status: 'ongoing' },
    { name: 'Angular Developer', code: 'CH_AN_02', startDate: '01-May-2026', endDate: '30-Jun-2026', status: 'completed' },
    { name: 'Spring Boot Specialist', code: 'CH_SB_03', startDate: '15-Jun-2026', endDate: '15-Aug-2026', status: 'ongoing' },
    { name: 'NodeJS Backend Specialist', code: 'CH_NO_04', startDate: '10-Apr-2026', endDate: '10-Jun-2026', status: 'completed' }
  ];
  return (
    <div className="app-container">
      <h1>Cohort Dashboard</h1>
      <div className="cohorts-list">
        {cohorts.map((cohort, index) => (
          <CohortDetails key={index} cohort={cohort} />
        ))}
      </div>
    </div>
  );
}

export default App;
