package com.company.view;

import com.company.events.AddWordToLessonEvent;
import com.company.events.AppEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.BlockingQueue;

public class MainToolBar extends JToolBar {

    JButton button;

    public MainToolBar(BlockingQueue<AppEvent> blockingQueue) {

        button = new JButton("Add Word");
        button.addActionListener((event) -> {

            JTextField textField1 = new JTextField();
            JTextField textField2 = new JTextField();

            final JComponent[] inputs = new JComponent[] {
                    textField1, textField2
            };

            int result = JOptionPane.showOptionDialog(
                    null,
                    inputs,
                    "Add Word",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    null);

            // TODO check result

            blockingQueue.add(new AddWordToLessonEvent(textField1.getText(), textField2.getText()));

        });
        add(button);

        button = new JButton("Test");
        button.addActionListener((event) -> {

            var basePanel = new JPanel();
            basePanel.setLayout(new BoxLayout(basePanel, BoxLayout.Y_AXIS));

            JDialog dialog = new JDialog();
            dialog.add(basePanel);

            var testPanel1 = new JPanel();
            testPanel1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            testPanel1.setLayout(new GridLayout(4, 1, 5, 5));

            JComponent[] buttons = {
                    new JLabel("koń"),
                    new JLabel("ptak"),
                    new JLabel("hipopotam"),
                    new JLabel("pies"),
            };

            for (JComponent jComponent : buttons) {
                testPanel1.add(jComponent);
                jComponent.setFont(new Font("Serif", Font.PLAIN, 20));
            }

            var testPanel2 = new JPanel();
            testPanel2.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            testPanel2.setLayout(new GridLayout(4, 1, 5, 5));

            JComponent[] fields = {
                    new JTextField(),
                    new JTextField(),
                    new JTextField(),
                    new JTextField(),
            };

            for (JComponent field : fields) {
                testPanel2.add(field);
                field.setFont(new Font("Serif", Font.PLAIN, 20));
            }

            var testPanel3 = new JPanel();
            testPanel3.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            testPanel3.setLayout(new GridLayout(4, 1, 5, 5));

            JComponent[] answers = {
                    new JLabel("test"),
                    new JLabel("test"),
                    new JLabel("test"),
                    new JLabel("test"),
            };

            for (JComponent answer : answers) {
                testPanel3.add(answer);
                answer.setFont(new Font("Serif", Font.PLAIN, 20));
            }

            JPanel tempPanel = new JPanel(new BorderLayout());
            tempPanel.add(testPanel1, BorderLayout.WEST);
            tempPanel.add(testPanel2, BorderLayout.CENTER);
            tempPanel.add(testPanel3, BorderLayout.EAST);
            testPanel3.setVisible(false);
            basePanel.add(new JScrollPane(tempPanel));

            var bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

            var checkBtn = new JButton("Check");
            checkBtn.setMnemonic(KeyEvent.VK_N);
            checkBtn.addActionListener((checkEvent) -> {
                testPanel3.setVisible(true);
                checkBtn.setEnabled(false);
            });

            var closeBtn = new JButton("Close");
            closeBtn.setMnemonic(KeyEvent.VK_C);
            closeBtn.addActionListener((closeEvent) -> {
                dialog.dispatchEvent(new WindowEvent(dialog, WindowEvent.WINDOW_CLOSING));
            });

            bottomPanel.add(checkBtn);
            bottomPanel.add(closeBtn);
            basePanel.add(bottomPanel);

            bottomPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

            //dialog.setModal(true);
            dialog.setTitle("Tip of the Day");
            dialog.setPreferredSize(new Dimension(450, 350));
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);

            dialog.addWindowListener(new WindowAdapter()
            {
                @Override
                public void windowClosing (WindowEvent e)
                {
                    super.windowClosing(e);
                }
            });

        });
        add(button);

    }

}
