package prog2.finalgroup1.view;

import prog2.finalgroup1.model.ExcelSheetData;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.EventObject;

public class SubjectWithGradesView extends JPanel {
    /*
    TO DO:
    - create a panel for adding course of the user with grades
            - components of a panel: -> display bscs courses offered involving a checkbox
                - new button if user course is not displayed -> [text field -> specify the course name, year,
                semester, unit, and grade -> two buttons (i.e., back and add)]
                - two buttons (i.e., back and add)
                - add button only consider or take the checked box
                - uncheck box = jtable not editable
                - check box = jtable editable
     */
    private AdditionalCourseView additionalCourseView;
    private JButton backMainMenu;
    private JButton addCourse;
    private JTable tableOfData;
    private String[][] excelData;
    private final String[] columnTitle = {"YEAR", "SEMESTER","COURSE CODE", "COMPUTER SCIENCE", "UNITS", "GRADES"};
    private JScrollPane pane;

    public SubjectWithGradesView(ExcelSheetData[] data)
    {
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

        addCourse = new JButton("Add Course");
        addCourse.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                displayAddCourseComponent();
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
        add(addCourse);
    }

    private void setUpTable() {
        tableOfData = new JTable(excelData, columnTitle);
        tableOfData.setEnabled(false);
    }

    private void displayAddCourseComponent() {
        additionalCourseView = new AdditionalCourseView();
    }


    public String[][] processedData (ExcelSheetData[] data) {
        String allData[][] = new String[data.length][6];

        String arr[] = new String[6];

        int i=0;
        while (i < data.length)
        {
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

    public JButton getBackMainMenu() {
        return backMainMenu;
    }

    public void setBackMainMenu(JButton backMainMenu) {
        this.backMainMenu = backMainMenu;
    }
}
