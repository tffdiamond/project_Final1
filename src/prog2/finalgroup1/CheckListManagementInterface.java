package prog2.finalgroup1;

import prog2.finalgroup1.model.ExcelSheetData;
import prog2.finalgroup1.model.UserModel;
import prog2.finalgroup1.view.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CheckListManagementInterface extends JFrame
{
    private final String loginPanelID = "login_id";
    private final String mainMenuPanelID = "main_id";
    private final String SubjectPanelID = "subject_id";
    private final String SubjectWithGradesPanelID = "subject_grades_id";
    private final String leftPanelID = "leftPanel_id";
    private final String rightPanelID = "rightPanel_id";
    private final String containerID = "container_id";
    private Login loginView;
    private UserCurriculum subjectPanel;
    private Curriculum subjectWithGradesPanel;
    private JButton subjects, subjectsWithGrades;
    private JButton user, settings;
    private final JPanel rightPanel;
    private JPanel leftPanel;
    private JPanel mainPanel;
    private JPanel container;
    CheckListManagementInterface()

    {
        CardLayout cardLayout = new CardLayout();
        setLayout(new BorderLayout());

        mainPanel = new JPanel();
        mainPanel.setLayout(cardLayout);

        leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(150, 600));

        this.rightPanel = new JPanel();
        rightPanel.setLayout(cardLayout);

        container = new JPanel();
        container.setLayout(new BorderLayout());
        container.add(leftPanel, BorderLayout.WEST);
        container.add(rightPanel, BorderLayout.CENTER);

        this.loginView = new Login();
        JButton login = loginView.getUserLogin();
        login.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if (loginView.isUserExist()) {

                    ExcelSheetData[] data = loginView.loadUserData();

                    initClassPanel(data);

                    setUpSubFrame();

                    // show the main menu components
                    changeScreen(containerID);

                }

            }
        });

        user = new JButton("User");
        initializeMainButtons(subjectsWithGrades);

        setUpMainFrame();

        // show the login menu panel
        ((CardLayout) mainPanel.getLayout()).show(mainPanel, loginPanelID);

        add(mainPanel);

        setSize(800, 600);
        setVisible(true);
    }

    private void setUpMainFrame() {
        mainPanel.add(loginPanelID, loginView);
        mainPanel.add(containerID, container);
    }
    private void setUpSubFrame() {
        subjects = new JButton("subject");
        initializeMainButtons(subjects);
        subjects.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                displaySubjectComponent();

            }
        });

        subjectsWithGrades = new JButton("subjectGrades");
        initializeMainButtons(subjectsWithGrades);
        subjectsWithGrades.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                displaySubjectWithGradesComponent();

            }
        });


        GridBagLayout mainGrid = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        leftPanel.setLayout(mainGrid);

        rightPanel.add(SubjectPanelID, subjectPanel);
        rightPanel.add(SubjectWithGradesPanelID, subjectWithGradesPanel);

        // show the UserCurriculum panel
        ((CardLayout) rightPanel.getLayout()).show(rightPanel, SubjectPanelID);

        // add buttons in the main menu panel
        constraints.gridx = 0;
        constraints.gridy = 0;
        leftPanel.add(subjects, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        leftPanel.add(subjectsWithGrades, constraints);
        leftPanel.setBackground(Color.black);


    }

    private void displaySubjectWithGradesComponent() {
        changeSubScreen(SubjectWithGradesPanelID);
    }

    private void changeSubScreen(String subScreen) {
        ((CardLayout) rightPanel.getLayout()).show(rightPanel, subScreen);
    }

    private void displaySubjectComponent() {
        changeSubScreen(SubjectPanelID);
    }

    private void initClassPanel(ExcelSheetData[] data) {
        UserModel model = loginView.getUser();

        this.subjectPanel = new UserCurriculum(data);

        this.subjectWithGradesPanel = new Curriculum(data, model);

        subjectWithGradesPanel.getAddNewCourse().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                subjectWithGradesPanel.getAdditionalCourse().addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);

                        // Insert on last row the recent added course in each table of sub panels
                        subjectPanel.insertNewDataInTable(subjectWithGradesPanel.getAdditionalCourseView().getDataToString());

                    }
                });

            }
        });

    }

    /**
     *
     * @param button
     */
    private void initializeMainButtons(JButton button) {
        buttonStyle(button);

    }

    /**
     *
     * @param button
     */
    private void buttonStyle(JButton button) {
    }

    /**
     *
     * @param screen
     */
    private void changeScreen(String screen) {
        ((CardLayout) mainPanel.getLayout()).show(mainPanel, screen);
    }
}
