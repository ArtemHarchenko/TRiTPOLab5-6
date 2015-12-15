import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Влад on 08.12.2015.
 */
public class SettingsForm extends JFrame {
    public final static int WIDTH = 300;
    public final static int HEIGHT = 120;

    private JPanel panel;
    private JLabel settings;
    private JButton back;
    private JButton ok;
    private JComboBox editComboBox;

    public SettingsForm() {
        setTitle("TODO-list");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);

        panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);

        settings = new JLabel("Интервал автоудаления: ");
        settings.setBounds(10, 15, 450, 20);
        settings.setFont(new Font("Arial", Font.PLAIN, 16));

        String[] intervals = {"1", "5", "10", "30"};
        editComboBox = new JComboBox(intervals);
        editComboBox.setBounds(210, 15, 40, 25);
        editComboBox.setEditable(true);

        back = new JButton("Назад");
        back.setBounds(10, 60, 75, 25);
        back.setFont(new Font("Arial", Font.PLAIN, 14));
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                setVisible(false);
            }
        });

        ok = new JButton("Готово");
        ok.setBounds(190, 60, 100, 25);
        ok.setFont(new Font("Arial", Font.PLAIN, 14));
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
            }
        });

        panel.add(settings);
        panel.add(editComboBox);
        panel.add(ok);
        panel.add(back);
        add(panel);
    }
}
