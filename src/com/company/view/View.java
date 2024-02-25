package com.company.view;

import com.company.events.AddWordToLessonEvent;
import com.company.events.ChooseLessonEvent;
import com.company.model.Data;
import com.company.events.AppEvent;
import com.company.model.Lesson;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Map;
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
        JDialog dialog;
        dialog = new TestDialog(data);
        dialog.setTitle("Test");
        dialog.setPreferredSize(new Dimension(450, 350));
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    class AppWindow {

        JList<String> lessonsSidebar;
        JTable tablePanel;

        // Column Names
        String[] columns = { "DEUTSCH", "POLSKI" };

        AppWindow() {

            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

//                FontUIResource font = new FontUIResource("Verdana", Font.PLAIN, 24);
//                UIManager.put("Table.font", font);
//                UIManager.put("Table.foreground", Color.RED);

            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                ex.printStackTrace();
            }

            lessonsSidebar = new JList<>();
            tablePanel = new JTable();

            lessonsSidebar.setFont(new Font("Serif", Font.PLAIN, 20));
            lessonsSidebar.setFixedCellHeight(30);
            lessonsSidebar.addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    String lessonName = lessonsSidebar.getSelectedValue();

                    // TODO if no available lesson, then hide toolbar

                    blockingQueue.add(new ChooseLessonEvent(lessonName));
                }
            });

            tablePanel.setBounds(30, 40, 200, 300);
            tablePanel.setRowHeight(30);
            //tablePanel.setFont(new Font("Serif", Font.PLAIN, 20));

            // Set font globally for JTable
            tablePanel.setFont(new Font("Serif", Font.PLAIN, 20));

            // Set cell renderer with the same font settings
            DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
            renderer.setFont(new Font("Serif", Font.PLAIN, 20));
            tablePanel.setDefaultRenderer(Object.class, renderer);

            // Set cell editor with the same font settings
            DefaultCellEditor editor = new DefaultCellEditor(new JTextField()) {
                @Override
                public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                    Component editorComponent = super.getTableCellEditorComponent(table, value, isSelected, row, column);
                    editorComponent.setFont(new Font("Serif", Font.PLAIN, 20));
                    return editorComponent;
                }
            };
            tablePanel.setDefaultEditor(Object.class, editor);

            JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
            mainSplitPane.setTopComponent(new JScrollPane(lessonsSidebar));
            mainSplitPane.setBottomComponent(new JScrollPane(tablePanel));
            mainSplitPane.setDividerLocation(200);

            JMenuBar menuBar = new MainMenuBar(blockingQueue);
            JToolBar toolBar = new MainToolBar(blockingQueue);
            JPanel mainPanel = new JPanel(new BorderLayout());

            mainPanel.add(toolBar, BorderLayout.NORTH);
            mainPanel.add(mainSplitPane);

            JFrame frame = new JFrame("Application");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setJMenuBar(menuBar);
            frame.add(mainPanel);
            frame.pack();
            frame.setVisible(true);
            frame.setSize(new Dimension(800, 600));
            frame.setLocationRelativeTo(null);
        }

        public void refreshLessons(Data data) {
            DefaultListModel<String> modelList = new DefaultListModel<String>();
            Map<String, Lesson> lessons = data.getLessons();

            // Required because we need to find index of lesson pointer by <chosenLesson>
            int index=0, iter=0;
            for (Map.Entry<String, Lesson> entry : lessons.entrySet()) {
                String key = entry.getKey();
                Lesson val = entry.getValue();
                modelList.addElement(key);
                if (val.equals(data.getChosenLesson())) {
                    //System.out.println(val);
                    index = iter;
                }
                iter++;
            }

            lessonsSidebar.setModel(modelList);
            lessonsSidebar.setSelectedIndex(index);
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

            tablePanel.setModel(model);
        }

    }



}
