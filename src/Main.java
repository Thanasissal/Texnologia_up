import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Subject Subject1 = new Subject(0.25);
        Subject Subject2 = new Subject(0.25);
        Subject Subject3 = new Subject(0.25);
        Subject Subject4 = new Subject(0.25);
        Subject specialSubject5 = new Subject(0.25);
        Subject specialSubject6 = new Subject(0.25);
        Subject PracticalSubject7 = new Subject(0.25);

        List<Subject> Subjects = new ArrayList<>();
        Subjects.add(Subject1);
        Subjects.add(Subject2);
        Subjects.add(Subject3);
        Subjects.add(Subject4);
        Subjects.add(specialSubject5);
        Subjects.add(specialSubject6);
        Subjects.add(PracticalSubject7);

        JFrame frame = new JFrame("Input Form");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel formPanel = new JPanel(new GridLayout(8, 4, 20, 5));


        List<JSpinner> GradeFields = new ArrayList<>();
        List<JSpinner> WeightFields = new ArrayList<>();
        List<JLabel> ResultLabels = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            JLabel resultLabel = new JLabel("0.00");
            resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
            ResultLabels.add(resultLabel);
        }



        JSpinner leftField = null;
        for (int i = 1; i <= 7; i++) {
            final int index = i - 1;

            if (i <= 4) {
                formPanel.add(new JLabel("Μάθημα " + i + ":"));
            } else if(i <= 6){
                formPanel.add(new JLabel("Ειδικό Μάθημα " + i + ":"));
            } else {
                formPanel.add(new JLabel("Πρακτικές Εξετάσεις " + i + ":"));
            }
            leftField = new JSpinner(new SpinnerNumberModel(0, 0, 20, 1));

            Dimension spinnerSize = new Dimension(30, 20);
            leftField.setPreferredSize(spinnerSize);
            JComponent editor = (JComponent) leftField.getEditor();
            JSpinner.DefaultEditor spinnerEditor = (JSpinner.DefaultEditor) editor;
            spinnerEditor.getTextField().setHorizontalAlignment(JTextField.CENTER);

            leftField.addChangeListener(e -> {
                int value = (Integer) ((JSpinner) e.getSource()).getValue();
                Subjects.get(index).setGrade(value);
                System.out.println(Subjects.get(index).getGrade());
            });

            GradeFields.add(leftField);
            formPanel.add(leftField);


            JSpinner rightField = new JSpinner(new SpinnerNumberModel(0.25, 0, 1, 0.1));
            rightField.setPreferredSize(spinnerSize);
            JComponent rightEditor = (JComponent) rightField.getEditor();
            JSpinner.DefaultEditor rightSpinnerEditor = (JSpinner.DefaultEditor) rightEditor;
            rightSpinnerEditor.getTextField().setHorizontalAlignment(JTextField.CENTER);

            rightField.addChangeListener(e -> {
                double value = (Double) ((JSpinner) e.getSource()).getValue();
                Subjects.get(index).setWeight(value);
                System.out.println(Subjects.get(index).getWeight());
            });

            WeightFields.add(rightField);
            formPanel.add(rightField);

            if (i > 4) {
                leftField.setEnabled(false);
                rightField.setEnabled(false);
            }

            JLabel resultLabel = new JLabel("0");
            resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
            ResultLabels.set(index, resultLabel);
            formPanel.add(resultLabel);
        }

        JLabel EmptyLabel1 = new JLabel("");
        ResultLabels.set(7, EmptyLabel1);
        formPanel.add(EmptyLabel1);
        JLabel EmptyLabel2 = new JLabel("");
        ResultLabels.set(8, EmptyLabel2);
        formPanel.add(EmptyLabel2);
        JLabel EmptyLabel3 = new JLabel("");
        ResultLabels.set(8, EmptyLabel3);
        formPanel.add(EmptyLabel3);
        JLabel resultLabel = new JLabel("0");
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        ResultLabels.set(9, resultLabel);
        formPanel.add(resultLabel);

        JPanel bottomPanel = new JPanel(new BorderLayout());


        JPanel checkboxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        checkboxPanel.setBorder(BorderFactory.createTitledBorder("Enable/Disable Rows"));

        JCheckBox checkbox1 = new JCheckBox("Ειδικό μάθημα");
        JCheckBox checkbox2 = new JCheckBox("Ειδικό μάθημα 2");
        JCheckBox checkbox3 = new JCheckBox("Πρακτικές εξετάσεις");

        checkbox1.addActionListener(e -> {
            boolean enabled = checkbox1.isSelected();
            GradeFields.get(4).setEnabled(enabled);
            WeightFields.get(4).setEnabled(enabled);
        });
        checkboxPanel.add(checkbox1);

        checkbox2.addActionListener(e -> {
            boolean enabled = checkbox2.isSelected();
            GradeFields.get(5).setEnabled(enabled);
            WeightFields.get(5).setEnabled(enabled);
        });
        checkboxPanel.add(checkbox2);

        checkbox3.addActionListener(e -> {
            boolean enabled = checkbox3.isSelected();
            GradeFields.get(6).setEnabled(enabled);
            WeightFields.get(6).setEnabled(enabled);
        });
        checkboxPanel.add(checkbox3);

        JButton calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(e -> {
            for (int i = 0; i < Subjects.size(); i++) {
                Subject subject = Subjects.get(i);
                if (subject.getGrade() != null && subject.getWeight() != null &&
                        (i < 4 || (i == 4 && checkbox1.isSelected()) || (i == 5 && checkbox2.isSelected()) || (i == 6 && checkbox3.isSelected()))) {
                    Double result = subject.CalculateScore(subject.getGrade(), subject.getWeight());
                    ResultLabels.get(i).setText(String.format("%d", result.intValue()));
                    System.out.println(String.format("Subject %d: %d", i, result.intValue()));
                if ( (i < 4 || (i == 4 && checkbox1.isSelected()) || (i == 5 && checkbox2.isSelected()) || (i == 6 && checkbox3.isSelected()))) {
                    Double total = 0.0;
                    for (int j = 0; j < Subjects.size(); j++) {
                        total += Double.parseDouble(ResultLabels.get(j).getText());
                    }
                    ResultLabels.get(9).setText(String.format("%d", total.intValue()));
                    }
                    if (i == 7) {}
                } else {
                    ResultLabels.get(i).setText("0");
                }
            }


        });


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(calculateButton);

        bottomPanel.add(checkboxPanel, BorderLayout.WEST);
        bottomPanel.add(buttonPanel, BorderLayout.EAST);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        frame.add(mainPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


    }
}
