package gui;

import model.IDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by okori on 06-Apr-17.
 */
public abstract class BaseDialog extends JDialog implements IDialog {
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
        addEscapeKeyStrokeAction(rootPane);
        addEnterKeyStrokeAction(rootPane);
        return rootPane;
    }


    private void addEscapeKeyStrokeAction(JRootPane pane) {
        String key = "ESCAPE";
        Action action = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                close();
            }
        };
        addKeyStroke(action, key, pane);
    }

    private void addEnterKeyStrokeAction(JRootPane pane) {
        String key = "ENTER";
        Action action = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onEnterKeyPressed();
            }
        };

        addKeyStroke(action, key, pane);
    }

    private void addKeyStroke(Action action, String key, JRootPane pane) {
        pane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(key), key);
        pane.getActionMap().put(key, action);
    }

    protected abstract void onEnterKeyPressed();

    @Override
    public void close() {
        setVisible(false);
        dispose();
    }
}
