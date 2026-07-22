import React, { useState } from 'react';
import CalculateScore from './Components/CalculateScore';
import './App.css';

function App() {
  const [name, setName] = useState('Steve');
  const [school, setSchool] = useState('SBOA School');
  const [total, setTotal] = useState(450);
  const [goal, setGoal] = useState(5);
  return (
    <div className="app-container">
      <h1>Student Score Calculator</h1>
      <div className="input-panel">
        <div className="input-field">
          <label>Student Name</label>
          <input type="text" value={name} onChange={(e) => setName(e.target.value)} />
        </div>
        <div className="input-field">
          <label>School Name</label>
          <input type="text" value={school} onChange={(e) => setSchool(e.target.value)} />
        </div>
        <div className="input-field">
          <label>Total Score</label>
          <input type="number" value={total} onChange={(e) => setTotal(Number(e.target.value))} />
        </div>
        <div className="input-field">
          <label>Number of Subjects (Goal)</label>
          <input type="number" value={goal} onChange={(e) => setGoal(Number(e.target.value))} />
        </div>
      </div>
      <CalculateScore Name={name} School={school} Total={total} goal={goal} />
    </div>
  );
}

export default App;
