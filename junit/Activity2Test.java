package Activities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Activity2Test {

    @Test
    void notEnoughFunds() {
        // Create an object for BankAccount class
        BankAccount account = new BankAccount(9);

        // Assertion for exception
        assertThrows(NotEnoughFundsException.class, () -> account.withdraw(10),
                "Balance must be greater than amount of withdrawal");
    }

    @Test
    void enoughFunds() {
        // Create an object for BankAccount class
        BankAccount account = new BankAccount(1000);
    
        // Assertion for no exceptions
        assertDoesNotThrow(() -> account.withdraw(1000));
    }
}

