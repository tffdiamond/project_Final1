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
    private final String EditSubjectGradePanelID = "edit_subject_grade_id";
    private final String EditCourseID = "edit_courseID";
    private LoginView loginView;
    private SubjectView subjectPanel;
    private SubjectWithGradesView subjectWithGradesPanel;
    private EditSubjectGradeView editSubjectGradePanel;
    private EditCourseView editCoursePanel;
    private JButton subjects, subjectsWithGrades, enterGrades, courseEdit;
    private final JPanel cardPanel;
    private final JPanel mainMenuPanel;
    CheckListManagementInterface()
    {

        GridBagLayout mainGrid = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();

        mainMenuPanel = new JPanel();
        mainMenuPanel.setLayout(mainGrid);

        this.loginView = new LoginView();
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
                    changeScreen(mainMenuPanelID);
                }

            }
        });

        CardLayout cardLayout = new CardLayout();
        this.cardPanel = new JPanel(cardLayout);

        subjects = new JButton("subject");
        initButton(subjects);
        subjects.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                displaySubjectComponent();

            }
        });

        subjectsWithGrades = new JButton("subjectGrades");
        initButton(subjectsWithGrades);
        subjectsWithGrades.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                displaySubjectWithGradesComponent();

            }
        });

        enterGrades = new JButton("enterGrades");
        initButton(enterGrades);
        enterGrades.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                displayEnterGradesComponent();

            }
        });

        courseEdit = new JButton("editCourse");
        initButton(courseEdit);
        courseEdit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                displayCourseEditComponent();

            }
        });

        // add buttons in the main menu panel
        mainMenuPanel.add(subjects);
        mainMenuPanel.add(subjectsWithGrades);
        mainMenuPanel.add(enterGrades);
        mainMenuPanel.add(courseEdit);

        mainMenuPanel.setBackground(Color.black);

        setUpMainFrame();

        // add the container in the frame
        add(cardPanel);

        // show the login menu panel
        ((CardLayout)cardPanel.getLayout()).show(cardPanel, loginPanelID);

        setSize(800, 500);
        setVisible(true);
    }

    private void setUpSubFrame() {
        cardPanel.add(mainMenuPanel, mainMenuPanelID);
        cardPanel.add(subjectPanel, SubjectPanelID);
        cardPanel.add(subjectWithGradesPanel, SubjectWithGradesPanelID);
        cardPanel.add(editSubjectGradePanel, EditSubjectGradePanelID);
        cardPanel.add(editCoursePanel, EditCourseID);
    }

    private void displayCourseEditComponent() {
        changeScreen(EditCourseID);
    }

    private void displayEnterGradesComponent() {
        changeScreen(EditSubjectGradePanelID);
    }

    private void displaySubjectWithGradesComponent() {
        changeScreen(SubjectWithGradesPanelID);
    }

    private void displaySubjectComponent() {
        changeScreen(SubjectPanelID);
    }

    private void initClassPanel(ExcelSheetData[] data) {
        UserModel model = loginView.getUser();

        this.subjectPanel = new SubjectView(data);
        subjectPanel.getBackMainMenu().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                // go back in main menu panel
                changeScreen(mainMenuPanelID);

            }
        });

        this.subjectWithGradesPanel = new SubjectWithGradesView(data, model);
        subjectWithGradesPanel.getBackMainMenu().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                // go back in main menu panel
                changeScreen(mainMenuPanelID);

            }
        });

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
                        editSubjectGradePanel.insertNewDataInTable(subjectWithGradesPanel.getAdditionalCourseView().getDataToString());
                        editCoursePanel.insertNewDataInTable(subjectWithGradesPanel.getAdditionalCourseView().getDataToString());

                    }
                });

            }
        });

        this.editSubjectGradePanel = new EditSubjectGradeView(data, model);
        editSubjectGradePanel.getBackMainMenu().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                // go back in main menu panel
                changeScreen(mainMenuPanelID);

            }
        });

        editSubjectGradePanel.getSaveData().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                // update the table correspond to the new data
                int row = editSubjectGradePanel.getRow();
                int col = editSubjectGradePanel.getColumn() + 2;
                Object data = editSubjectGradePanel.getData();

                subjectWithGradesPanel.getModel().setValueAt(data, row, col);
            }
        });

        this.editCoursePanel = new EditCourseView(data, model);
        editCoursePanel.getBackMainMenu().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                // go back in main menu panel
                changeScreen(mainMenuPanelID);

            }
        });

        editCoursePanel.getSaveData().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                // update table of sub panels correspond to the new data
                int row = editCoursePanel.getRow();
                int col = editCoursePanel.getColumn();
                Object data = editCoursePanel.getData();

                if (col == 0) {
                    subjectWithGradesPanel.getModel().setValueAt(data, row, col+2);
                    subjectPanel.getModel().setValueAt(data, row, col + 2);
                    editSubjectGradePanel.getModel().setValueAt(data, row, col);
                }
                else if (col == 1)
                {
                    subjectWithGradesPanel.getModel().setValueAt(data, row, col+2);
                    subjectPanel.getModel().setValueAt(data, row, col + 2);
                    editSubjectGradePanel.getModel().setValueAt(data, row, col);
                }

            }
        });

    }

    /**
     *
     * @param button
     */
    private void initButton(JButton button) {
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
        ((CardLayout)cardPanel.getLayout()).show(cardPanel, screen); // -> show the appropriate panel
        // The arguments are the "parent panel" and the "id" of a child panel in a parent panel, respectively.
    }

    private void setUpMainFrame() {
        cardPanel.add(loginView, loginPanelID);
    }
}
