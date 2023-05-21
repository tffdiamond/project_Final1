import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.*;

public class test1 extends JFrame {
    private JTable table;

    public test1() {
        initializeTable();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Table InputMethodListener Example");
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
     /*
        - This method implementation is appropriate for final project 2 csv/dat file format
     */
//    private void createUserDataFile() {
//        int i=0;
//        File file;
//
//        try
//        {
//            do {
//                file = new File("data" + i);
//
//                if (file.createNewFile()) {
//                    break;
//                } else {
//                    i++;
//                    new File("data" + i);
//                }
//            } while (file.exists());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//    }

    private void initializeTable() {
        // Create a sample table model
        Object[][] data = {
                {"John", "Doe"},
                {"Jane", "Smith"}
        };
        String[] columns = {"First Name", "Last Name"};
        DefaultTableModel model = new DefaultTableModel(data, columns);

        // Create the table and set the model
        table = new JTable(model);

        // Add the InputMethodListener to the table's text component
        Component editorComponent = table.getEditorComponent();
        if (editorComponent instanceof JTextComponent) {
            JTextComponent textComponent = (JTextComponent) editorComponent;
            textComponent.addInputMethodListener(new InputMethodListener() {
                @Override
                public void inputMethodTextChanged(InputMethodEvent e) {
                    // Handle text composition changes here
                    System.out.println("Text composition changed: " + e.getText());
                }

                @Override
                public void caretPositionChanged(InputMethodEvent e) {
                    // Handle caret position changes here
                    System.out.println("Caret position changed: " + e.getCaret());
                }
            });
        }

        // Add the table to a scroll pane and set it as the content pane of the frame
        JScrollPane scrollPane = new JScrollPane(table);
        setContentPane(scrollPane);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(test1::new);
    }
}