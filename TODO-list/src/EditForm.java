import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class EditForm {
    public final static int WIDTH = 355;
    public final static int HEIGHT = 250;

    private JPanel panel;
    private JLabel time,  definition, place, date, importance;
    private JButton back, ok, delete, settings;
    private SettingsForm set;
    private JSpinner dateSpinner, timeSpinner;
    private JComboBox importanceComboBox;
    private JTextField placeTextField, definitionTextArea;
    private TODOlistDAO dao;
    private TODOlist task;
    private SimpleDateFormat formatDate, formatTime;
    public JFrame frame;

    public EditForm(Connection con) {
        initVariables(con);
        buildGUI();

        delete.setEnabled(false);

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                Random rand = new Random();
                task.setId(rand.nextInt(2147483647));
                task.setDate(formatDate.format(dateSpinner.getValue()));
                task.setTime(formatTime.format(timeSpinner.getValue()));
                task.setPlace(placeTextField.getText());
                task.setDefinition(definitionTextArea.getText());
                task.setImportance(importanceComboBox.getSelectedItem().toString());
                dao.add(task);
                TODOlistUI.tasksTable.add(task);
                JOptionPane.showMessageDialog(frame, "Дело успешно добавлено");
                frame.dispose();
            }
        });
    }

    public EditForm(Connection con, TODOlist taskk) {
        initVariables(con);
        this.task = taskk;
        buildGUI();
        initGUI(taskk);

        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                dao.remove(task);
                TODOlistUI.tasksTable.remove(task);
                JOptionPane.showMessageDialog(frame, "Дело успешно удалено");
                frame.dispose();
            }
        });

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                int index = TODOlistUI.tasksTable.getRowNumber(task);
                int id = dao.getID(task);
                task.setId(id);
                task.setDate(formatDate.format(dateSpinner.getValue()));
                task.setTime(formatTime.format(timeSpinner.getValue()));
                task.setPlace(placeTextField.getText());
                task.setDefinition(definitionTextArea.getText());
                task.setImportance(importanceComboBox.getSelectedItem().toString());
                dao.update(task);
                TODOlistUI.tasksTable.update(task, index);
                JOptionPane.showMessageDialog(frame, "Дело успешно обновлено");
                frame.dispose();
            }
        });
    }

    private void initVariables(Connection con) {
        task = new TODOlist();
        formatDate = new SimpleDateFormat("yyyy-MM-dd");
        formatTime = new SimpleDateFormat("hh:mm");
        dao = new TODOlistDAO(con);
        set = new SettingsForm();
    }

    private void buildGUI() {
        frame = new JFrame("TODO-list");
        frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);

        date = new JLabel("Дата: ");
        date.setBounds(10, 30, 450, 20);
        date.setFont(new Font("Arial", Font.PLAIN, 16));
        time = new JLabel("Время: ");
        time.setBounds(10, 55, 450, 20);
        time.setFont(new Font("Arial", Font.PLAIN, 16));

        place = new JLabel("Место: ");
        place.setBounds(10, 80, 450, 20);
        place.setFont(new Font("Arial", Font.PLAIN, 16));

        placeTextField = new JTextField();
        placeTextField.setBounds(100, 80, 245, 25);
        placeTextField.setFont(new Font("Arial", Font.PLAIN, 16));

        definition = new JLabel("Описание: ");
        definition.setBounds(10, 105, 450, 20);
        definition.setFont(new Font("Arial", Font.PLAIN, 16));

        definitionTextArea = new JTextField();
        definitionTextArea.setBounds(100, 105, 245, 25);
        definitionTextArea.setFont(new Font("Arial", Font.PLAIN, 16));

        importance = new JLabel("Важность: ");
        importance.setBounds(10, 130, 450, 20);
        importance.setFont(new Font("Arial", Font.PLAIN, 16));

        settings = new JButton("+");
        settings.setBounds(300, 5, 45, 25);
        settings.setFont(new Font("Arial", Font.PLAIN, 14));
        settings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                set.setVisible(true);
            }
        });

        dateSpinner = new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH));
        dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd"));
        dateSpinner.setBounds(100, 30, 120, 26);

        timeSpinner = new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH));
        timeSpinner.setEditor(new JSpinner.DateEditor(timeSpinner, "hh:mm"));
        timeSpinner.setBounds(100, 55, 120, 26);

        String[] importances = {"Обязательно", "Важно", "Желательно", "Если хватит сил"};
        importanceComboBox = new JComboBox(importances);
        importanceComboBox.setBounds(100, 130, 120, 25);
        importanceComboBox.setEditable(false);

        back = new JButton("Назад");
        back.setBounds(10, 185, 75, 25);
        back.setFont(new Font("Arial", Font.PLAIN, 14));
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                frame.dispose();
            }
        });

        delete = new JButton("Удалить");
        delete.setBounds(95, 185, 100, 25);
        delete.setFont(new Font("Arial", Font.PLAIN, 14));

        ok = new JButton("Готово");
        ok.setBounds(205, 185, 135, 25);
        ok.setFont(new Font("Arial", Font.PLAIN, 14));

        panel.add(date);
        panel.add(time);
        panel.add(place);
        panel.add(placeTextField);
        panel.add(definition);
        panel.add(definitionTextArea);
        panel.add(importance);
        panel.add(dateSpinner);
        panel.add(timeSpinner);
        panel.add(back);
        panel.add(ok);
        panel.add(delete);
        panel.add(settings);
        panel.add(importanceComboBox);
        frame.add(panel);
    }

    private void initGUI(TODOlist task) {
        dateSpinner.setValue(new Date(Integer.valueOf(task.getDate().substring(0, 4)) - 1900,
                Integer.valueOf(task.getDate().substring(5, 7)) - 1 ,
                Integer.valueOf(task.getDate().substring(8))));
        Date d = new Date();
        d.setHours(Integer.valueOf(task.getTime().substring(0, 2)));
        d.setMinutes(Integer.valueOf(task.getTime().substring(3, 5)));
        timeSpinner.setValue(d);
        placeTextField.setText(task.getPlace());
        definitionTextArea.setText(task.getDefinition());
        importanceComboBox.setSelectedItem(task.getImportance());
    }
}
