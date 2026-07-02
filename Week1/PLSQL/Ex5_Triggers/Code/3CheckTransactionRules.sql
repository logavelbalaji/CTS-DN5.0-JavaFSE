CREATE OR REPLACE TRIGGER CheckTransactionRules
BEFORE INSERT ON Transactions
FOR EACH ROW
DECLARE
    v_balance NUMBER;
BEGIN
    IF :new.Amount <= 0 THEN
        RAISE_APPLICATION_ERROR(-20005, 'Transaction amount must be positive.');
    END IF;
    IF :new.TransactionType = 'Withdrawal' THEN
        SELECT Balance INTO v_balance FROM Accounts WHERE AccountID = :new.AccountID;
        IF v_balance < :new.Amount THEN
            RAISE_APPLICATION_ERROR(-20006, 'Withdrawal amount exceeds account balance.');
        END IF;
    END IF;
END;
/
