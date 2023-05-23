package prog2.finalgroup1.view;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import prog2.finalgroup1.model.ExcelSheetData;
import prog2.finalgroup1.model.UserModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

public class LoginView extends JPanel {
    private final String mainPanelID = "main_id";
    private final String signUpPanelID = "sign-up-id";
    private SignUpView signUpView;
    private UserModel user;
    private JTextField username;
    private JPasswordField password;
    private JButton userLogin;
    private final JPanel cardPanel;
    private final JPanel mainPanel;
    private boolean userExist;

    public LoginView()
    {
        GridBagLayout mainGrid = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        setPreferredSize(new Dimension(800, 500));
        mainPanel = new JPanel(mainGrid);
        mainPanel.setBackground(Color.pink);

        CardLayout cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        initPanel();

        JLabel usernameLabel = new JLabel("Username");
        username = new JTextField(10);
        JLabel passwordLabel = new JLabel("Password");
        password = new JPasswordField(10);


        userLogin = new JButton("Login");
        userLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                try {
                    if (Objects.equals(username.getText(), "") || Objects.equals(password.getText(), "")) {
                        JOptionPane.showMessageDialog(null, "Please input the student username and password");
                    } else if (!Objects.equals(username.getText(), "") && !Objects.equals(password.getText(), "")) {
                        user = new UserModel(username.getText(), password.getText());

                        userExist = authenticateUserLogin(user);

                        if (!userExist){
                            JOptionPane.showMessageDialog(null, "Username does not exist!");
                        }

                        setVisible(false);
                    }
                } catch (Exception exception)
                {
                    exception.printStackTrace();
                }
            }
        });

        JButton userSignUp = new JButton("Sign Up");
        userSignUp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                displaySignUpComponent();

            }
        });

        setUpFrame();

        // add components in main panel
        mainPanel.add(usernameLabel);
        mainPanel.add(username);
        mainPanel.add(passwordLabel);
        mainPanel.add(password);
        mainPanel.add(userLogin);
        mainPanel.add(userSignUp);

        // add the card panel in the main frame
        add(cardPanel);

        // show the main panel
        ((CardLayout) cardPanel.getLayout()).show(cardPanel, mainPanelID);

    }

    private void setUpFrame() {
        cardPanel.add(mainPanel, mainPanelID);
        cardPanel.add(signUpView, signUpPanelID);
    }

    private void displaySignUpComponent() {
        changeScreen(signUpPanelID);
    }

    /**
     *
     * @param screen
     */
    private void changeScreen(String screen) {
        ((CardLayout) cardPanel.getLayout()).show(cardPanel, screen);
    }

    private void initPanel() {
        signUpView = new SignUpView();

        signUpView.getBack_button().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                changeScreen(mainPanelID);

            }
        });
    }

    /**
     *
     * @param user
     * @return
     * @throws IOException
     */
    private boolean authenticateUserLogin(UserModel user) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("usersData/privateInformation"));
        String lineOfText = reader.readLine();

        while (lineOfText != null){

        String[] tempArr = lineOfText.split(",");

        if (tempArr[0].equals(user.getUsername()) && tempArr[1].equals(user.getPassword())) {
                return true;
        }

        lineOfText = reader.readLine();

        }

        return false;
    }

    /**
     *
     * @return
     */
    public ExcelSheetData[] loadUserData() {
        ExcelSheetData[] data;
        try {
            XSSFWorkbook workbook = new XSSFWorkbook("res/StudentData.xlsx");
            XSSFSheet sheet = workbook.getSheet(username.getText());

            Iterator<Row> rowIterator = sheet.iterator();

            // instantiate the array
            data = new ExcelSheetData[sheet.getLastRowNum()];

            int i = 0;
            while (rowIterator.hasNext())
            {
                // iterates each rows
                Row row = rowIterator.next();

                // skip the first row
                if (row.getRowNum() == 0)
                    continue;

                data[i] = rowCells(row);
                i++;
            }
            return data;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param row
     * @return
     */
    private ExcelSheetData rowCells(Row row) {
        return new ExcelSheetData((int) Double.parseDouble(String.valueOf(row.getCell(0))),
                (int) Double.parseDouble(String.valueOf(row.getCell(1))),
                String.valueOf(row.getCell(2)),String.valueOf(row.getCell(3))
                ,Double.parseDouble(String.valueOf(row.getCell(4))), String.valueOf(row.getCell(5)));
    }

    /**
     *
     * @return
     */
    public JButton getUserLogin() {
        return userLogin;
    }

    /**
     *
     * @param userLogin
     */
    public void setUserLogin(JButton userLogin) {
        this.userLogin = userLogin;
    }

    /**
     *
     * @return
     */
    public JTextField getUsername() {
        return username;
    }

    /**
     *
     * @return
     */
    public JTextField getPassword() {
        return password;
    }

    /**
     *
     * @return
     */
    public boolean isUserExist() {
        return userExist;
    }

    /**
     *
     * @param userExist
     */
    public void setUserExist(boolean userExist) {
        this.userExist = userExist;
    }

    /**
     *
     * @return
     */
    public UserModel getUser() {
        return user;
    }

    /**
     *
     * @param user
     */
    public void setUser(UserModel user) {
        this.user = user;
    }
}
