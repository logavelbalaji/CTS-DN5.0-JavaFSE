import React from 'react';

const IndianPlayers = () => {
  const allPlayers = ['Virat', 'Rohit', 'Dhoni', 'Rahul', 'Jadeja', 'Bumrah'];
  const [firstOdd, firstEven, secondOdd, secondEven, thirdOdd, thirdEven] = allPlayers;
  const oddTeam = [firstOdd, secondOdd, thirdOdd];
  const evenTeam = [firstEven, secondEven, thirdEven];
  const T20players = ['Virat', 'Rohit', 'Bumrah'];
  const RanjiTrophy = ['Pujara', 'Rahane', 'Shaw'];
  const mergedPlayers = [...T20players, ...RanjiTrophy];
  return (
    <div className="section-container">
      <h2>Indian Players</h2>
      <div className="cards-wrapper">
        <div className="list-card">
          <h3>Odd Team Players (Destructured)</h3>
          <ul>
            {oddTeam.map((name, idx) => (
              <li key={idx}>
                <span>{name}</span>
              </li>
            ))}
          </ul>
        </div>
        <div className="list-card">
          <h3>Even Team Players (Destructured)</h3>
          <ul>
            {evenTeam.map((name, idx) => (
              <li key={idx}>
                <span>{name}</span>
              </li>
            ))}
          </ul>
        </div>
        <div className="list-card">
          <h3>Merged Players (T20 &amp; Ranji)</h3>
          <ul>
            {mergedPlayers.map((name, idx) => (
              <li key={idx}>
                <span>{name}</span>
              </li>
            ))}
          </ul>
        </div>
      </div>
    </div>
  );
};

export default IndianPlayers;
