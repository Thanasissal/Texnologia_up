package test.java;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import test.java.TestUtils;

import static org.junit.jupiter.api.Assertions.*;
import javax.swing.*;
import java.util.List;

import main.java.*;

public class GUITest {
    private GUI gui;

    @BeforeEach
    void setUp() {
        gui = new GUI();
    }

    @Test
    @DisplayName("Test School Data Loading")
    void testSchoolDataLoading() {
        // Test loading schools for different departments
        List<School> theoritikaSchools = gui.fortoseSxoles("Θεωρητική");
        List<School> thetikiSchools = gui.fortoseSxoles("Θετική");
        
        assertNotNull(theoritikaSchools, "Theoretical schools list should not be null");
        assertNotNull(thetikiSchools, "Science schools list should not be null");
        assertFalse(theoritikaSchools.isEmpty(), "Theoretical schools list should not be empty");
        assertFalse(thetikiSchools.isEmpty(), "Science schools list should not be empty");
    }

    @Test
    @DisplayName("Test Grade Validation")
    void testGradeValidation() {
        JTextField[] textFields = TestUtils.findTextFields(gui);
        assertNotNull(textFields, "Grade text fields should not be null");
        
        // Set invalid grades
        textFields[0].setText("25"); // Above maximum
        textFields[1].setText("-5"); // Negative
        textFields[2].setText("abc"); // Non-numeric
        textFields[3].setText("18"); // Valid grade
        
        JButton calcButton = TestUtils.findButtonByText(gui, "Υπολογισμός Μορίων");
        calcButton.doClick();
        
        // The error should be caught and handled
        JTextArea resultArea = TestUtils.findTextArea(gui);
        assertTrue(resultArea.getText().isEmpty(), 
            "Result area should be empty due to invalid grades");
    }

    @Test
    @DisplayName("Test School Type Switch")
    void testSchoolTypeSwitch() {
        JComboBox<?> schoolTypeCombo = (JComboBox<?>) TestUtils.getChildNamed(gui, "comboDirection");
        JComboBox<?> directionCombo = gui.selectedDirection;
        
        // Test initial state (Γενικό Λύκειο)
        assertEquals("Γενικό Λύκειο", schoolTypeCombo.getSelectedItem(),
            "Initial school type should be Γενικό Λύκειο");
        assertTrue(directionCombo.getItemCount() == 4,
            "General Lyceum should have 4 directions");
        
        // Switch to ΕΠΑΛ
        schoolTypeCombo.setSelectedItem("Επαγγελματικό Λύκειο");
        assertTrue(directionCombo.getItemCount() > 4,
            "EPAL should have more than 4 directions");
    }

    @Test
    @DisplayName("Test Valid Score Calculation")
    void testValidScoreCalculation() {
        // Set all grades to 18
        JTextField[] textFields = TestUtils.findTextFields(gui);
        for (JTextField field : textFields) {
            field.setText("18");
        }
        
        JButton calcButton = TestUtils.findButtonByText(gui, "Υπολογισμός Μορίων");
        calcButton.doClick();
        
        JTextArea resultArea = TestUtils.findTextArea(gui);
        String result = resultArea.getText();
        
        assertFalse(result.isEmpty(), "Result should not be empty");
        assertTrue(result.contains("Μόρια:"), "Result should contain score");
        assertTrue(result.contains("Μέσος Όρος:"), "Result should contain average");
    }

    @Test
    @DisplayName("Test Special Subjects Handling")
    void testSpecialSubjectsHandling() {
        JComboBox<?> specialSubjectsCombo = gui.comboSpecialYesNo;

        // Enable special subjects
        specialSubjectsCombo.setSelectedItem("Ναι");

        // Find first special subject checkbox and enable it
        JCheckBox[] specialCheckboxes = gui.specialCheckBoxes;
        JTextField[] specialGrades = gui.specialGrades;

        assertNotNull(specialCheckboxes, "Special subject checkboxes should not be null");
        assertNotNull(specialGrades, "Special subject grade fields should not be null");

        // Select first special subject and set grade
        specialCheckboxes[0].setSelected(true);
        specialGrades[0].setText("19");
    }
}