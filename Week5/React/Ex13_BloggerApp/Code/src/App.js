import React, { useState } from 'react';
import BookDetails from './Components/BookDetails';
import BlogDetails from './Components/BlogDetails';
import CourseDetails from './Components/CourseDetails';
import './App.css';

function App() {
  const [category, setCategory] = useState('book');
  const [showMetadata, setShowMetadata] = useState(false);
  const [premiumTheme, setPremiumTheme] = useState(true);
  let componentToRender;
  if (category === 'book') {
    componentToRender = <BookDetails />;
  } else if (category === 'blog') {
    componentToRender = <BlogDetails />;
  } else if (category === 'course') {
    componentToRender = <CourseDetails />;
  }
  return (
    <div className={`app-container ${premiumTheme ? 'premium-mode' : 'standard-mode'}`}>
      <header className="app-header">
        <h1>Blogger App Portal</h1>
        <div className="settings-panel">
          <label className="toggle-switch">
            <input
              type="checkbox"
              checked={premiumTheme}
              onChange={(e) => setPremiumTheme(e.target.checked)}
            />
            <span>Premium Dark Theme</span>
          </label>
        </div>
      </header>
      <div className="navigation-bar">
        <button
          onClick={() => setCategory('book')}
          className={`nav-btn ${category === 'book' ? 'active' : ''}`}
        >
          Book Details
        </button>
        <button
          onClick={() => setCategory('blog')}
          className={`nav-btn ${category === 'blog' ? 'active' : ''}`}
        >
          Blog Details
        </button>
        <button
          onClick={() => setCategory('course')}
          className={`nav-btn ${category === 'course' ? 'active' : ''}`}
        >
          Course Details
        </button>
      </div>
      <div className="display-banner">
        {premiumTheme ? (
          <div className="banner premium">
            <h2>Premium Dashboard View Enabled</h2>
          </div>
        ) : (
          <div className="banner standard">
            <h2>Standard Listing View Enabled</h2>
          </div>
        )}
      </div>
      <main className="content-area">
        {componentToRender}
      </main>
      <div className="footer-options">
        <label className="checkbox-container">
          <input
            type="checkbox"
            checked={showMetadata}
            onChange={(e) => setShowMetadata(e.target.checked)}
          />
          <span>Show Application Stats & Metadata (Logical AND)</span>
        </label>
        {showMetadata && (
          <div className="metadata-panel">
            <p><strong>Total Items Mapped:</strong> 9 Items</p>
            <p><strong>Current Active Tab:</strong> {category.toUpperCase()}</p>
          </div>
        )}
      </div>
    </div>
  );
}

export default App;
