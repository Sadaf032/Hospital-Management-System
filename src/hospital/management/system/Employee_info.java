package hospital.management.system;
import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Employee_info extends JFrame {

    JTable table;

    // Constructor
    Employee_info() {
        // Main panel
        JPanel panel = new JPanel();
        panel.setBounds(5, 5, 990, 590);
        panel.setBackground(new Color(109, 164, 170));
        panel.setLayout(null);
        add(panel);

        // Table
        table = new JTable();
        table.setBounds(10, 50, 960, 450);
        table.setBackground(Color.WHITE);
        table.setFont(new Font("Tahoma", Font.PLAIN, 14));
        panel.add(table);

        // Column labels
        JLabel[] labels = new JLabel[6];
        String[] colNames = {"Emp_ID", "Name", "Department", "Designation", "Phone", "Email"};
        for (int i = 0; i < labels.length; i++) {
            labels[i] = new JLabel(colNames[i]);
            labels[i].setFont(new Font("Tahoma", Font.BOLD, 14));
            labels[i].setBounds(10 + (i * 150), 20, 140, 20);
            panel.add(labels[i]);
        }

        // BACK button
        JButton button = new JButton("BACK");
        button.setBounds(400, 520, 120, 30);
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        panel.add(button);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false); // Close JFrame
            }
        });

        // Load data from Oracle
        loadEmployeeData();

        // JFrame settings
        setUndecorated(true);
        setSize(1000, 600);
        setLocation(350, 230);
        setLayout(null);
        setVisible(true);
    }
    // Method to load data
    private void loadEmployeeData() {
        try {
            Connector c = new Connector(); // Custom Oracle connection class
            String query = "SELECT * FROM EMPLOYEE_INFO"; // Match table name exactly
            ResultSet rs = c.statement.executeQuery(query);
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading employee data!");
        }
    }
}