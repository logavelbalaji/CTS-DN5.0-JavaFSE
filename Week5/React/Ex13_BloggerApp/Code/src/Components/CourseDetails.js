import React from 'react';

const CourseDetails = () => {
  const courses = [
    { id: 1, title: 'Full-Stack Web Development', duration: '6 Months', instructor: 'John Doe' },
    { id: 2, title: 'Introduction to Cloud Computing', duration: '3 Months', instructor: 'Jane Smith' },
    { id: 3, title: 'UI/UX Design Masterclass', duration: '4 Months', instructor: 'Alice Johnson' }
  ];
  return (
    <div className="category-section">
      <h2>Course Syllabus</h2>
      <div className="cards-grid">
        {courses.map(course => (
          <div key={course.id} className="detail-card">
            <h3>{course.title}</h3>
            <p><strong>Instructor:</strong> {course.instructor}</p>
            <p><strong>Duration:</strong> {course.duration}</p>
          </div>
        ))}
      </div>
    </div>
  );
};

export default CourseDetails;
