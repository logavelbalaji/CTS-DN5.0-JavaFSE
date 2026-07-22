import React, { useState } from 'react';
import ListofPlayers from './Components/ListofPlayers';
import IndianPlayers from './Components/IndianPlayers';
import './App.css';

function App() {
  const [flag, setFlag] = useState(true);
  let componentToRender;
  if (flag) {
    componentToRender = <ListofPlayers />;
  } else {
    componentToRender = <IndianPlayers />;
  }
  return (
    <div className="app-container">
      <h1>Cricket App</h1>
      <div className="toggle-container">
        <button onClick={() => setFlag(!flag)} className="toggle-btn">
          Toggle Component View (Current Flag: {String(flag)})
        </button>
      </div>
      <div className="render-area">
        {componentToRender}
      </div>
    </div>
  );
}

export default App;
