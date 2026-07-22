import React from 'react';
import styles from './CohortDetails.module.css';

const CohortDetails = ({ cohort }) => {
  const headingColor = cohort.status === 'ongoing' ? 'green' : 'blue';
  return (
    <div className={styles.box}>
      <h3 style={{ color: headingColor }}>{cohort.name}</h3>
      <dl>
        <dt>Cohort Code</dt>
        <dd>{cohort.code}</dd>
        <dt>Start Date</dt>
        <dd>{cohort.startDate}</dd>
        <dt>End Date</dt>
        <dd>{cohort.endDate}</dd>
        <dt>Status</dt>
        <dd>{cohort.status}</dd>
      </dl>
    </div>
  );
};

export default CohortDetails;
