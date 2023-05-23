package prog2.finalgroup1.view;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import prog2.finalgroup1.model.ExcelSheetData;
import prog2.finalgroup1.model.UserModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class AdditionalCourseView extends JFrame {
    private JComboBox<String> semester;
    private JComboBox<String> year;
    private String semesterItem;
    private String yearItem;
    private JTextField courseCode;
    private JTextField courseTitle;
    private JTextField units;
    private JTextField grades;
    private JButton additionalCourse;
    private JButton back;
    private UserModel userModel;
    private ExcelSheetData newData;
    private String[] dataToString;

    /**
     *
     * @param userModel
     */
    AdditionalCourseView(UserModel userModel)
    {
        setSize(400, 400);
        GridBagLayout mainGrid = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(mainGrid);
        mainPanel.setPreferredSize(new Dimension(400, 400));
        mainPanel.setBackground(Color.cyan);

        this.userModel = userModel;

        additionalCourse = new JButton("Add");
        additionalCourse.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                try {
                     newData = addedCourse();
                     insertUserNewData();
                } catch (InvalidFormatException | IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

        back = new JButton("Back");
        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

            }
        });

        String[] semesters = {"1","2","3"};
        semester = new JComboBox<>(semesters);
        semester.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JComboBox<?> comboBox = (JComboBox<?>) e.getSource();
                semesterItem = (String) comboBox.getSelectedItem();

            }
        });

        String[] years = {"1","2","3","4"};
        year = new JComboBox<>(years);
        year.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JComboBox<?> comboBox = (JComboBox<?>) e.getSource();
                yearItem = (String) comboBox.getSelectedItem();

            }
        });

        JLabel yearLabel = new JLabel("Year: ");
        JLabel semesterLabel = new JLabel("Semester: ");
        JLabel courseCodeLabel = new JLabel("Course Code: ");
        courseCode = new JTextField(12);
        JLabel courseTitleLabel = new JLabel("Course Title: ");
        courseTitle = new JTextField(12);
        JLabel unitLabel = new JLabel("Units: ");
        units = new JTextField(12);
        JLabel gradeLabel = new JLabel("Grade: ");
        grades = new JTextField(12);

        constraints.gridx = 0;
        constraints.gridy = 0;
        mainPanel.add(courseCodeLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        mainPanel.add(courseCode, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        mainPanel.add(courseTitleLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        mainPanel.add(courseTitle, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        mainPanel.add(unitLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        mainPanel.add(units, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        mainPanel.add(semesterLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.insets = new Insets(0,0,0,90);
        mainPanel.add(semester, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.insets = new Insets(0,0,0,0);
        mainPanel.add(yearLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.insets = new Insets(0,0,0,90);
        mainPanel.add(year, constraints);

        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.insets = new Insets(0,0,0,0);
        mainPanel.add(gradeLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 5;
        constraints.insets = new Insets(0,0,0,0);
        mainPanel.add(grades, constraints);

        constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.insets = new Insets(20,0,0,0);
        mainPanel.add(back, constraints);

        constraints.gridx = 1;
        constraints.gridy = 6;
        constraints.insets = new Insets(20,0,0,0);
        mainPanel.add(additionalCourse, constraints);


        add(mainPanel);

        setVisible(true);

    }

    /**
     *
     * @return
     */
    private ExcelSheetData addedCourse() {

        return new ExcelSheetData(Integer.parseInt(yearItem), Integer.parseInt(semesterItem), courseCode.getText()
                , courseTitle.getText(), Double.parseDouble(units.getText()), grades.getText());

    }

    /**
     *
     * @throws InvalidFormatException
     * @throws IOException
     */
    private void insertUserNewData() throws InvalidFormatException, IOException {
        dataToString = new String[]{String.valueOf(newData.getYear()), String.valueOf(newData.getTerm()),
                newData.getCourseNumber(), newData.getDescriptiveTitle(), String.valueOf(newData.getUnits()),
                newData.getGrades()};

        OPCPackage pkg2 = OPCPackage.open(new File("res/StudentData.xlsx"));
        XSSFWorkbook CS_studentWorkBook = new XSSFWorkbook(pkg2);
        XSSFSheet sheet;
        Row row;
        Row myRow;

        sheet = CS_studentWorkBook.getSheet(userModel.getUsername());
        int rows = sheet.getLastRowNum();
        myRow = sheet.createRow(rows + 1);
        row = sheet.getRow(sheet.getLastRowNum() - 1);

        if (row != null)
        {
            for (int i=0; i<row.getLastCellNum(); i++)
            {
                myRow.createCell(i);
            }

            for (int j = 0 ; j < myRow.getLastCellNum() || j < dataToString.length; j++) {
                if (myRow.getCell(j) != null) {
                    myRow.getCell(j).setCellValue(dataToString[j]);
                }
            }

        }

        try (FileOutputStream fos = new FileOutputStream("res/StudentData.xlsx", true)){
            CS_studentWorkBook.write(fos);
        }

        pkg2.close();

    }

    /**
     *
     * @return
     */
    public String getSemesterItem() {
        return semesterItem;
    }

    /**
     *
     * @param semesterItem
     */
    public void setSemesterItem(String semesterItem) {
        this.semesterItem = semesterItem;
    }

    /**
     *
     * @return
     */
    public String getYearItem() {
        return yearItem;
    }

    /**
     *
     * @param yearItem
     */
    public void setYearItem(String yearItem) {
        this.yearItem = yearItem;
    }

    /**
     *
     * @return
     */
    public JTextField getCourseCode() {
        return courseCode;
    }

    /**
     *
     * @param courseCode
     */
    public void setCourseCode(JTextField courseCode) {
        this.courseCode = courseCode;
    }

    /**
     *
     * @return
     */
    public JTextField getCourseTitle() {
        return courseTitle;
    }

    /**
     *
     * @param courseTitle
     */
    public void setCourseTitle(JTextField courseTitle) {
        this.courseTitle = courseTitle;
    }

    /**
     *
     * @return
     */
    public JTextField getUnits() {
        return units;
    }

    /**
     *
     * @param units
     */
    public void setUnits(JTextField units) {
        this.units = units;
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
    public JButton getBack() {
        return back;
    }

    /**
     *
     * @param back
     */
    public void setBack(JButton back) {
        this.back = back;
    }

    /**
     *
     * @return
     */
    public ExcelSheetData getNewData() {
        return newData;
    }

    /**
     *
     * @param newData
     */
    public void setNewData(ExcelSheetData newData) {
        this.newData = newData;
    }

    /**
     *
     * @return
     */
    public String[] getDataToString() {
        return dataToString;
    }

    /**
     *
     * @param dataToString
     */

    public void setDataToString(String[] dataToString) {
        this.dataToString = dataToString;
    }
}


