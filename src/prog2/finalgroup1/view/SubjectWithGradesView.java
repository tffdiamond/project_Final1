package prog2.finalgroup1.view;

import prog2.finalgroup1.model.ExcelSheetData;
import prog2.finalgroup1.model.UserModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SubjectWithGradesView extends JPanel {
    private AdditionalCourseView additionalCourseView;
    private JButton backMainMenu;
    private JButton addNewCourse;
    private JButton additionalCourse;
    private JTable tableOfData;
    private String[][] excelData;
    private final String[] columnTitle = {"YEAR", "SEMESTER", "COURSE CODE", "COMPUTER SCIENCE", "UNITS", "GRADES"};
    private JScrollPane pane;
    private DefaultTableModel model;

    public SubjectWithGradesView(ExcelSheetData[] data, UserModel model) {
        GridBagLayout mainGrid = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        setPreferredSize(new Dimension(800, 500));
        setLayout(mainGrid);
        setBackground(Color.cyan);

        excelData = processedData(data);

        setUpTable();

        // attached JTable in JScrollPane
        pane = new JScrollPane(tableOfData);
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        backMainMenu = new JButton("Back");
        this.backMainMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                setVisible(false);

            }
        });

        addNewCourse = new JButton("Add Course");
        addNewCourse.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                displayAddCourseComponent(model);
            }
        });

        // TO DO: position each component
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 0;
        constraints.weighty = 0;
        constraints.insets = new Insets(20, 20, 0, 0);
        add(this.backMainMenu, constraints);

        add(pane);
        add(addNewCourse);
    }

    private void setUpTable() {
        model = new DefaultTableModel(excelData, columnTitle);
        tableOfData = new JTable(model);
        tableOfData.setEnabled(false);
    }

    private void displayAddCourseComponent(UserModel model) {
        additionalCourseView = new AdditionalCourseView(model);
        additionalCourse = additionalCourseView.getAdditionalCourse();
        additionalCourse.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                insertNewDataInTable();

            }
        });

    }

    public void insertNewDataInTable() {
        model.insertRow(tableOfData.getRowCount(), additionalCourseView.getDataToString());
    }

    public String[][] processedData(ExcelSheetData[] data) {
        String allData[][] = new String[data.length][6];

        String arr[] = new String[6];

        int i = 0;
        while (i < data.length) {
            for (ExcelSheetData pureData : data) {
                arr[0] = String.valueOf(pureData.getYear());
                arr[1] = String.valueOf(pureData.getTerm());
                arr[2] = pureData.getCourseNumber();
                arr[3] = pureData.getDescriptiveTitle();
                arr[4] = String.valueOf(pureData.getUnits());
                arr[5] = pureData.getGrades();

                for (int j = 0; j < 6; j++) {
                    allData[i][j] = arr[j];
                }
                i++;
            }
        }

        return allData;
    }

    public AdditionalCourseView getAdditionalCourseView() {
        return additionalCourseView;
    }

    public void setAdditionalCourseView(AdditionalCourseView additionalCourseView) {
        this.additionalCourseView = additionalCourseView;
    }

    public JButton getBackMainMenu() {
        return backMainMenu;
    }

    public void setBackMainMenu(JButton backMainMenu) {
        this.backMainMenu = backMainMenu;
    }
    public JButton getAddNewCourse() {
        return addNewCourse;
    }

    public void setAddNewCourse(JButton addNewCourse) {
        this.addNewCourse = addNewCourse;
    }

    public JButton getAdditionalCourse() {
        return additionalCourse;
    }

    public void setAdditionalCourse(JButton additionalCourse) {
        this.additionalCourse = additionalCourse;
    }

    public DefaultTableModel getModel() {
        return model;
    }

    public void setModel(DefaultTableModel model) {
        this.model = model;
    }
}
