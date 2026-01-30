package it.unibo.bank.impl;

import it.unibo.bank.api.AccountHolder;
import it.unibo.bank.api.BankAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Test class for the {@link StrictBankAccount} class.
 */
class TestStrictBankAccount {

    // Create a new AccountHolder and a StrictBankAccount for it each time tests are executed.
    private AccountHolder mRossi;
    private BankAccount bankAccount;

    /**
     * Prepare the tests.
     */
    @BeforeEach
    public void setUp() {
        this.mRossi = new AccountHolder("Mario", "Rossi", 1);
        this.bankAccount = new StrictBankAccount(mRossi, 0);
    }

    /**
     * Test the initial state of the StrictBankAccount.
     */
    @Test
    public void testInitialization() {
        assertEquals(0.0, bankAccount.getBalance());
        assertEquals(0, bankAccount.getTransactionsCount());
        assertEquals(mRossi, bankAccount.getAccountHolder());
    }

    /**
     * Perform a deposit of 100€, compute the management fees, and check that the balance is correctly reduced.
     */
    @Test
    public void testManagementFees() {
        bankAccount.deposit(1, 100);

        int transactionsBefore = bankAccount.getTransactionsCount();
        double expectedFee = StrictBankAccount.MANAGEMENT_FEE + transactionsBefore * StrictBankAccount.TRANSACTION_FEE;


        double expectedBalance;
        if (((StrictBankAccount) bankAccount).isWithdrawAllowed(expectedFee)) {
            expectedBalance = 100 - expectedFee;
        } else {
            expectedBalance = 100;
        }

        bankAccount.chargeManagementFees(1);

        assertEquals(expectedBalance, bankAccount.getBalance());
    }


    /**
     * Test that withdrawing a negative amount causes a failure.
     */
    @Test
    public void testNegativeWithdraw() {
        try {
            bankAccount.withdraw(1, -50);
            fail("Negative withdraw should throw exception");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } 
    }

    /**
     * Test that withdrawing more money than it is in the account is not allowed.
     */
    @Test
    public void testWithdrawingTooMuch() {
    try {
        bankAccount.withdraw(1, 150);
        fail("Withdrawing too much should throw exception");
        } catch (IllegalArgumentException e) {
        // expected
        }
    }
}
