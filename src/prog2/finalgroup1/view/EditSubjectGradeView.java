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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class EditSubjectGradeView extends JPanel {
    private JButton backMainMenu;
    private JButton saveData;
    private JTable tableOfData;
    private String[][] excelData;
    private final String[] columnTitle = {"COURSE CODE", "DESCRIPTIVE TITLE", "UNITS", "GRADES"};
    private JScrollPane pane;
    private DefaultTableModel model;
    private UserModel userModel;
    private ExcelSheetData[] sheetData;
    private int row;
    private int column;
    private Object data;

    public EditSubjectGradeView(ExcelSheetData[] sheetData, UserModel userModel) {

        GridBagLayout mainGrid = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        setPreferredSize(new Dimension(800, 500));
        setLayout(mainGrid);
        setBackground(Color.cyan);

        this.userModel = userModel;
        this.sheetData = sheetData;

        excelData = processedData();

        setUpTable();

        saveData = new JButton("Apply");
        saveData.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

            }
        });

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

        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 0;
        constraints.weighty = 0;
        constraints.insets = new Insets(20, 20, 0, 0);
        add(this.backMainMenu, constraints);

        add(saveData);

        add(pane);

    }

    private void setUpTable() {
        model = new DefaultTableModel(excelData, columnTitle);
        tableOfData = new JTable(model) {
            // Set each column to be non-editable except fourth column
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }
        };

        tableOfData.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

        tableOfData.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {

                try {
                    saveUserInput(e);
                } catch (InvalidFormatException | IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

    }

    public void insertNewDataInTable(String[] dataToString) {
        String[] newData = {dataToString[2], dataToString[3], dataToString[4], dataToString[5]};
        model.insertRow(tableOfData.getRowCount(), newData);
    }

    public void saveUserInput(TableModelEvent e) throws InvalidFormatException, IOException {
        OPCPackage pkg2 = OPCPackage.open(new File("res/StudentData.xlsx"));
        XSSFWorkbook CS_studentWorkBook = new XSSFWorkbook(pkg2);
        XSSFSheet sheet;
        Row rowExcel;
        Cell cell;
        int lastCell;
        row = e.getFirstRow();
        column = e.getColumn();
        TableModel model = (TableModel) e.getSource();

        sheet = CS_studentWorkBook.getSheet(userModel.getUsername());

        if (column == -1)
        {
            return;
        }
        else {
            data = model.getValueAt(row, column);

            rowExcel = sheet.getRow(row + 1);

            if (rowExcel != null) {
                lastCell = rowExcel.getLastCellNum();
                cell = rowExcel.getCell(lastCell - 1);

                if (!cell.equals(data)) {
                    cell.setCellValue(String.valueOf(data));
                }

            }
        }

        // Write the output to a file
        try (OutputStream fileOut = new FileOutputStream("res/StudentData.xlsx", true)) {
            CS_studentWorkBook.write(fileOut);
        }

        pkg2.close();

    }

    public String[][] processedData() {
        String[][] allData = new String[sheetData.length][4];

        String[] arr = new String[4];

        int i=0;
        while (i < sheetData.length)
        {
            for (ExcelSheetData pureData : sheetData) {
                arr[0] = pureData.getCourseNumber();
                arr[1] = pureData.getDescriptiveTitle();
                arr[2] = String.valueOf(pureData.getUnits());
                arr[3] = String.valueOf(pureData.getGrades());

                for (int j = 0; j < 4; j++) {
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

    public JButton getSaveData() {
        return saveData;
    }

    public void setSaveData(JButton saveData) {
        this.saveData = saveData;
    }

    public JTable getTableOfData() {
        return tableOfData;
    }

    public void setTableOfData(JTable tableOfData) {
        this.tableOfData = tableOfData;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public DefaultTableModel getModel() {
        return model;
    }

    public void setModel(DefaultTableModel model) {
        this.model = model;
    }
}
