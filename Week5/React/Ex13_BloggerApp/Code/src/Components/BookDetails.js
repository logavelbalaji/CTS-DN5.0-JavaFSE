import React from 'react';

const BookDetails = () => {
  const books = [
    { id: 1, title: 'Mastering React', author: 'Dan Abramov', price: 850 },
    { id: 2, title: 'Clean Code', author: 'Robert C. Martin', price: 950 },
    { id: 3, title: 'You Dont Know JS', author: 'Kyle Simpson', price: 650 }
  ];
  return (
    <div className="category-section">
      <h2>Book Collection</h2>
      <div className="cards-grid">
        {books.map(book => (
          <div key={book.id} className="detail-card">
            <h3>{book.title}</h3>
            <p><strong>Author:</strong> {book.author}</p>
            <p><strong>Price:</strong> Rs. {book.price}</p>
          </div>
        ))}
      </div>
    </div>
  );
};

export default BookDetails;
