package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import bankapp.BankAccount;
import bankapp.Loan;

public class LoanTest {
       @Test
       void testIncrementTimePeriod(){
            Loan testLoan = new Loan(1000,.05);
            testLoan.incrementTimePeriod();
            assertEquals(testLoan.getAmountUnpaid(),1051.16,.01);
       }
       @Test
       void testNotApproved(){
            Loan testLoan = new Loan(1000,.05);
            boolean isApproved=testLoan.isApproved();
            assertFalse(isApproved);
       }
       @Test
       void testFailAuthorization(){
            Loan testLoan = new Loan(1000,.05);
            BankAccount testAccount = new BankAccount("testUser","testPass");
            boolean authorized = testLoan.authorizeLoan(0, testAccount);
            assertFalse(authorized);
       }
       @Test
       void testPassAuthorization(){
            Loan testLoan = new Loan(1000,.05);
            BankAccount testAccount = new BankAccount("testUser","testPass",1000,null);
            boolean authorized = testLoan.authorizeLoan(0, testAccount);
            assertTrue(authorized);
       }
       @Test
       void testGrantLoan(){
            Loan testLoan = new Loan(1000,.05);
            BankAccount testAccount = new BankAccount("testUser","testPass",1000,null);
            testLoan.grantLoanWithoutIncome(testAccount);
            assertTrue(testLoan.isApproved());
            assertEquals(testLoan.getLoanHolder(), testAccount);
       }
       @Test
       void testDenyLoan(){
            Loan testLoan = new Loan(1000,.05);
            BankAccount testAccount = new BankAccount("testUser","testPass");
            testLoan.grantLoanWithoutIncome(testAccount);
            assertFalse(testLoan.isApproved());
            assertEquals(testLoan.getLoanHolder(), null);
       }
       @Test
       void testNegativeLoanPayment(){
            Loan testLoan = new Loan(1000,.05);
            try{
                testLoan.tryLoanPayment(-10);
                fail();
            }catch(IllegalArgumentException e){
                assertTrue(true);
            }
       }
       @Test
       void testUnapprovedLoanPayment(){
            Loan testLoan = new Loan(1000,.05);
            boolean madePayment=testLoan.tryLoanPayment(20);
            assertFalse(madePayment);
       }
       @Test
       void testInsufficientFundsLoanPayment(){
            Loan testLoan = new Loan(1000,.05);
            BankAccount testAccount = new BankAccount("testUser","testPass",998,null);
            testLoan.grantLoanWithoutIncome(testAccount);
            assertEquals(testLoan.getLoanHolder(), testAccount);
            boolean madePayment=testLoan.tryLoanPayment(999);
            assertFalse(madePayment);
       }
       @Test
       void testGreaterThanOutstandingAmountLoanPayment(){
            Loan testLoan = new Loan(1000,.05);
            BankAccount testAccount = new BankAccount("testUser","testPass",2000,null);
            testLoan.grantLoanWithoutIncome(testAccount);
            assertEquals(testLoan.getLoanHolder(), testAccount);
            try{
                testLoan.tryLoanPayment(1001);
                fail();
            }catch(IllegalArgumentException e){
                assertTrue(true);
            }
       }
       @Test
       void testSuccessfulLoanPayment(){
            Loan testLoan = new Loan(1000,.05);
            BankAccount testAccount = new BankAccount("testUser","testPass",1000,null);
            testLoan.grantLoanWithoutIncome(testAccount);
            assertEquals(testLoan.getLoanHolder(), testAccount);
            boolean madePayment=testLoan.tryLoanPayment(500);
            assertTrue(madePayment);
            assertEquals(testLoan.getAmountUnpaid(), 500,.01);
            assertEquals(testAccount.getBalance(), 500,.01);
       }
}   