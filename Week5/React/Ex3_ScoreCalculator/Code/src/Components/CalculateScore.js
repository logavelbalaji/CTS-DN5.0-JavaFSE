import React from 'react';
import '../Stylesheets/mystyle.css';

const CalculateScore = ({ Name, School, Total, goal }) => {
  const average = Total && goal ? (Total / goal).toFixed(2) : 0;
  return (
    <div className="score-card">
      <h2>Student Score Details</h2>
      <div className="field-group">
        <div className="field">
          <span className="label">Name:</span>
          <span className="value">{Name}</span>
        </div>
        <div className="field">
          <span className="label">School:</span>
          <span className="value">{School}</span>
        </div>
        <div className="field">
          <span className="label">Total Score:</span>
          <span className="value">{Total}</span>
        </div>
        <div className="field">
          <span className="label">Number of Subjects (Goal):</span>
          <span className="value">{goal}</span>
        </div>
        <div className="field highlight">
          <span className="label">Average Score:</span>
          <span className="value">{average}</span>
        </div>
      </div>
    </div>
  );
};

export default CalculateScore;
