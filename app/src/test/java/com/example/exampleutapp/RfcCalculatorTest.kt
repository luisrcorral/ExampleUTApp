package com.example.exampleutapp

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * RfcCalculatorTest - A comprehensive suite of unit tests for RfcCalculator.
 * 
 * This class is designed to teach beginners the principles of robust testing:
 * 1. Structural Coverage: Ensuring we hit every 'if' and 'else' branch.
 * 2. Boundary Value Analysis: Testing the exact edge of a condition.
 * 3. Void/Empty Value Coverage: Checking how the system handles missing data.
 * 4. Range Coverage: Testing different valid inputs across the expected spectrum.
 */
class RfcCalculatorTest {

    // --- STRUCTURAL & VOID VALUE COVERAGE ---
    // These tests ensure our logic handles "blank" or invalid inputs correctly.

    @Test
    fun `calculate returns INVALID when name is blank`() {
        // Condition Coverage: name.isBlank() is TRUE
        val result = RfcCalculator.calculate("", "Corral", "Lopez", "1990-05-15")
        assertEquals("INVALID", result)
    }

    @Test
    fun `calculate returns INVALID when paternal last name is too short`() {
        // Boundary Analysis & Condition Coverage: paternalLastName.length < 2
        // We test length 1, which is the boundary between valid (2) and invalid.
        val result = RfcCalculator.calculate("Luis", "C", "Lopez", "1990-05-15")
        assertEquals("INVALID", result)
    }

    @Test
    fun `calculate uses X when maternal last name is blank`() {
        // Decision Coverage: maternalLastName.isNotBlank() is FALSE
        // This exercises the 'else "X"' branch in our logic.
        val result = RfcCalculator.calculate("Luis", "Corral", "Velazquez", "1981-01-04")
        // Expected: CO (Paternal) + V (Maternal placeholder) + L (Name) + 810104 (Date)
        assertEquals("COVL810104", result)
    }

    @Test
    fun `calculate returns 000000 when date format is wrong`() {
        // Condition Coverage: parts.size != 3
        // If the date doesn't have two dashes, it's invalid.
        val result = RfcCalculator.calculate("Luis", "Corral", "Lopez", "19900515")
        assertEquals("000000", result)
    }

    // --- BOUNDARY VALUE ANALYSIS ---
    // Testing the exact limits where logic might change.

    @Test
    fun `calculate works with paternal last name of exactly 2 characters`() {
        // Boundary Analysis: paternalLastName.length is exactly 2.
        // This is the minimum length allowed before returning "INVALID".
        val result = RfcCalculator.calculate("Luis", "Co", "Lopez", "1990-05-15")
        // Expected: CO (Paternal) + L (Maternal) + L (Name) + 900515 (Date)
        assertEquals("COLL900515", result)
    }

    // --- RANGE COVERAGE ---
    // Testing different valid inputs to ensure the "normal" paths work.

    @Test
    fun `calculate returns correct RFC for standard inputs`() {
        // Standard valid case: Paternal(CO), Maternal(L), Name(L), Date(900515)
        val result = RfcCalculator.calculate("Luis", "Corral", "Velazquez", "1990-05-15")
        assertEquals("COLL900515", result)
    }

    @Test
    fun `calculate handles different centuries in date`() {
        // Range Coverage: Testing a date in the 2000s.
        val result = RfcCalculator.calculate("Ana", "Zavala", "Garcia", "2005-12-31")
        // Expected: ZA (Paternal) + G (Maternal) + A (Name) + 051231 (Date)
        assertEquals("ZAGA051231", result)
    }

    @Test
    fun `calculate handles lowercase inputs by converting to uppercase`() {
        // Stress testing: Ensuring the Locale.ROOT uppercase conversion works.
        val result = RfcCalculator.calculate("luis", "corral", "lopez", "1990-05-15")
        assertEquals("COLL900515", result)
    }
}
