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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;

public class EditCourseView extends JPanel {
    private JButton backMainMenu;
    private JButton saveData;
    private JTable tableOfData;
    private String[][] excelData;
    private final String[] columnTitle = {"COURSE CODE", "COMPUTER SCIENCE", "UNITS"};
    private JScrollPane pane;
    private DefaultTableModel model;
    private UserModel userModel;
    private ExcelSheetData[] sheetData;
    private int row;
    private int column;
    private Object data;

    /**
     *
     * @param data
     * @param userModel
     */
    public EditCourseView(ExcelSheetData[] data, UserModel userModel)
    {
       /*
        // an algorithm to sort subjects that have been taken
       */
        GridBagLayout mainGrid = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        setPreferredSize(new Dimension(800, 500));
        setLayout(mainGrid);
        setBackground(Color.cyan);

        this.userModel = userModel;
        this.sheetData = data;

        excelData = processedData();

        setUpTable();

        saveData = new JButton("Apply");
        saveData.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

//                tableOfData.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
//
//                tableOfData.getModel().addTableModelListener(new TableModelListener() {
//                    @Override
//                    public void tableChanged(TableModelEvent e) {
//
//                        try {
//                            saveUserInput(e);
//                        } catch (InvalidFormatException | IOException ex) {
//                            throw new RuntimeException(ex);
//                        }
//
//                    }
//                });

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
                return column == 0 || column == 1;
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

    /**
     *
     * @param dataToString
     */
    public void insertNewDataInTable(String[] dataToString) {
        String[] newData = {dataToString[2], dataToString[3], dataToString[4]};
        model.insertRow(tableOfData.getRowCount(), newData);
    }

    /**
     *
     * @param e
     * @throws IOException
     * @throws InvalidFormatException
     */
    private void saveUserInput(TableModelEvent e) throws IOException, InvalidFormatException {
        OPCPackage pkg2 = OPCPackage.open(new File("res/StudentData.xlsx"));
        XSSFWorkbook CS_studentWorkBook = new XSSFWorkbook(pkg2);
        XSSFSheet sheet;
        Row rowExcel;
        Cell cell;
        int lastCell;
        row = e.getFirstRow();
        column = e.getColumn();
        TableModel model = (TableModel) e.getSource();

        if (column == -1)
        {
            return;
        }

        sheet = CS_studentWorkBook.getSheet(userModel.getUsername());

        data = model.getValueAt(row, column);

        // write the data change in appropriate sheet of a user
        rowExcel = sheet.getRow(row+1);

        // compare the description title column of Jtable to user course title
        if (column == 1) {
            if (rowExcel != null) {
                lastCell = rowExcel.getLastCellNum()-2;
                cell = rowExcel.getCell(lastCell - 1);

                if (!cell.equals(data)) {
                    cell.setCellValue(String.valueOf(data));
                }
            }
        }

        // compare the course code of Jtable column to user course code
        else if (column == 0)
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

    /**
     *
     * @return
     */
    public String[][] processedData () {
        String[][] allData = new String[sheetData.length][3];

        String[] arr = new String[3];

        int i=0;
        while (i < sheetData.length)
        {
            for (ExcelSheetData pureData : sheetData) {
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

    /**
     *
     * @return
     */
    public int getRow() {
        return row;
    }

    /**
     *
     * @param row
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     *
     * @return
     */
    public int getColumn() {
        return column;
    }

    /**
     *
     * @param column
     */
    public void setColumn(int column) {
        this.column = column;
    }

    /**
     *
     * @return
     */
    public Object getData() {
        return data;
    }

    /**
     *
     * @param data
     */
    public void setData(Object data) {
        this.data = data;
    }

    /**
     *
     * @return
     */
    public JButton getSaveData() {
        return saveData;
    }

    /**
     *
     * @param saveData
     */
    public void setSaveData(JButton saveData) {
        this.saveData = saveData;
    }
}
