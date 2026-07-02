DECLARE
    v_age NUMBER;
BEGIN
    FOR r_loan IN (
        SELECT l.LoanID, c.DOB, l.InterestRate 
        FROM Loans l 
        JOIN Customers c ON l.CustomerID = c.CustomerID
    ) LOOP
        v_age := MONTHS_BETWEEN(SYSDATE, r_loan.DOB) / 12;
        IF v_age > 60 THEN
            UPDATE Loans
            SET InterestRate = InterestRate - 1
            WHERE LoanID = r_loan.LoanID;
        END IF;
    END LOOP;
    COMMIT;
END;
/
