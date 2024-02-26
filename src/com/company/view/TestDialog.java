package com.company.view;

import com.company.model.Data;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TestDialog extends JDialog {

    private final JComponent[][] testData;

    private JPanel createTestPanel(int size) {
        JPanel testPanel = new JPanel();
        int gridSize = (size > 10) ? size : size + 10;
        testPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        testPanel.setLayout(new GridLayout(gridSize, 1, 5, 5));
        return testPanel;
    }

    public TestDialog(Data data) {

        var basePanel = new JPanel();
        basePanel.setLayout(new BoxLayout(basePanel, BoxLayout.Y_AXIS));

        add(basePanel);

        String[][] words = data.getChosenLesson().getWords();

        var testPanel0 = createTestPanel(words.length);
        var testPanel2 = createTestPanel(words.length);
        var testPanel1 = createTestPanel(words.length);

        testData = new JComponent[words.length][];
        for (int i=0; i < words.length; i++) {
            testData[i] = new JComponent[]{
                new JLabel(words[i][1]),
                new JTextField(),
                new JLabel(words[i][0]),
            };
        }
        for (JComponent[] componentArray : testData) {

            testPanel0.add(componentArray[0]);
            testPanel1.add(componentArray[1]);
            testPanel2.add(componentArray[2]);

            for (Component component : componentArray) {
                component.setFont(new Font("Sans-serif", Font.PLAIN, 20));
            }

        }

        JPanel tempPanel = new JPanel(new BorderLayout());
        tempPanel.add(testPanel0, BorderLayout.WEST);
        tempPanel.add(testPanel1, BorderLayout.CENTER);
        tempPanel.add(testPanel2, BorderLayout.EAST);
        testPanel2.setVisible(false);
        basePanel.add(new JScrollPane(tempPanel));

        var bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        var checkBtn = new JButton("Check");
        checkBtn.setMnemonic(KeyEvent.VK_N);
        checkBtn.addActionListener((checkEvent) -> {

            for (JComponent[] componentArray : testData) {
                JTextField textField = (JTextField)componentArray[1];
                JLabel answerLabel = (JLabel)componentArray[2];
                if (answerLabel.getText().equals(textField.getText())) {
                    answerLabel.setForeground(new Color(0, 128, 0));
                } else {
                    answerLabel.setForeground(Color.RED);
                }
            }

            testPanel2.setVisible(true);
            checkBtn.setEnabled(false);
        });

        var closeBtn = new JButton("Close");
        closeBtn.setMnemonic(KeyEvent.VK_C);
        closeBtn.addActionListener((closeEvent) -> {
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        });

        bottomPanel.add(checkBtn);
        bottomPanel.add(closeBtn);
        basePanel.add(bottomPanel);

        bottomPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        //dialog.setModal(true);
        //setTitle("Tip of the Day");
        //setPreferredSize(new Dimension(450, 350));
        //setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        //setLocationRelativeTo(null);
        //setVisible(true);

        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing (WindowEvent e)
            {
                super.windowClosing(e);
            }
        });


    }

}
