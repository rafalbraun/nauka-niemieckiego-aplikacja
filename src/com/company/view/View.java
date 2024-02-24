package com.company.view;

import com.company.model.Data;
import com.company.events.AppEvent;
import com.company.model.Lesson;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.Collection;
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
        //DefaultListModel<String> demoList = new DefaultListModel<String>();
        //demoList.addElement("addElements");
        //setModel(demoList);

        window.refreshLessons(data);
        window.refreshWords(data);
    }

    class AppWindow {

        JList<Lesson> lessonsSidebar;
        JTable tablePanel;

        AppWindow() {

            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

//                FontUIResource font = new FontUIResource("Verdana", Font.PLAIN, 24);
//                UIManager.put("Table.font", font);
//                UIManager.put("Table.foreground", Color.RED);

            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                ex.printStackTrace();
            }

            String[][] data = {
                    { "die Zeit (ż.)", "czas" },
                    { "das Beispiel (n.)", "przykład" },
                    { "das Jahr (n.)", "rok" },
                    { "der Morgen (m.)", "poranek" },
                    { "die Stadt (f.)", "miasto" }
            };

            // Column Names
            String[] columnNames = { "Original", "Translation" };

            lessonsSidebar = new JList<>();
            tablePanel = new JTable(data, columnNames);

            lessonsSidebar.setFont(new Font("Serif", Font.PLAIN, 20));
            lessonsSidebar.setFixedCellHeight(30);

            tablePanel.setBounds(30, 40, 200, 300);
            tablePanel.setRowHeight(30);
            tablePanel.setFont(new Font("Serif", Font.PLAIN, 20));

            //TableColumn col = tablePanel.getColumnModel().getColumn(0);
            //col.setCellRenderer(new CustomTableCellRenderer());

            JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
            mainSplitPane.setTopComponent(new JScrollPane(lessonsSidebar));
            mainSplitPane.setBottomComponent(new JScrollPane(tablePanel));
            mainSplitPane.setDividerLocation(200);
            //mainSplitPane.setLayout(new BorderLayout());

            JMenuBar menuBar = new MainMenuBar(blockingQueue);

            JFrame frame = new JFrame("Application");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setJMenuBar(menuBar);
            //frame.add(lessonsSidebar);
            frame.add(mainSplitPane);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            frame.setSize(new Dimension(800, 600));
            frame.setLocationRelativeTo(null);
        }

        public class CustomTableCellRenderer extends JLabel implements TableCellRenderer {

            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int rowIndex, int vColIndex) {

                //Color newColor = (Color)color;
                //setBackground(newColor);
                setFont(new Font("Serif", Font.PLAIN, 20));
                setText(value.toString());
                setBorder(new EmptyBorder(20, 10, 20, 10));

                return this;
            }
        }

        public void refreshLessons(Data data) {
            DefaultListModel<Lesson> modelList = new DefaultListModel<Lesson>();
            Collection<Lesson> lessons = data.getLessons();
            for(Lesson lesson : lessons) {
                modelList.addElement(lesson);
            }
            lessonsSidebar.setModel(modelList);
        }

        public void refreshWords(Data data) {
            Lesson lesson = data.getChosenLesson();

        }

    }



}
