import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class Frame {

    JTextArea sendTextField = new JTextArea();
    JTextArea getTextField = new JTextArea();
    JButton button;
    JScrollPane sendPane = new JScrollPane(sendTextField, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    JScrollPane getPane = new JScrollPane(getTextField, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);


    public void createGUI() {
//        JFrame frame = new JFrame("Chatting roon");
        JFrame frame = new JFrame("Chatting Room");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container contentPane = new MyPanel();
        frame.setContentPane(contentPane);
        contentPane.setLayout(new FlowLayout());

        button = new JButton("send");
        button.addActionListener(new ButtonListener());
//        contentPane.add(button);
        contentPane.add(sendPane);
        contentPane.add(getPane);
        sendTextField.addKeyListener(new KeyBoardListener());

        frame.setSize(500, 600);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Frame().createGUI();
            }
        });
    }

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String message = sendTextField.getText();
            try {
                String getMessage = new Client(message).send();
                sendTextField.setText("");
                getTextField.setText(getMessage);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    private class KeyBoardListener extends KeyAdapter {
        @Override
        public void keyTyped(KeyEvent e) {
            if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                button.doClick();
            }
        }
    }

    class MyPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            int width = this.getWidth();
            int height = this.getHeight();
            g.clearRect(0, 0, width, height);
            g.setColor(Color.BLACK);
            g.fillRect(100, 0, 5, height);
            g.fillRect(100, height-100, width-100, 5);
            sendTextField.setBounds(105, height-95, width-105, 100);
            sendTextField.setLineWrap(true);
            sendPane.setBounds(105, height-95, width-105, 96);
            getTextField.setBounds(105, 0, width-105, height-105);
            getTextField.setLineWrap(true);
            getTextField.setEditable(false);
            getPane.setBounds(105, 0, width-105, height-100);
        }
    }
}

