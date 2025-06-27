package test.java;

import javax.swing.*;
import java.awt.*;

public class TestUtils {
    public static Component getChildNamed(Component parent, String name) {
        if (name.equals(parent.getName())) {
            return parent;
        }

        if (parent instanceof Container) {
            for (Component child : ((Container) parent).getComponents()) {
                Component found = getChildNamed(child, name);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }

    public static JButton findButtonByText(Container container, String text) {
        for (Component comp : container.getComponents()) {
            if (comp instanceof JButton && ((JButton) comp).getText().equals(text)) {
                return (JButton) comp;
            } else if (comp instanceof Container) {
                JButton button = findButtonByText((Container) comp, text);
                if (button != null) {
                    return button;
                }
            }
        }
        return null;
    }

    public static JTextField[] findTextFields(Container container) {
        java.util.List<JTextField> fields = new java.util.ArrayList<>();
        for (Component comp : container.getComponents()) {
            if (comp instanceof JTextField) {
                fields.add((JTextField) comp);
            } else if (comp instanceof Container) {
                JTextField[] childFields = findTextFields((Container) comp);
                fields.addAll(java.util.Arrays.asList(childFields));
            }
        }
        return fields.toArray(new JTextField[0]);
    }

    public static JTextArea findTextArea(Container container) {
        for (Component comp : container.getComponents()) {
            if (comp instanceof JTextArea) {
                return (JTextArea) comp;
            } else if (comp instanceof Container) {
                JTextArea area = findTextArea((Container) comp);
                if (area != null) {
                    return area;
                }
            }
        }
        return null;
    }
}