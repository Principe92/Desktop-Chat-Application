package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by okori on 06-Apr-17.
 */
public abstract class BaseDialog extends JDialog {
    public BaseDialog(JFrame parent, String title) {
        super(parent, title);

        this.setMinimumSize(new Dimension(400, 400));

        getContentPane().add(buildLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
    }

    protected abstract Component buildLayout();

    @Override
    public JRootPane createRootPane() {
        JRootPane rootPane = new JRootPane();
        KeyStroke stroke = KeyStroke.getKeyStroke("ESCAPE");
        Action action = new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent e) {
                close();
            }
        };
        InputMap inputMap = rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(stroke, "ESCAPE");
        rootPane.getActionMap().put("ESCAPE", action);
        return rootPane;
    }

    public void close() {
        setVisible(false);
        dispose();
    }
}
