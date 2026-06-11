import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class StudentGradeTrackerGUI {

    private static final ArrayList<String> names = new ArrayList<>();
    private static final ArrayList<Integer> scores = new ArrayList<>();

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> createInterface());
    }

    private static void createInterface() {

        JFrame window = new JFrame("Student Grade Tracker");
        window.setSize(850, 650);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Color background = new Color(245, 246, 250);
        Color primary = new Color(44, 62, 80);
        Color blue = new Color(52, 152, 219);
        Color green = new Color(39, 174, 96);

        JPanel rootPanel = new JPanel(new BorderLayout(15, 15));
        rootPanel.setBackground(background);
        rootPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(primary);

        JLabel heading = new JLabel("Student Grade Tracker");
        heading.setForeground(Color.WHITE);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 28));

        headerPanel.add(heading);

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        JLabel nameLabel = new JLabel("Student Name");
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JTextField nameField = new JTextField();

        JLabel marksLabel = new JLabel("Marks Obtained");
        marksLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JTextField marksField = new JTextField();

        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(marksLabel);
        inputPanel.add(marksField);

        // Buttons
        JButton addButton = new JButton("Add Student");
        addButton.setBackground(blue);
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);

        JButton reportButton = new JButton("Generate Report");
        reportButton.setBackground(green);
        reportButton.setForeground(Color.WHITE);
        reportButton.setFocusPainted(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(background);

        buttonPanel.add(addButton);
        buttonPanel.add(reportButton);

        // Student Records Area
        JTextArea recordsArea = new JTextArea();
        recordsArea.setEditable(false);
        recordsArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(recordsArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(blue, 2),
                "Student Records"));

        // Summary Area
        JTextArea summaryArea = new JTextArea(8, 40);
        summaryArea.setEditable(false);
        summaryArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JScrollPane summaryScroll = new JScrollPane(summaryArea);
        summaryScroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(green, 2),
                "Summary Report"));

        // Add Student Action
        addButton.addActionListener(e -> {

            String studentName = nameField.getText().trim();

            if (studentName.isEmpty()) {
                JOptionPane.showMessageDialog(window,
                        "Please enter student name.");
                return;
            }

            int marks;

            try {
                marks = Integer.parseInt(marksField.getText().trim());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(window,
                        "Please enter valid marks.");
                return;
            }

            if (marks < 0 || marks > 100) {
                JOptionPane.showMessageDialog(window,
                        "Marks should be between 0 and 100.");
                return;
            }

            String grade;

            if (marks >= 90)
                grade = "A+";
            else if (marks >= 80)
                grade = "A";
            else if (marks >= 70)
                grade = "B";
            else if (marks >= 60)
                grade = "C";
            else if (marks >= 50)
                grade = "D";
            else
                grade = "F";

            names.add(studentName);
            scores.add(marks);

            recordsArea.append(
                    String.format(
                            "Name: %-15s Marks: %-5d Grade: %s%n",
                            studentName,
                            marks,
                            grade));

            nameField.setText("");
            marksField.setText("");
        });

        // Generate Report Action
        reportButton.addActionListener(e -> {

            if (scores.isEmpty()) {
                JOptionPane.showMessageDialog(window,
                        "Please add at least one student.");
                return;
            }

            int total = 0;
            int highest = scores.get(0);
            int lowest = scores.get(0);

            String topper = names.get(0);

            for (int i = 0; i < scores.size(); i++) {

                int mark = scores.get(i);

                total += mark;

                if (mark > highest) {
                    highest = mark;
                    topper = names.get(i);
                }

                if (mark < lowest) {
                    lowest = mark;
                }
            }

            double average = (double) total / scores.size();

            StringBuilder report = new StringBuilder();

            report.append("=========== STUDENT PERFORMANCE REPORT ===========\n\n");

            report.append("Total Students : ")
                    .append(scores.size())
                    .append("\n");

            report.append("Average Score : ")
                    .append(String.format("%.2f", average))
                    .append("\n");

            report.append("Highest Score : ")
                    .append(highest)
                    .append("\n");

            report.append("Lowest Score : ")
                    .append(lowest)
                    .append("\n");

            report.append("Top Performer : ")
                    .append(topper)
                    .append("\n\n");

            report.append("--------------- STUDENT DETAILS -----------------\n");

            for (int i = 0; i < names.size(); i++) {

                String grade;

                int mark = scores.get(i);

                if (mark >= 90)
                    grade = "A+";
                else if (mark >= 80)
                    grade = "A";
                else if (mark >= 70)
                    grade = "B";
                else if (mark >= 60)
                    grade = "C";
                else if (mark >= 50)
                    grade = "D";
                else
                    grade = "F";

                report.append(
                        String.format(
                                "%-15s Marks: %-5d Grade: %s%n",
                                names.get(i),
                                mark,
                                grade));
            }

            summaryArea.setText(report.toString());
        });

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(background);

        centerPanel.add(inputPanel, BorderLayout.NORTH);
        centerPanel.add(buttonPanel, BorderLayout.CENTER);
        centerPanel.add(scrollPane, BorderLayout.SOUTH);

        rootPanel.add(headerPanel, BorderLayout.NORTH);
        rootPanel.add(centerPanel, BorderLayout.CENTER);
        rootPanel.add(summaryScroll, BorderLayout.SOUTH);

        window.add(rootPanel);
        window.setVisible(true);
    }
}