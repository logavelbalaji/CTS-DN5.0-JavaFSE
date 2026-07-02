CREATE OR REPLACE PROCEDURE UpdateEmployeeBonus(
    p_department VARCHAR2,
    p_bonus_percentage NUMBER
) IS
BEGIN
    IF p_bonus_percentage < 0 THEN
        RAISE_APPLICATION_ERROR(-20002, 'Bonus percentage cannot be negative.');
    END IF;
    UPDATE Employees
    SET Salary = Salary * (1 + p_bonus_percentage / 100)
    WHERE Department = p_department;
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Successfully applied ' || p_bonus_percentage || '% bonus to employees in department ' || p_department || '.');
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error: Failed to update employee bonus. Message: ' || SQLERRM);
END;
/
