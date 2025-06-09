package alextexno;

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

    fileScanner fileScanner = new fileScanner();

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
        
        // Τυπος σχολείου 
        JPanel SchoolTypePanel = new JPanel();
        SchoolTypePanel.setBorder(BorderFactory.createTitledBorder("Τύπος Σχολείου"));
        comboDirection = new JComboBox<>(new String[]{"Γενικό Λύκειο","Επαγγελματικό Λύκειο"});
        SchoolTypePanel.add(comboDirection);
        mainPanel.add(SchoolTypePanel);


        // Κατεύθυνση
        JPanel directionPanel = new JPanel();
        directionPanel.setBorder(BorderFactory.createTitledBorder("Επιλογή Κατεύθυνσης"));
        comboDirection = new JComboBox<>(new String[]{"Θεωρητική", "Θετική", "Οικονομικά", "Υγείας","Γεωπονία Τρροφίμων και Περιβάλλοντος","Διοίκησης και Οικονομίας","Εφαρμοσμένων Τεχνών","Μηχανολογίας","Ναυτιλιακών Επαγγελμάτων","Πληροφορικής\n"
        		+ "\n" ,"Υγείας και Πρόνοιας - Ευεξίας"
        		});
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
        mathimataAnaKateuthinsi.put("Γεωπονία Τρροφίμων και Περιβάλλοντος", new String[]{"Νέα Ελληνικά", "Μαθηματα", "Σύγχρονες Γεωργικές Επιχειρήσεις", "Αρχές Βιολογικής Γεωργίας"});
        mathimataAnaKateuthinsi.put("Διοίκησης και Οικονομίας", new String[]{"Νέα Ελληνικά", "Μαθηματικά", "Αρχές Οικονομικής Θεωρίας", "Αρχές Οργάνωσης και Διοίκησης "});
        mathimataAnaKateuthinsi.put("Εφαρμοσμένων Τεχνών", new String[]{"Νέα Ελληνικά", "Μαθηματικά", "Ιστορία Σύγχρονης Τέχνης", "Τεχνολογία Υλικών"});
        mathimataAnaKateuthinsi.put("Μηχανολογίας", new String[]{"Νέα Ελληνικά", "Μαθηματικά", "Στοιχεία Μηχανών", "Στοιχεία Ψύξης - Κλιματισμού"});
        mathimataAnaKateuthinsi.put("Ναυτιλιακών Επαγγελμάτων", new String[]{"Νέα Ελληνικά", "Μαθηματικά", "Ναυτικές Μηχανές", "Ναυτικό Δίκαιο - Διεθνείς Κανονισμοί στη Ναυτιλία - Εφαρμογές"});
        mathimataAnaKateuthinsi.put("Πληροφορικής", new String[]{"Νέα Ελληνικά", "Μαθηματικά", "Προγραμματισμός Υπολογιστών", "Δίκτυα Υπολογιστών"});
        mathimataAnaKateuthinsi.put("Υγείας και Πρόνοιας - Ευεξίας", new String[]{"Νέα Ελληνικά", "Μαθηματικά", "Ανατομία - Φυσιολογία ", "Υγιεινή"});
        
        syntelestesAnaKateuthinsi.put("Θεωρητική", new double[]{0.25, 0.30, 0.30, 0.15});
        syntelestesAnaKateuthinsi.put("Θετική", new double[]{0.25, 0.25, 0.25, 0.25});
        syntelestesAnaKateuthinsi.put("Οικονομικά", new double[]{0.25, 0.30, 0.25, 0.20});
        syntelestesAnaKateuthinsi.put("Υγείας", new double[]{0.25, 0.30, 0.25, 0.20});
        syntelestesAnaKateuthinsi.put("Γεωπονία Τρροφίμων και Περιβάλλοντος",new double[]{0.25, 0.30, 0.30, 0.15});
        syntelestesAnaKateuthinsi.put("Διοίκησης και Οικονομίας",new double[]{0.25, 0.30, 0.30, 0.15});
        syntelestesAnaKateuthinsi.put("Εφαρμοσμένων Τεχνών",new double[]{0.25, 0.30, 0.30, 0.15});
        syntelestesAnaKateuthinsi.put("Μηχανολογίας", new double[]{0.25, 0.30, 0.30, 0.15});
        syntelestesAnaKateuthinsi.put("Ναυτιλιακών Επαγγελμάτων",new double[]{0.25, 0.30, 0.30, 0.15});
        syntelestesAnaKateuthinsi.put("Πληροφορικής", new double[]{0.25, 0.30, 0.30, 0.15});
        syntelestesAnaKateuthinsi.put("Υγείας και Πρόνοιας - Ευεξίας",new double[]{0.25, 0.30, 0.30, 0.15});
        

        comboDirection.addActionListener(e -> enhmerwshMathimaton());
        enhmerwshMathimaton();

        calcButton.addActionListener(e -> upologismos());
    }

    private void enhmerwshMathimaton() {
        String kateuthinsi = (String) comboDirection.getSelectedItem();
        String[] mathimata = mathimataAnaKateuthinsi.get(kateuthinsi);
        for (int i = 0; i < 4; i++) {
            labelMathimata[i].setText(mathimata[i] + ":");
            textMathimata[i].setText("");
        }
    }
    
    
 
    private void upologismos() {
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
                        sunoloMorion += v * syntelestes[i] * 1000;
                        sunoloVathmon += v * 0.25 * 1000;
                        count++;
                    }
                }
            }

            double mo = sunoloVathmon / count;
            int telikaMoria = (int) sunoloMorion;

            StringBuilder sb = new StringBuilder();
            sb.append("Μόρια: ").append(telikaMoria).append("\n");
            sb.append("Μέσος Όρος: ").append(String.format("%.2f", mo)).append("\n\n");

            List<School> sxoles = fortoseSxoles(kateuthinsi);
            if (sxoles.isEmpty()) {
                sb.append("Δεν βρέθηκαν σχολές για αυτά τα μόρια.");
            } else {
                sb.append("Πιθανές Σχολές:\n");
                for (School s : sxoles) {
                    if (telikaMoria > s.getScore()) {
                        sb.append("• ").append(s.getName() + ", " + s.getCity() + " " + s.getScore().toString()).append("\n");
                    }
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

    private List<School> fortoseSxoles(String kateuthinsi) {
        ArrayList<School> schools;
        String filename = switch (kateuthinsi) {
            case "Θεωρητική" -> "./Sxoles/theoritiki_cleaned.csv";
            case "Θετική" -> "./Sxoles/thetiki_cleaned.csv";
            case "Οικονομικά" -> "./Sxoles/oikonomika_cleaned.csv";
            case "Υγείας" -> "./Sxoles/igeias_cleaned.csv";
            default -> null;
        };

        schools = fileScanner.getSchools(filename);

        return schools;
    }


   
}

    
