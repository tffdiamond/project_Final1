package prog2.finalgroup1.view;

import prog2.finalgroup1.model.ExcelSheetData;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UserCurriculum extends JPanel {
    private JTable tableOfData;
    private String[][] excelData;
    private final String[] columnTitle = {"YEAR", "SEMESTER" ,"COURSE CODE", "DESCRIPTIVE TITLE", "UNITS"};
    private JScrollPane pane;
    private DefaultTableModel model;

    /*
        TO DO:
            - only display courses the user had been taken
            - implement a sorting functionality -> descending grades or ascending grades
            - implement a logic that deletes courses -> use checkbox
            -
     */
    /**
     *
     * @param data
     */
    public UserCurriculum(ExcelSheetData[] data)
    {
        GridBagLayout mainGrid = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        setLayout(mainGrid);
        setBackground(Color.cyan);

        excelData = processedData(data);

        setUpTable();

        pane = new JScrollPane(tableOfData);
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);


        add(pane);
    }

    private void setUpTable() {
        model = new DefaultTableModel(excelData, columnTitle);
        tableOfData = new JTable(model);
        tableOfData.setEnabled(false);
    }

    /**
     *
     * @param dataToString
     */
    public void insertNewDataInTable(String[] dataToString) {
        String[] newData = {dataToString[0], dataToString[1], dataToString[2], dataToString[3], dataToString[4]};
        model.insertRow(tableOfData.getRowCount(), newData);
    }

    /**
     *
     * @param data
     * @return
     */
    public String[][] processedData (ExcelSheetData[] data) {
        String[][] allData = new String[data.length][5];

        String[] arr = new String[5];

        int i=0;
        while (i < data.length)
        {
            for (ExcelSheetData pureData : data) {
                arr[0] = String.valueOf(pureData.getYear());
                arr[1] = String.valueOf(pureData.getTerm());
                arr[2] = pureData.getCourseNumber();
                arr[3] = pureData.getDescriptiveTitle();
                arr[4] = String.valueOf(pureData.getUnits());

                for (int j = 0; j < 5; j++) {
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

    /**
     *
     * @return
     */
}

