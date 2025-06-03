package texnologia;
import java.util.*;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

import java.util.ArrayList;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GUI extends JFrame {
    private JComboBox<String> comboDirection;
    private JTextField[] textMathimata = new JTextField[4];
    private JLabel[] labelMathimata = new JLabel[4];
    private JComboBox<String> comboSpecialYesNo;
    private JCheckBox[] specialCheckBoxes;
    private JTextField[] specialGrades;
    private JTextArea resultArea;

    private HashMap<String, String[]> mathimataAnaKateuthinsi = new HashMap<>();
    private HashMap<String, double[]> syntelestesAnaKateuthinsi = new HashMap<>();
    private String[] eidikaMathimata = {
            "Αγγλικά", "Ισπανικά", "Γερμανικά", "Γαλλικά", "Ιταλικά",
            "Ελεύθερο Σχέδιο", "Γραμμικό Σχέδιο",
            "Μουσική Αντίληψη", "Μουσική Εκτέλεση & Ερμηνεία"
    };

    public GUI() {
        setTitle("Υπολογιστής Μορίων");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 750);
        setLocationRelativeTo(null);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Κατεύθυνση
        JPanel directionPanel = new JPanel();
        directionPanel.setBorder(BorderFactory.createTitledBorder("Επιλογή Κατεύθυνσης"));
        comboDirection = new JComboBox<>(new String[]{"Θεωρητική", "Θετική", "Οικονομικά", "Υγείας"});
        directionPanel.add(comboDirection);
        mainPanel.add(directionPanel);

        // Μαθήματα
        JPanel lessonsPanel = new JPanel();
        lessonsPanel.setLayout(new GridLayout(4, 2, 10, 10));
        lessonsPanel.setBorder(BorderFactory.createTitledBorder("Βασικά Μαθήματα"));
        for (int i = 0; i < 4; i++) {
            labelMathimata[i] = new JLabel("Μάθημα " + (i + 1));
            textMathimata[i] = new JTextField(5);
            lessonsPanel.add(labelMathimata[i]);
            lessonsPanel.add(textMathimata[i]);
        }
        mainPanel.add(lessonsPanel);

        // Ειδικά μαθήματα επιλογή
        JPanel specialPanel = new JPanel();
        specialPanel.setLayout(new BoxLayout(specialPanel, BoxLayout.Y_AXIS));
        specialPanel.setBorder(BorderFactory.createTitledBorder("Ειδικά Μαθήματα"));
        JPanel specialChoicePanel = new JPanel();
        specialChoicePanel.add(new JLabel("Έχεις ειδικό μάθημα;"));
        comboSpecialYesNo = new JComboBox<>(new String[]{"Όχι", "Ναι"});
        specialChoicePanel.add(comboSpecialYesNo);
        specialPanel.add(specialChoicePanel);

        JPanel checkBoxPanel = new JPanel(new GridLayout(5, 2));
        specialCheckBoxes = new JCheckBox[eidikaMathimata.length];
        specialGrades = new JTextField[eidikaMathimata.length];
        for (int i = 0; i < eidikaMathimata.length; i++) {
            JPanel boxPanel = new JPanel();
            specialCheckBoxes[i] = new JCheckBox(eidikaMathimata[i]);
            specialGrades[i] = new JTextField(3);
            specialGrades[i].setEnabled(false);
            boxPanel.add(specialCheckBoxes[i]);
            boxPanel.add(specialGrades[i]);
            checkBoxPanel.add(boxPanel);
            int finalI = i;
            specialCheckBoxes[i].addActionListener(e -> {
                specialGrades[finalI].setEnabled(specialCheckBoxes[finalI].isSelected());
            });
        }
        specialPanel.add(checkBoxPanel);
        mainPanel.add(specialPanel);

        // Κουμπί και αποτέλεσμα
        JButton calcButton = new JButton("Υπολογισμός Μορίων");
        mainPanel.add(calcButton);

        resultArea = new JTextArea(4, 40);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        resultArea.setEditable(false);
        resultArea.setBorder(BorderFactory.createTitledBorder("Αποτέλεσμα"));
        mainPanel.add(resultArea);

        // Προσθήκη στο frame
        setContentPane(new JScrollPane(mainPanel));

        // Δεδομένα κατευθύνσεων
        mathimataAnaKateuthinsi.put("Θεωρητική", new String[]{"Έκθεση", "Ιστορία", "Αρχαία", "Λατινικά"});
        mathimataAnaKateuthinsi.put("Θετική", new String[]{"Έκθεση", "Μαθηματικά", "Φυσική", "Χημεία"});
        mathimataAnaKateuthinsi.put("Οικονομικά", new String[]{"Έκθεση", "Μαθηματικά", "ΑΟΘ", "ΑΕΠΠ"});
        mathimataAnaKateuthinsi.put("Υγείας", new String[]{"Έκθεση", "Βιολογία", "Φυσική", "Χημεία"});

        syntelestesAnaKateuthinsi.put("Θεωρητική", new double[]{0.25, 0.30, 0.30, 0.15});
        syntelestesAnaKateuthinsi.put("Θετική", new double[]{0.25, 0.25, 0.25, 0.25});
        syntelestesAnaKateuthinsi.put("Οικονομικά", new double[]{0.25, 0.30, 0.25, 0.20});
        syntelestesAnaKateuthinsi.put("Υγείας", new double[]{0.25, 0.30, 0.25, 0.20});

        comboDirection.addActionListener(e -> ενημέρωσηΜαθημάτων());
        ενημέρωσηΜαθημάτων();

        calcButton.addActionListener(e -> υπολογισμός());
    }

    private void ενημέρωσηΜαθημάτων() {
        String kateuthinsi = (String) comboDirection.getSelectedItem();
        String[] mathimata = mathimataAnaKateuthinsi.get(kateuthinsi);
        for (int i = 0; i < 4; i++) {
            labelMathimata[i].setText(mathimata[i] + ":");
            textMathimata[i].setText("");
        }
    }
    
    

    private void υπολογισμός() {
        try {
            String kateuthinsi = (String) comboDirection.getSelectedItem();
            double[] syntelestes = syntelestesAnaKateuthinsi.get(kateuthinsi);

            double sunoloMorion = 0;
            double sunoloVathmon = 0;
            int count = 0;

            for (int i = 0; i < 4; i++) {
                String text = textMathimata[i].getText().trim();
                if (text.isEmpty()) throw new NumberFormatException("Άφησες κενό βαθμό στο βασικό μάθημα " + (i + 1));
                double v = Double.parseDouble(text);
                if (v < 0 || v > 20) throw new NumberFormatException("Ο βαθμός πρέπει να είναι μεταξύ 0 και 20.");
                sunoloMorion += v * syntelestes[i] * 1000;
                sunoloVathmon += v;
                count++;
            }

            if (comboSpecialYesNo.getSelectedItem().equals("Ναι")) {
                for (int i = 0; i < eidikaMathimata.length; i++) {
                    if (specialCheckBoxes[i].isSelected()) {
                        String text = specialGrades[i].getText().trim();
                        if (text.isEmpty()) throw new NumberFormatException("Άφησες κενό στο ειδικό μάθημα: " + eidikaMathimata[i]);
                        double v = Double.parseDouble(text);
                        if (v < 0 || v > 20) throw new NumberFormatException("Ο βαθμός πρέπει να είναι μεταξύ 0 και 20.");
                        sunoloVathmon += v;
                        count++;
                    }
                }
            }

            double mo = sunoloVathmon / count;
            int telikaMoria = (int) sunoloMorion;

            StringBuilder sb = new StringBuilder();
            sb.append("Μόρια: ").append(telikaMoria).append("\n");
            sb.append("Μέσος Όρος: ").append(String.format("%.2f", mo)).append("\n\n");

            List<String> sxoles = φορτωσεΣχολες(kateuthinsi, telikaMoria);
            if (sxoles.isEmpty()) {
                sb.append("Δεν βρέθηκαν σχολές για αυτά τα μόρια.");
            } else {
                sb.append("Πιθανές Σχολές:\n");
                for (String s : sxoles) {
                    sb.append("• ").append(s).append("\n");
                }
            }

            resultArea.setText(sb.toString());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Λάθος βαθμός: " + e.getMessage(),
                    "Σφάλμα", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Γενικό σφάλμα: " + e.getMessage(),
                    "Σφάλμα", JOptionPane.ERROR_MESSAGE);
        }
    }

    
    private List<String> φορτωσεΣχολες(String kateuthinsi, int moria) {
        List<String> σχολες = new ArrayList<>();
        String filename = switch (kateuthinsi) {
            case "Θεωρητική" -> "theoritiki.csv";
            case "Θετική" -> "thetiki.csv";
            case "Οικονομικά" -> "oikonomika.csv";
            case "Υγείας" -> "igeias.csv";
            default -> null;
        };

        if (filename == null) return σχολες;

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String onoma = parts[0].trim();
                    String poli = parts[1].trim();
                    int minMoria = Integer.parseInt(parts[2].trim());

                    if (moria >= minMoria) {
                        σχολες.add(onoma + " - " + poli + " (" + minMoria + ")");
                    }
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Σφάλμα κατά την ανάγνωση αρχείου: " + filename,
                    "Σφάλμα", JOptionPane.ERROR_MESSAGE);
        }

        return σχολες;
    }


   
}

    

