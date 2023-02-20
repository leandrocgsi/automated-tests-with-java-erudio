package br.com.erudio.math;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Test Math Operations in SimpleMath Class")
class SimpleMathTest {

    // test[System Under Test]_[Condition or State Change]_[Expected Result]
    @Test
    @DisplayName("Test 6.2 + 2 = 8.2")
    void testSum_When_SixDotTwoIsAddedByTwo_ShouldReturnEightDotTwo() {

        // AAA Arrange, Act Assert
        // Given / Arrange
        SimpleMath math = new SimpleMath();
        
        double firstNumber = 6.2D;
        double secondNumber = 2D;
        double expected = 8.2D;
        
        // When / Act
        Double actual = math.sum(firstNumber, secondNumber);
        
        // Then / Assert
        assertEquals(expected, actual,
            () -> firstNumber + "+" + secondNumber +
            " did not produce " + expected + "!");
    }
    
    @Test
    @DisplayName("Test 6.2 - 2 = 4.2")
    void testSubtraction() {
        SimpleMath math = new SimpleMath();
        double firstNumber = 6.2D;
        double secondNumber = 2D;
        
        Double actual = math.subtraction(firstNumber, secondNumber);
        double expected = 4.2D;
        
        assertEquals(expected, actual,
                () -> firstNumber + "-" + secondNumber +
                " did not produce " + expected + "!");
    }
    
    @Test
    @DisplayName("Test 6.2 * 2 = 12.4")
    void testMultiplication() {
        SimpleMath math = new SimpleMath();
        double firstNumber = 6.2D;
        double secondNumber = 2D;
        
        Double actual = math.multiplication(firstNumber, secondNumber);
        double expected = 12.4D;
        
        assertEquals(expected, actual,
                () -> firstNumber + "*" + secondNumber +
                " did not produce " + expected + "!");
    }
    
    @Test
    @DisplayName("Test 6.2 / 2 = 3.1")
    void testDivision() {
        SimpleMath math = new SimpleMath();
        double firstNumber = 6.2D;
        double secondNumber = 2D;
        
        Double actual = math.division(firstNumber, secondNumber);
        double expected = 3.1D;
        
        assertEquals(expected, actual,
                () -> firstNumber + "/" + secondNumber +
                " did not produce " + expected + "!");
    }

    // test[System Under Test]_[Condition or State Change]_[Expected Result]
    @Disabled("TODO: We need still work on it!")
    @Test
    @DisplayName("Test Division by Zero")
    void testDivision_When_FirstNumberIsDividedByZero_ShouldThrowArithmeticException() {
        fail();
    }
    
    @Test
    @DisplayName("Test (6.2 + 2) / 2 = 4.1")
    void testMean() {
        SimpleMath math = new SimpleMath();
        double firstNumber = 6.2D;
        double secondNumber = 2D;
        
        Double actual = math.mean(firstNumber, secondNumber);
        double expected = 4.1D;
        
        assertEquals(expected, actual,
            () -> "(" + firstNumber + "+" + secondNumber + ")/2" +
            " did not produce " + expected + "!");
    }

    @Test
    @DisplayName("Test Square Root of 81 = 9")
    void testSquareRoot() {
        SimpleMath math = new SimpleMath();
        double number = 81D;
        double expected = 9D;
        
        Double actual = math.squareRoot(number);
        
        assertEquals(expected, actual,
            () -> "Square Root of " + number +
            " did not produce " + expected + "!");
    }

    // test[System Under Test]_[Condition or State Change]_[Expected Result]
    @DisplayName("Display Name")
    @Test
    void testABCD_When_XYZ_Should() {
        // Given / Arrange
        // When / Act
        // Then / Assert
    }
}