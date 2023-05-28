package prog2.finalgroup1.view;

import prog2.finalgroup1.model.ExcelSheetData;
import prog2.finalgroup1.model.UserModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Curriculum extends JPanel {
    private AdditionalCourse additionalCourseView;
    private JButton addNewCourse;
    private JButton additionalCourse;
    private JTable tableOfData;
    private String[][] excelData;
    private final String[] columnTitle = {"YEAR", "SEMESTER", "COURSE CODE", "COMPUTER SCIENCE", "UNITS", "GRADES"};
    private JScrollPane pane;
    private DefaultTableModel model;

    /**
     *
     * @param data
     * @param model
     */
    public Curriculum(ExcelSheetData[] data, UserModel model) {
        GridBagLayout mainGrid = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        setLayout(mainGrid);
        setBackground(Color.cyan);

        excelData = processedData(data);

        setUpTable();

        // attached JTable in JScrollPane
        pane = new JScrollPane(tableOfData);
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        addNewCourse = new JButton("Add Course");
        addNewCourse.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                displayAddCourseComponent(model);
            }
        });

        add(pane);
        add(addNewCourse);
    }

    private void setUpTable() {
        model = new DefaultTableModel(excelData, columnTitle);
        tableOfData = new JTable(model);
        tableOfData.setEnabled(false);
    }

    /**
     *
     * @param model
     */
    private void displayAddCourseComponent(UserModel model) {
        additionalCourseView = new AdditionalCourse(model);
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

    /**
     *
     * @param data
     * @return
     */
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

    /**
     *
     * @return
     */
    public AdditionalCourse getAdditionalCourseView() {
        return additionalCourseView;
    }

    /**
     *
     * @param additionalCourseView
     */
    public void setAdditionalCourseView(AdditionalCourse additionalCourseView) {
        this.additionalCourseView = additionalCourseView;
    }

    public JButton getAddNewCourse() {
        return addNewCourse;
    }

    /**
     *
     * @param addNewCourse
     */
    public void setAddNewCourse(JButton addNewCourse) {
        this.addNewCourse = addNewCourse;
    }

    /**
     *
     * @return
     */
    public JButton getAdditionalCourse() {
        return additionalCourse;
    }

    /**
     *
     * @param additionalCourse
     */
    public void setAdditionalCourse(JButton additionalCourse) {
        this.additionalCourse = additionalCourse;
    }

    /**
     *
     * @return
     */
    public DefaultTableModel getModel() {
        return model;
    }

    /**
     *
     * @param model
     */
    public void setModel(DefaultTableModel model) {
        this.model = model;
    }
}
