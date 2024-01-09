//package EventHandling;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;

public class TextEditor extends JFrame implements ActionListener {
    private JTextArea textArea;
    private JScrollPane scrollBar;
    private JMenuBar menu;
    private JMenu fileMenu;
    private JMenu helpMenu;
    private JMenu editMenu;
    private JMenuItem newItem;
    private JMenuItem openItem;
    private JMenuItem saveItem;
    private JMenuItem exitItem;
    private JMenuItem undoItem;
    private JMenuItem redoItem;
    private JMenuItem copyItem;
    private JMenuItem selectAllItem;
    private UndoManager undoManager;
    private JMenuItem helpItem;
    private File currentFile;

    TextEditor() {
        JFrame frame = new JFrame();
        frame.setResizable(true);
        frame.setTitle("TextEditor");
        frame.setSize(500, 500);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_S) {
                    save();
                }
            }
        });

        scrollBar = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        frame.add(scrollBar, BorderLayout.CENTER);

        menu = new JMenuBar();
        fileMenu = new JMenu("File");
        helpMenu = new JMenu("Help");
        editMenu = new JMenu("Edit");

        newItem = new JMenuItem("New");
        openItem = new JMenuItem("Open");
        saveItem = new JMenuItem("Save");
        exitItem = new JMenuItem("Exit");
        undoItem = new JMenuItem("Undo");
        redoItem = new JMenuItem("Redo");
        copyItem = new JMenuItem("Copy");
        selectAllItem = new JMenuItem("Select All");
        helpItem = new JMenuItem(("Help"));

        undoManager = new UndoManager();
        textArea.getDocument().addUndoableEditListener(undoManager);

        newItem.addActionListener(this);
        openItem.addActionListener(this);
        saveItem.addActionListener(this);
        exitItem.addActionListener(this);
        undoItem.addActionListener(this);
        redoItem.addActionListener(this);
        copyItem.addActionListener(this);
        selectAllItem.addActionListener(this);
        helpItem.addActionListener(this);

        newItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));
        undoItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));
        redoItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_DOWN_MASK));
        copyItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
        selectAllItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));

        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);

        editMenu.add(undoItem);
        editMenu.add(redoItem);
        editMenu.add(copyItem);
        editMenu.add(selectAllItem);
        helpMenu.add(helpItem);

        menu.add(fileMenu);
        menu.add(editMenu);
        menu.add(helpMenu);
        frame.setJMenuBar(menu);

        //frame.add(createTopPanel(), BorderLayout.NORTH);
        frame.add(scrollBar, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        return topPanel;
    }

    private void showHelpDialog() {
        JOptionPane.showMessageDialog(this,
                "TextEditor Help:\n" +
                        "- New: Create a new file\n" +
                        "- Open: Open an existing file\n" +
                        "- Save: Save the current file\n" +
                        "- Exit: Close the application\n" +
                        "- Undo: Undo the last action\n" +
                        "- Redo: Redo the last undone action\n" +
                        "- Copy: Copy the selected text\n" +
                        "- Select All: Select all text in the editor\n" +
                        "- Shortcut Keys:\n" +
                        "  - New: Ctrl + N\n" +
                        "  - Open: Ctrl + O\n" +
                        "  - Save: Ctrl + S\n" +
                        "  - Exit: Ctrl + Q\n" +
                        "  - Undo: Ctrl + Z\n" +
                        "  - Redo: Ctrl + Y\n" +
                        "  - Copy: Ctrl + C\n" +
                        "  - Select All: Ctrl + A",
                "TextEditor Help",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void save() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("."));

        int response = fileChooser.showSaveDialog(null);
        if (response == JFileChooser.APPROVE_OPTION) {
            currentFile = fileChooser.getSelectedFile();
            try (PrintWriter fileOut = new PrintWriter(currentFile)) {
                fileOut.println(textArea.getText());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TextEditor());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newItem) {
            textArea.setText("");
        } else if (e.getSource() == openItem) {
            open();
        } else if (e.getSource() == saveItem) {
            save();
        } else if (e.getSource() == exitItem) {
            System.exit(0);
        } else if (e.getSource() == undoItem) {
            if (undoManager.canUndo()) {
                undoManager.undo();
            }
        } else if (e.getSource() == redoItem) {
            if (undoManager.canRedo()) {
                undoManager.redo();
            }
        } else if (e.getSource() == copyItem) {
            textArea.copy();
        } else if (e.getSource() == selectAllItem) {
            textArea.selectAll();
        }
        else if(e.getSource()==helpItem)
            showHelpDialog();
    }

    private void open() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("."));
        FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("Text Files", "txt");
        fileChooser.setFileFilter(fileFilter);

        int response = fileChooser.showOpenDialog(null);
        if (response == JFileChooser.APPROVE_OPTION) {
            currentFile = fileChooser.getSelectedFile();
            try (Scanner fileIn = new Scanner(currentFile)) {
                if (currentFile.isFile()) {
                    textArea.setText("");
                    while (fileIn.hasNextLine()) {
                        String curr = fileIn.nextLine() + "\n";
                        textArea.append(curr);
                    }
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
