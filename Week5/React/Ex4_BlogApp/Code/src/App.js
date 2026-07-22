import React, { Component } from 'react';
import Posts from './Posts';
import './App.css';

class App extends Component {
  render() {
    return (
      <div className="app-container">
        <h1>Blogger Articles</h1>
        <Posts />
      </div>
    );
  }
}

export default App;
