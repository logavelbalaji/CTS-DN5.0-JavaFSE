import React from 'react';

const ListofPlayers = () => {
  const players = [
    { name: 'Virat', score: 105 },
    { name: 'Rohit', score: 85 },
    { name: 'Pant', score: 75 },
    { name: 'Gill', score: 90 },
    { name: 'Hardik', score: 80 },
    { name: 'Jadeja', score: 60 },
    { name: 'Iyer', score: 50 },
    { name: 'Dhoni', score: 45 },
    { name: 'Rahul', score: 40 },
    { name: 'Ashwin', score: 35 },
    { name: 'Bumrah', score: 25 }
  ];
  const playersAbove70 = players.filter(player => player.score >= 70);
  const playersBelow70 = players.filter(player => player.score < 70);
  return (
    <div className="section-container">
      <h2>List of Players</h2>
      <div className="cards-wrapper">
        <div className="list-card">
          <h3>All Players (Mapped)</h3>
          <ul>
            {players.map((player, idx) => (
              <li key={idx}>
                <span>{player.name}</span>
                <span className="score">{player.score}</span>
              </li>
            ))}
          </ul>
        </div>
        <div className="list-card">
          <h3>Filtered Players (Score &gt;= 70)</h3>
          <ul>
            {playersAbove70.map((player, idx) => (
              <li key={idx}>
                <span>{player.name}</span>
                <span className="score high">{player.score}</span>
              </li>
            ))}
          </ul>
        </div>
        <div className="list-card">
          <h3>Filtered Players (Score &lt; 70)</h3>
          <ul>
            {playersBelow70.map((player, idx) => (
              <li key={idx}>
                <span>{player.name}</span>
                <span className="score low">{player.score}</span>
              </li>
            ))}
          </ul>
        </div>
      </div>
    </div>
  );
};

export default ListofPlayers;
