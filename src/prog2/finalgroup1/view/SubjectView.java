package prog2.finalgroup1.view;

import prog2.finalgroup1.model.ExcelSheetData;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SubjectView extends JPanel {
    private JButton backMainMenu;
    private JTable tableOfData;
    private String[][] excelData;
    private final String[] columnTitle = {"YEAR", "SEMESTER" ,"COURSE CODE", "COMPUTER SCIENCE", "UNITS"};
    private JScrollPane pane;
    private DefaultTableModel model;


    /**
     *
     * @param data
     */
    public SubjectView(ExcelSheetData[] data)
    {
        GridBagLayout mainGrid = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        setPreferredSize(new Dimension(800, 500));
        setLayout(mainGrid);
        setBackground(Color.cyan);

        excelData = processedData(data);

        setUpTable();

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

        // TO DO: position each component
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 0;
        constraints.weighty = 0;
        constraints.insets = new Insets(20, 20, 0, 0);
        add(this.backMainMenu, constraints);

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
    public JButton getBackMainMenu() {
        return backMainMenu;
    }

    /**
     *
     * @param backMainMenu
     */
    public void setBackMainMenu(JButton backMainMenu) {
        this.backMainMenu = backMainMenu;
    }
}

