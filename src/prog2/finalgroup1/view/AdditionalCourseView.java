package prog2.finalgroup1.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class AdditionalCourseView extends JFrame {
    private JComboBox<String> semester;
    private JComboBox<Integer> year;
    AdditionalCourseView()
    {
        setSize(400, 400);
        GridBagLayout mainGrid = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(mainGrid);
        mainPanel.setPreferredSize(new Dimension(400, 400));
        mainPanel.setBackground(Color.cyan);

        JButton additionalCourse = new JButton("Add");
        additionalCourse.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

            }
        });

        JButton back = new JButton("Back");
        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

            }
        });

        JLabel courseCodeLabel = new JLabel("Course: ");
        JTextField courseCode = new JTextField(12);
        JLabel courseTitleLabel = new JLabel("Course Title: ");
        JTextField courseTitle = new JTextField(12);

//        constraints.anchor = GridBagConstraints.FIRST_LINE_END;

        constraints.gridx = 0;
        constraints.gridy = 0;
        mainPanel.add(courseCodeLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        mainPanel.add(courseCode, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        mainPanel.add(courseTitleLabel, constraints);

//        constraints.anchor = GridBagConstraints.FIRST_LINE_END;
        constraints.gridx = 1;
        constraints.gridy = 1;
        mainPanel.add(courseTitle, constraints);

//        constraints.anchor = GridBagConstraints.FIRST_LINE_END;
        constraints.gridx = 0;
        constraints.gridy = 3;
        mainPanel.add(additionalCourse, constraints);

//        constraints.anchor = GridBagConstraints.FIRST_LINE_END;
        constraints.gridx = 1;
        constraints.gridy = 3;
        mainPanel.add(back, constraints);

        add(mainPanel);

        setVisible(true);

    }
}
