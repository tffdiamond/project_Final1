package prog2.finalgroup1.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class SignUpView extends JPanel {

    private final JTextField firstName;
    private final JTextField lastName;
    private final JTextField username;
    private final JPasswordField password;
    private JButton sign_up;
    private JButton back_button;    
    private XSSFWorkbook CS_curriculumWorkBook;
    private XSSFWorkbook CS_studentWorkBook;

    public SignUpView() {
        GridBagLayout mainGrid = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        setPreferredSize(new Dimension(800, 500));
        setLayout(mainGrid);
        setBackground(Color.GREEN);

        sign_up = new JButton("Sign Up");
        sign_up.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if (Objects.equals(username.getText(), "") || Objects.equals(password.getText(), "") || Objects.equals(firstName.getText(), "") || Objects.equals(lastName.getText(), "")) {
                    JOptionPane.showMessageDialog(null, "Please provide an information to all given inputs");
                } else if (!Objects.equals(username.getText(), "") || !Objects.equals(password.getText(), "") || !Objects.equals(firstName.getText(), "") || !Objects.equals(lastName.getText(), "")) {
                    boolean isAccountCreated;

                    try {
                        isAccountCreated = userInformation();

                        if (isAccountCreated) {
                            JOptionPane.showMessageDialog(null, "Account created");
                            createUserDataFile();
                            setVisible(false);
                        }
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }

                }

            }
        });

        back_button = new JButton("<-");
        back_button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

            }
        });

        firstName = new JTextField(10);
        lastName = new JTextField(10);
        username = new JTextField(10);
        password = new JPasswordField(10);

        add(firstName);
        add(lastName);
        add(username);
        add(password);
        add(sign_up);
        add(back_button);
    }

    private boolean userInformation() throws IOException {
        ArrayList<String> listOfAccountCreated = new ArrayList<>();
        String usersPrivateInformation;
        boolean value;

        BufferedReader inputStream = new BufferedReader(new FileReader("usersData/privateInformation"));
        PrintWriter outputStream = new PrintWriter(new FileWriter("usersData/privateInformation", true));

        do {
            usersPrivateInformation = inputStream.readLine();
            listOfAccountCreated.add(usersPrivateInformation);
        } while (usersPrivateInformation != null);

        if (listOfAccountCreated.contains(username.getText())) {
            JOptionPane.showMessageDialog(null, "Username is unavailable");
            value = false;
        } else {
            outputStream.println(username.getText()+","+password.getText());
            value = true;
        }
        inputStream.close();
        outputStream.close();

        return value;
    }

    private void createUserDataFile() throws IOException, InvalidFormatException {
        OPCPackage pkg1 = OPCPackage.open(new File("res/BSCSCurriculumData1.xlsx"));
        CS_curriculumWorkBook = new XSSFWorkbook(pkg1);

        OPCPackage pkg2 = OPCPackage.open(new File("res/StudentData.xlsx"));
        CS_studentWorkBook = new XSSFWorkbook(pkg2);

        XSSFSheet studentSheet = null;
        XSSFCell myCell;
        XSSFCell cell;
        XSSFRow studentSheetRow;
        XSSFRow row;
        String sheetName = username.getText();
        int firstRow;
        int lastRow;
        int firstCell;
        int lastCell;

        // get a copy from original curriculum data
        XSSFSheet sheet = CS_curriculumWorkBook.getSheetAt(0);

        studentSheet = CS_studentWorkBook.createSheet(sheetName);

        firstRow = sheet.getFirstRowNum();
        lastRow = sheet.getLastRowNum();

        for (int i = firstRow; i < lastRow; i++) {
            row = sheet.getRow(i);
            studentSheetRow = studentSheet.createRow(i);
            if (row != null) {
                firstCell = row.getFirstCellNum();
                lastCell = row.getLastCellNum();
                for (int iCell = firstCell; iCell < lastCell; iCell++) {
                    cell = row.getCell(iCell);
                    myCell = studentSheetRow.createCell(iCell);
                    if (cell != null) {
                        myCell.setCellType(cell.getCellTypeEnum());
                            switch (cell.getCellType()) {
                                case XSSFCell.CELL_TYPE_BLANK -> myCell.setCellValue("");
                                case XSSFCell.CELL_TYPE_BOOLEAN -> myCell.setCellValue(cell.getBooleanCellValue());
                                case XSSFCell.CELL_TYPE_ERROR -> myCell.setCellErrorValue(cell.getErrorCellValue());
                                case XSSFCell.CELL_TYPE_NUMERIC -> myCell.setCellValue(cell.getNumericCellValue());
                                case XSSFCell.CELL_TYPE_STRING -> myCell.setCellValue(cell.getStringCellValue());
                                default -> myCell.setCellFormula(cell.getCellFormula());
                            }
                        }
                    }
                }
            }

        // Write the output to a file
        try (OutputStream fileOut = new FileOutputStream("res/StudentData.xlsx", true)) {
            CS_studentWorkBook.write(fileOut);
        }

        pkg1.close();
        pkg2.close();

    }

    public JButton getSign_up() {
        return sign_up;
    }

    public void setSign_up(JButton sign_up) {
        this.sign_up = sign_up;
    }

    public JButton getBack_button() {
        return back_button;
    }

    public void setBack_button(JButton back_button) {
        this.back_button = back_button;
    }

    }


