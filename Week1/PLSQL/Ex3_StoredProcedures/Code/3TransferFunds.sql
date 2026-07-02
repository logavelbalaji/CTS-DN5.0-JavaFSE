CREATE OR REPLACE PROCEDURE TransferFunds(
    p_from_account NUMBER,
    p_to_account NUMBER,
    p_amount NUMBER
) IS
    v_from_balance NUMBER;
BEGIN
    IF p_amount <= 0 THEN
        RAISE_APPLICATION_ERROR(-20003, 'Transfer amount must be positive.');
    END IF;
    SELECT Balance INTO v_from_balance FROM Accounts WHERE AccountID = p_from_account FOR UPDATE;
    IF v_from_balance < p_amount THEN
        RAISE_APPLICATION_ERROR(-20004, 'Insufficient funds in the source account.');
    END IF;
    UPDATE Accounts SET Balance = Balance - p_amount, LastModified = SYSDATE WHERE AccountID = p_from_account;
    UPDATE Accounts SET Balance = Balance + p_amount, LastModified = SYSDATE WHERE AccountID = p_to_account;
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Transferred Rs. ' || p_amount || ' from Account ' || p_from_account || ' to Account ' || p_to_account || '.');
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error: Account ID not found.');
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error: Transfer failed. Message: ' || SQLERRM);
END;
/
