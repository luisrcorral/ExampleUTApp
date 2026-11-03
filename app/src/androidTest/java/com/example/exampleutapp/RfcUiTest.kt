package com.example.exampleutapp

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import org.junit.Rule
import org.junit.Test

/**
 * RfcUiTest - A suite of 10 UI tests using Jetpack Compose Testing APIs.
 * 
 * For beginners: Unlike unit tests that test logic, UI tests interact with the screen
 * as a user would. We use "Matchers" to find elements, "Actions" to interact with them,
 * and "Assertions" to verify the result.
 */
class RfcUiTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    /**
     * Test 1: Navigation from Main to Report.
     * Strategy: Verifies that the "View Records Report" button works.
     */
    @Test
    fun testNavigationToReport() {
        composeTestRule.onNodeWithText("View Records Report").performClick()
        // Verify we reached the Report screen by checking for the "Report" header
        composeTestRule.onNodeWithText("Report").assertIsDisplayed()
    }

    /**
     * Test 2: Input Validation - Blank Fields.
     * Strategy: Stress test void value coverage in UI.
     */
    @Test
    fun testEmptyInputValidation() {
        composeTestRule.onNodeWithText("Calculate RFC").performClick()
        composeTestRule.onNodeWithText("Name, Paternal Last Name and DOB are required").assertIsDisplayed()
    }

    /**
     * Test 3: Input Validation - Invalid Date Format.
     * Strategy: Boundary/Pattern testing.
     */
    @Test
    fun testInvalidDateFormat() {
        composeTestRule.onNodeWithText("First Name").performTextInput("Luis")
        composeTestRule.onNodeWithText("Paternal Last Name").performTextInput("Corral")
        composeTestRule.onNodeWithText("Date of Birth (YYYY-MM-DD)").performTextInput("90-05-15") // Wrong format
        composeTestRule.onNodeWithText("Calculate RFC").performClick()
        composeTestRule.onNodeWithText("Invalid date format (YYYY-MM-DD)").assertIsDisplayed()
    }

    /**
     * Test 4: Successful RFC Calculation Flow.
     * Strategy: Happy path (Main -> Second Activity).
     */
    @Test
    fun testSuccessfulCalculation() {
        // Fill all fields
        composeTestRule.onNodeWithText("First Name").performTextInput("Luis")
        composeTestRule.onNodeWithText("Paternal Last Name").performTextInput("Corral")
        composeTestRule.onNodeWithText("Maternal Last Name").performTextInput("Lopez")
        composeTestRule.onNodeWithText("Date of Birth (YYYY-MM-DD)").performTextInput("1990-05-15")
        
        composeTestRule.onNodeWithText("Calculate RFC").performClick()
        
        // Assert we are on the result screen and the RFC is correct
        composeTestRule.onNodeWithText("RFC: COLL900515").assertIsDisplayed()
        composeTestRule.onNodeWithText("Name: Luis Corral Lopez").assertIsDisplayed()
    }

    /**
     * Test 5: Handle Blank Maternal Last Name (Placeholder 'X').
     * Strategy: Logic verification through UI.
     */
    @Test
    fun testMaternalNamePlaceholder() {
        composeTestRule.onNodeWithText("First Name").performTextInput("Luis")
        composeTestRule.onNodeWithText("Paternal Last Name").performTextInput("Corral")
        composeTestRule.onNodeWithText("Date of Birth (YYYY-MM-DD)").performTextInput("1990-05-15")
        
        composeTestRule.onNodeWithText("Calculate RFC").performClick()
        
        // RFC should have an 'X' for the missing maternal name
        composeTestRule.onNodeWithText("RFC: COXL900515").assertIsDisplayed()
    }

    /**
     * Test 6: Navigation Back from Second Activity.
     * Strategy: UI Flow coverage.
     */
    @Test
    fun testBackNavigationFromResult() {
        testSuccessfulCalculation()
        composeTestRule.onNodeWithText("Back to Main").performClick()
        // Check if we are back on the main screen
        composeTestRule.onNodeWithText("RFC Calculator").assertIsDisplayed()
    }

    /**
     * Test 7: Data Persistence in Report Screen.
     * Strategy: End-to-end integration (Main -> Second -> Third).
     */
    @Test
    fun testRecordAppearsInReport() {
        // 1. Calculate a record
        composeTestRule.onNodeWithText("First Name").performTextInput("Juan")
        composeTestRule.onNodeWithText("Paternal Last Name").performTextInput("Perez")
        composeTestRule.onNodeWithText("Date of Birth (YYYY-MM-DD)").performTextInput("1985-01-01")
        composeTestRule.onNodeWithText("Calculate RFC").performClick()
        
        // 2. Go to Report
        composeTestRule.onNodeWithText("Go to Report").performClick()
        
        // 3. Verify record exists in the list
        composeTestRule.onNodeWithText("Juan Perez ").assertIsDisplayed()
        composeTestRule.onNodeWithText("RFC: PEXJ850101").assertIsDisplayed()
    }

    /**
     * Test 8: Record Deletion in Third Activity.
     * Strategy: Feature functionality testing.
     */
    @Test
    fun testDeleteRecord() {
        // Ensure there is at least one record (from Test 7 logic)
        testRecordAppearsInReport()
        
        // Click remove on the record we just found
        composeTestRule.onNodeWithText("Remove").performClick()
        
        // The record should no longer be visible
        composeTestRule.onNodeWithText("RFC: PEXJ850101").assertDoesNotExist()
    }

    /**
     * Test 9: Navigation Back to Main from Report.
     * Strategy: Multi-activity flow.
     */
    @Test
    fun testBackToMainFromReport() {
        composeTestRule.onNodeWithText("View Records Report").performClick()
        composeTestRule.onNodeWithText("Back to Main").performClick()
        composeTestRule.onNodeWithText("RFC Calculator").assertIsDisplayed()
    }

    /**
     * Test 10: Structural UI Integrity - All Fields Present.
     * Strategy: UI layout coverage.
     */
    @Test
    fun testMainScreenLayout() {
        composeTestRule.onNodeWithText("First Name").assertExists()
        composeTestRule.onNodeWithText("Paternal Last Name").assertExists()
        composeTestRule.onNodeWithText("Maternal Last Name").assertExists()
        composeTestRule.onNodeWithText("Date of Birth (YYYY-MM-DD)").assertExists()
        composeTestRule.onNodeWithText("Calculate RFC").assertExists()
    }
}
