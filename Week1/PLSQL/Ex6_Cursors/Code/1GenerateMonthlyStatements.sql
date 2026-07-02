DECLARE
    CURSOR GenerateMonthlyStatements IS
        SELECT c.Name, a.AccountID, t.TransactionID, t.TransactionDate, t.Amount, t.TransactionType
        FROM Customers c
        JOIN Accounts a ON c.CustomerID = a.CustomerID
        JOIN Transactions t ON a.AccountID = t.AccountID
        WHERE EXTRACT(MONTH FROM t.TransactionDate) = EXTRACT(MONTH FROM SYSDATE)
          AND EXTRACT(YEAR FROM t.TransactionDate) = EXTRACT(YEAR FROM SYSDATE);
    r_stmt GenerateMonthlyStatements%ROWTYPE;
BEGIN
    OPEN GenerateMonthlyStatements;
    LOOP
        FETCH GenerateMonthlyStatements INTO r_stmt;
        EXIT WHEN GenerateMonthlyStatements%NOTFOUND;
        DBMS_OUTPUT.PUT_LINE('Statement for Customer: ' || r_stmt.Name || 
                             ' | Account: ' || r_stmt.AccountID || 
                             ' | Txn ID: ' || r_stmt.TransactionID || 
                             ' | Date: ' || TO_CHAR(r_stmt.TransactionDate, 'YYYY-MM-DD') || 
                             ' | Type: ' || r_stmt.TransactionType || 
                             ' | Amount: Rs. ' || r_stmt.Amount);
    END LOOP;
    CLOSE GenerateMonthlyStatements;
END;
/
