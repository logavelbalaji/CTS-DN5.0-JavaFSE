CREATE OR REPLACE PROCEDURE SafeTransferFunds(
    p_from_account NUMBER,
    p_to_account NUMBER,
    p_amount NUMBER
) IS
    v_from_balance NUMBER;
    v_to_balance NUMBER;
    insufficient_funds EXCEPTION;
BEGIN
    IF p_amount <= 0 THEN
        RAISE_APPLICATION_ERROR(-20001, 'Transfer amount must be positive.');
    END IF;
    SELECT Balance INTO v_from_balance FROM Accounts WHERE AccountID = p_from_account FOR UPDATE;
    SELECT Balance INTO v_to_balance FROM Accounts WHERE AccountID = p_to_account FOR UPDATE;
    IF v_from_balance < p_amount THEN
        RAISE insufficient_funds;
    END IF;
    UPDATE Accounts SET Balance = Balance - p_amount, LastModified = SYSDATE WHERE AccountID = p_from_account;
    UPDATE Accounts SET Balance = Balance + p_amount, LastModified = SYSDATE WHERE AccountID = p_to_account;
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Successfully transferred Rs. ' || p_amount || ' from Account ' || p_from_account || ' to Account ' || p_to_account || '.');
EXCEPTION
    WHEN insufficient_funds THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error: Insufficient funds in Account ' || p_from_account || '. Transfer of Rs. ' || p_amount || ' aborted.');
    WHEN NO_DATA_FOUND THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error: One or both Account IDs do not exist. Transfer aborted.');
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error: Transfer failed. Message: ' || SQLERRM);
END;
/
