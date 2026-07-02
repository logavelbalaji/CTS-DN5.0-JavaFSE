BEGIN
    FOR r_loan IN (
        SELECT l.LoanID, c.Name, l.EndDate, l.LoanAmount
        FROM Loans l
        JOIN Customers c ON l.CustomerID = c.CustomerID
        WHERE l.EndDate BETWEEN SYSDATE AND SYSDATE + 30
    ) LOOP
        DBMS_OUTPUT.PUT_LINE('Reminder: Customer ' || r_loan.Name || ', your loan with ID ' || r_loan.LoanID || ' of Rs. ' || r_loan.LoanAmount || ' is due on ' || TO_CHAR(r_loan.EndDate, 'YYYY-MM-DD') || '.');
    END LOOP;
END;
/
