package prog2.finalgroup1.view;

import prog2.finalgroup1.model.ExcelSheetData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SubjectView extends JPanel {
    private JButton backMainMenu;
    private JTable tableOfData;
    private String[][] excelData;
    private final String[] columnTitle = {"YEAR", "SEMESTER" ,"COURSE CODE", "COMPUTER SCIENCE", "UNITS"};
    private JScrollPane pane;

    public SubjectView(ExcelSheetData[] data)
    {
        GridBagLayout mainGrid = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        setPreferredSize(new Dimension(800, 500));
        setLayout(mainGrid);
        setBackground(Color.cyan);

        excelData = processedData(data);
        tableOfData = new JTable(excelData, columnTitle);
        tableOfData.setEnabled(false);
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

    public String[][] processedData (ExcelSheetData[] data) {
        String allData[][] = new String[data.length][5];

        String arr[] = new String[5];

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

    public JButton getBackMainMenu() {
        return backMainMenu;
    }

    public void setBackMainMenu(JButton backMainMenu) {
        this.backMainMenu = backMainMenu;
    }
}
