CREATE OR REPLACE FUNCTION CalculateMonthlyInstallment(
    p_loan_amount NUMBER,
    p_annual_interest_rate NUMBER,
    p_duration_years NUMBER
) RETURN NUMBER IS
    v_monthly_rate NUMBER;
    v_num_months NUMBER;
    v_emi NUMBER;
BEGIN
    v_monthly_rate := p_annual_interest_rate / 12 / 100;
    v_num_months := p_duration_years * 12;
    IF v_monthly_rate = 0 THEN
        v_emi := p_loan_amount / v_num_months;
    ELSE
        v_emi := (p_loan_amount * v_monthly_rate * POWER(1 + v_monthly_rate, v_num_months)) / (POWER(1 + v_monthly_rate, v_num_months) - 1);
    END IF;
    RETURN ROUND(v_emi, 2);
END;
/
