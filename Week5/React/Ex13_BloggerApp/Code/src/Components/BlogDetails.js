import React from 'react';

const BlogDetails = () => {
  const blogs = [
    { id: 1, title: 'React 18 Features', author: 'Sophie Alpert', readTime: '5 mins' },
    { id: 2, title: 'Understanding ES6 Mappings', author: 'Rachel Nabors', readTime: '8 mins' },
    { id: 3, title: 'State Management in 2026', author: 'Andrew Clark', readTime: '10 mins' }
  ];
  return (
    <div className="category-section">
      <h2>Blog Posts</h2>
      <div className="cards-grid">
        {blogs.map(blog => (
          <div key={blog.id} className="detail-card">
            <h3>{blog.title}</h3>
            <p><strong>Author:</strong> {blog.author}</p>
            <p><strong>Read Time:</strong> {blog.readTime}</p>
          </div>
        ))}
      </div>
    </div>
  );
};

export default BlogDetails;
