package com.company.view;

import com.company.events.*;
import com.company.model.Data;
import com.company.model.Lesson;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.BlockingQueue;

public class View {

    private AppWindow window;
    private final BlockingQueue<AppEvent> blockingQueue;

    public View(BlockingQueue<AppEvent> blockingQueue, final Data data) {
        this.blockingQueue = blockingQueue;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                window = new AppWindow();
                refresh(data);
            }
        });
    }

    public void refresh(Data data) {
        window.refreshLessons(data);
    }

    public void refreshWords(Data data) {
        window.refreshWords(data);
    }

    public void showTestDialog(Data data) {
        JDialog dialog = new TestDialog(data);
        dialog.setTitle("Test");
        dialog.setSize(new Dimension(600, 600));
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    public int showInputDialog(JComponent[] inputs) {

        JPanel panel = new JPanel(new GridLayout(4,1));
        panel.add(new JLabel("DEUTSCH:"));
        panel.add(inputs[0]);
        panel.add(new JLabel("POLSKI:"));
        panel.add(inputs[1]);

        /*
        * @see: https://stackoverflow.com/questions/6251665/setting-component-focus-in-joptionpane-showoptiondialog
        * @see: https://tips4java.wordpress.com/2010/03/14/dialog-focus/
        * @see: https://github.com/tips4java/tips4java/blob/main/source/RequestFocusListener.java
        */
        inputs[0].addAncestorListener(new RequestFocusListener());

        return JOptionPane.showOptionDialog(window.frame, panel, "Dodaj Słówko",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{"OK", "Cancel"}, "OK");

    }

    class AppWindow {

        JList<String> lessonsList;
        JTable wordsTable;
        JFrame frame;

        // Column Names
        String[] columns = { "DEUTSCH", "POLSKI" };

        AppWindow() {

            lessonsList = new JList<>();
            wordsTable = new JTable();

            lessonsList.setFont(new Font("Sans-serif", Font.PLAIN, 20));
            lessonsList.setFixedCellHeight(30);
            lessonsList.addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    String lessonName = lessonsList.getSelectedValue();

                    // TODO if no available lesson, then hide toolbar

                    blockingQueue.add(new ChooseLessonEvent(lessonName));
                }
            });

            JPopupMenu popupMenuList = new JPopupMenu() {
                @Override
                public void show(Component invoker, int x, int y) {
                    int row = lessonsList.locationToIndex(new Point(x, y));
                    if (row != -1) {
                        lessonsList.setSelectedIndex(row);
                    }
                    super.show(invoker, x, y);
                }
            };
            lessonsList.setComponentPopupMenu(popupMenuList);

            JMenuItem removeLesson = new JMenuItem("Remove Lesson");
            popupMenuList.add(removeLesson);
            removeLesson.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    int choice = JOptionPane.showConfirmDialog(frame, "Do you want to remove this lesson?",
                            "Remove Lesson", JOptionPane.YES_NO_OPTION);

                    if (choice == JOptionPane.YES_OPTION) {
                        int selectedRow = lessonsList.getSelectedIndex();
                        String lessonName = lessonsList.getModel().getElementAt(selectedRow);
                        blockingQueue.add(new RemoveLessonEvent(lessonName));
                    }

                }
            });

            wordsTable.setBounds(30, 40, 200, 300);
            wordsTable.setRowHeight(30);
            wordsTable.setFont(new Font("Sans-serif", Font.PLAIN, 20));

            // Set cell renderer with the same font settings
            DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
            renderer.setFont(new Font("Sans-serif", Font.PLAIN, 20));
            wordsTable.setDefaultRenderer(Object.class, renderer);

            wordsTable.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent event) {
                    super.mousePressed(event);

                    // selects the row at which point the mouse is clicked
                    Point point = event.getPoint();
                    int currentRow = wordsTable.rowAtPoint(point);
                    wordsTable.setRowSelectionInterval(currentRow, currentRow);
                }
            });

            // Add a table model listener to detect cell value changes
            wordsTable.getModel().addTableModelListener(new TableModelListener() {
                @Override
                public void tableChanged(TableModelEvent e) {
                        System.out.println("value changed");
                }
            });

            // constructs the popup menu
            JPopupMenu popupMenu = new JPopupMenu();
            JMenuItem menuItemRemove = new JMenuItem("Remove Word");
            JMenuItem menuItemMoveUp = new JMenuItem("Move Up");
            JMenuItem menuItemMoveDn = new JMenuItem("Move Down");
            popupMenu.add(menuItemRemove);
            popupMenu.add(menuItemMoveUp);
            popupMenu.add(menuItemMoveDn);

            menuItemRemove.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    int choice = JOptionPane.showConfirmDialog(frame, "Do you want to remove this word?",
                            "Remove Word", JOptionPane.YES_NO_OPTION);

                    if (choice == JOptionPane.YES_OPTION) {
                        int selectedRow = wordsTable.getSelectedRow();
                        Vector rowData = ((DefaultTableModel) wordsTable.getModel()).getDataVector().elementAt(wordsTable.convertRowIndexToModel(wordsTable.getSelectedRow()));
                        blockingQueue.add(new RemoveWordLessonEvent((String)rowData.get(0), (String)rowData.get(1)));
                    }

                }
            });

            menuItemMoveUp.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int selectedRow = wordsTable.getSelectedRow();
                    blockingQueue.add(new MoveUpWordLessonEvent(selectedRow));
                }
            });

            menuItemMoveDn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int selectedRow = wordsTable.getSelectedRow();
                    blockingQueue.add(new MoveDnWordLessonEvent(selectedRow));
                }
            });

            // sets the popup menu for the table
            wordsTable.setComponentPopupMenu(popupMenu);

            // Set cell editor with the same font settings
            DefaultCellEditor editor = new DefaultCellEditor(new JTextField()) {
                @Override
                public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                    Component editorComponent = super.getTableCellEditorComponent(table, value, isSelected, row, column);
                    editorComponent.setFont(new Font("Sans-serif", Font.PLAIN, 20));
                    return editorComponent;
                }
            };
            wordsTable.setDefaultEditor(Object.class, editor);

            JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
            mainSplitPane.setTopComponent(new JScrollPane(lessonsList));
            mainSplitPane.setBottomComponent(new JScrollPane(wordsTable));
            mainSplitPane.setDividerLocation(200);

            JMenuBar menuBar = new MainMenuBar(blockingQueue, frame);
            JToolBar toolBar = new MainToolBar(blockingQueue, frame);
            JPanel mainPanel = new JPanel(new BorderLayout());

            mainPanel.add(toolBar, BorderLayout.NORTH);
            mainPanel.add(mainSplitPane);

            frame = new JFrame("Application");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setJMenuBar(menuBar);
            frame.add(mainPanel);
            frame.pack();
            frame.setVisible(true);
            frame.setSize(new Dimension(800, 600));
            frame.setLocationRelativeTo(null);

            assignShortcuts();
        }

        public void refreshLessons(Data data) {
            DefaultListModel<String> modelList = new DefaultListModel<String>();
            Map<String, Lesson> lessons = data.getLessons();

            // Required because we need to find index of lesson pointer by /chosenLesson/
            int index=0, iter=0;
            for (Map.Entry<String, Lesson> entry : lessons.entrySet()) {
                String key = entry.getKey();
                Lesson val = entry.getValue();
                modelList.addElement(key);
                if (val.equals(data.getChosenLesson())) {
                    index = iter;
                }
                iter++;
            }

            lessonsList.setModel(modelList);
            lessonsList.setSelectedIndex(index);
        }

        public void refreshWords(Data data) {
            Lesson lesson = data.getChosenLesson();

            // Check if lesson is even available
            if (lesson == null) return;

            // Convert list of words into array to place in JTable
            String[][] newData = lesson.getWords();

            // Create a DefaultTableModel with data and column names
            DefaultTableModel model = new DefaultTableModel(newData, columns);

            // Set new data in the DefaultTableModel
            model.setDataVector(newData, columns);

            // Refresh the table
            model.fireTableDataChanged();

            // Add a table model listener to detect cell value changes
            model.addTableModelListener(new TableModelListener() {
                @Override
                public void tableChanged(TableModelEvent e) {
                    if (e.getType() == TableModelEvent.UPDATE) {
                        Vector<Vector> rowData = ((DefaultTableModel) wordsTable.getModel()).getDataVector();
                        lesson.setWordList(rowData);
                    }
                }
            });

            wordsTable.setModel(model);
        }

        public void assignShortcuts() {
            // Define the action and its associated key stroke
            Action action = new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //System.out.println("Global shortcut activated: Ctrl + W");
                    // Perform action here
                    blockingQueue.add(new CreateWordLessonEvent());
                }
            };
            KeyStroke keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_W, KeyEvent.CTRL_DOWN_MASK);

            // Create a map to associate the key stroke with the action
            InputMap inputMap = frame.getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
            ActionMap actionMap = frame.getRootPane().getActionMap();
            inputMap.put(keyStroke, "globalShortcut");
            actionMap.put("globalShortcut", action);
        }

    }

}
