
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TODOlistUI extends JFrame{
    public final static int WIDTH = 400;
    public final static int HEIGHT = 400;

    private JPanel panel;
    private JLabel date;
    private JSpinner spinner;
    private JButton exit, add, edit;
    private ConnectionPool pool;
    private Connection con;
    private SimpleDateFormat formatDate;
    public static TasksTableModel tasksTable;
    private JTable tasks;
    private TODOlist task;

    public TODOlistUI() {
        initVariables();
        buildGUI();
        tasksTable.ShowData(con, formatDate.format(spinner.getValue()));
    }

    private void initVariables() {
        pool = new ConnectionPool();
        pool.initConnectionPool("com.mysql.jdbc.Driver", "jdbc:mysql://127.0.0.1/todolist", "root", "123456");
        con = pool.getConnection();
        task = new TODOlist();
        formatDate = new SimpleDateFormat("yyyy-MM-dd");
    }

    private void buildGUI() {
        setTitle("TODO-list");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);

        panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);

        date = new JLabel("Дата: ");
        date.setBounds(10, 3, 450, 20);
        date.setFont(new Font("Arial", Font.BOLD, 16));

        spinner = new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH));
        spinner.setEditor(new JSpinner.DateEditor(spinner, "yyyy-MM-dd"));
        spinner.setBounds(60, 0, 120, 27);

        exit = new JButton("Выход");
        exit.setBounds(10, 335, 75, 25);
        exit.setFont(new Font("Arial", Font.PLAIN, 14));
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                dispose();
            }
        });

        add = new JButton("Добавить");
        add.setBounds(120, 335, 100, 25);
        add.setFont(new Font("Arial", Font.PLAIN, 14));
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                EditForm e = new EditForm(con);
                e.frame.setVisible(true);
            }
        });

        edit = new JButton("Редактировать");
        edit.setBounds(250, 335, 135, 25);
        edit.setFont(new Font("Arial", Font.PLAIN, 14));
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                EditForm edit = new EditForm(con, task);
                edit.frame.setVisible(true);
            }
        });
        edit.setEnabled(false);

        tasksTable = new TasksTableModel();
        tasks = new JTable(tasksTable);
        JScrollPane scroll = new JScrollPane(tasks);
        scroll.setBounds(10, 35, 375, 295);
        tasks.setTableHeader(null);
        tasks.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                task.setDate(formatDate.format(spinner.getValue()));
                task.setTime(tasks.getValueAt(tasks.getSelectedRow(), 0).toString());
                task.setPlace(tasks.getValueAt(tasks.getSelectedRow(), 1).toString());
                task.setDefinition(tasks.getValueAt(tasks.getSelectedRow(), 2).toString());
                task.setImportance(tasks.getValueAt(tasks.getSelectedRow(), 3).toString());
                edit.setEnabled(true);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                edit.setEnabled(false);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        spinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent arg0) {
                tasksTable.ShowData(con, formatDate.format(spinner.getValue()));
            }
        });

        panel.add(date);
        panel.add(spinner);
        panel.add(exit);
        panel.add(add);
        panel.add(edit);
        panel.add(scroll);
        add(panel);
    }

    public static void main(String[] args){
        TODOlistUI ui = new TODOlistUI();
        ui.setVisible(true);
    }
}

