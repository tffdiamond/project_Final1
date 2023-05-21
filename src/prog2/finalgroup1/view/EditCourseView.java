package prog2.finalgroup1.view;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import prog2.finalgroup1.model.ExcelSheetData;
import prog2.finalgroup1.model.UserModel;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;

public class EditCourseView extends JPanel {
    private JButton backMainMenu;
    private JTable tableOfData;
    private String[][] excelData;
    private final String[] columnTitle = {"COURSE CODE", "COMPUTER SCIENCE", "UNITS"};
    private JScrollPane pane;

    public JButton getBackMainMenu() {
        return backMainMenu;
    }

    public void setBackMainMenu(JButton backMainMenu) {
        this.backMainMenu = backMainMenu;
    }

    public EditCourseView(ExcelSheetData[] data, UserModel model)
    {
       /*
        // an algorithm to sort subjects that have been taken
       */
        GridBagLayout mainGrid = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        setPreferredSize(new Dimension(800, 500));
        setLayout(mainGrid);
        setBackground(Color.cyan);

        excelData = processedData(data);

        setUpTable(model);

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

    private void setUpTable(UserModel model) {

        tableOfData = new JTable(excelData, columnTitle) {

            // Set each column to be non-editable except fourth column
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0 || column == 1;
            }
        };

        tableOfData.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

        tableOfData.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {

                try {
                    saveUserInput(model, e);

                } catch (InvalidFormatException | IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

    }

    private void saveUserInput(UserModel userModel, TableModelEvent e) throws IOException, InvalidFormatException {

        OPCPackage pkg2 = OPCPackage.open(new File("res/StudentData.xlsx"));
        XSSFWorkbook CS_studentWorkBook = new XSSFWorkbook(pkg2);
//        TableModel model = tableOfData.getModel();
        XSSFSheet sheet;
        Row rowExcel;
        Cell cell;
        int lastCell;
        int row = e.getFirstRow();
        int col = e.getColumn();
        TableModel model = (TableModel) e.getSource();

        sheet = CS_studentWorkBook.getSheet(userModel.getUsername());

        Object data = model.getValueAt(row, col);

        // write the data change in appropriate sheet of a user
        rowExcel = sheet.getRow(row+1);

        // compare the description title column of Jtable to user course title
        if (col == 1) {
            if (rowExcel != null) {
                lastCell = rowExcel.getLastCellNum()-2;
                cell = rowExcel.getCell(lastCell - 1);

                if (!cell.equals(data)) {
                    cell.setCellValue(String.valueOf(data));
                }
            }
        }

        // compare the course code of Jtable column to user course code
        else if (col == 0)
        {
            if (rowExcel != null) {
                lastCell = rowExcel.getLastCellNum()-3;
                cell = rowExcel.getCell(lastCell - 1);

                if (!cell.equals(data)) {
                    cell.setCellValue(String.valueOf(data));
                }
            }
        }

        // Write the output to a file
        try(OutputStream fileOut = new FileOutputStream("res/StudentData.xlsx", true))
        {
            CS_studentWorkBook.write(fileOut);
        }

        pkg2.close();

    }

    public String[][] processedData (ExcelSheetData[] data) {
        String[][] allData = new String[data.length][3];

        String[] arr = new String[3];

        int i=0;
        while (i < data.length)
        {
            for (ExcelSheetData pureData : data) {
                arr[0] = pureData.getCourseNumber();
                arr[1] = pureData.getDescriptiveTitle();
                arr[2] = String.valueOf(pureData.getUnits());

                for (int j = 0; j < 3; j++) {
                    allData[i][j] = arr[j];
                }
                i++;
            }
        }

        return allData;
    }
}