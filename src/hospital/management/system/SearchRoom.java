package hospital.management.system;

import net.proteanit.sql.DbUtils;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class SearchRoom extends JFrame {

    private Choice choice;
    private JTable table;

    public SearchRoom() {

        // ===== Panel =====
        JPanel panel = new JPanel();
        panel.setBounds(5, 5, 700, 500);
        panel.setBackground(new Color(90, 156, 163));
        panel.setLayout(null);
        add(panel);

        // ===== Title =====
        JLabel title = new JLabel("Search For Room");
        title.setBounds(220, 10, 300, 30);
        title.setForeground(Color.white);
        title.setFont(new Font("Tahoma", Font.BOLD, 20));
        panel.add(title);

        // ===== Choice for Availability =====
        JLabel statusLabel = new JLabel("Status:");
        statusLabel.setBounds(70, 70, 80, 20);
        statusLabel.setForeground(Color.white);
        statusLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        panel.add(statusLabel);

        choice = new Choice();
        choice.setBounds(170, 70, 120, 20);
        choice.add("Available");
        choice.add("Occupied");
        panel.add(choice);

        // ===== Table =====
        table = new JTable();
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(10, 150, 670, 250);
        panel.add(scroll);

        // ===== Column Labels =====
        String[] labels = {"Room Number", "Availability", "Price", "Bed Type"};
        int x = 23;
        for (String s : labels) {
            JLabel lbl = new JLabel(s);
            lbl.setBounds(x, 125, 150, 20);
            lbl.setForeground(Color.white);
            lbl.setFont(new Font("Tahoma", Font.BOLD, 14));
            panel.add(lbl);
            x += 150;
        }

        // ===== Load all rooms initially =====
        loadTable("SELECT * FROM Room");

        // ===== Search Button =====
        JButton searchBtn = new JButton("Search");
        searchBtn.setBounds(200, 420, 120, 25);
        searchBtn.setBackground(Color.BLACK);
        searchBtn.setForeground(Color.WHITE);
        panel.add(searchBtn);

        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchRooms();
            }
        });

        // ===== Back Button =====
        JButton backBtn = new JButton("Back");
        backBtn.setBounds(380, 420, 120, 25);
        backBtn.setBackground(Color.black);
        backBtn.setForeground(Color.white);
        panel.add(backBtn);

        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false); // Close JFrame
            }
        });

        // ===== Frame Settings =====
        setUndecorated(true);
        setSize(700, 500);
        setLayout(null);
        setLocation(450, 250);
        setVisible(true);
    }

    // ===== Load all rooms =====
    private void loadTable(String query) {
        try {
            Connector c = new Connector(); // Existing Connector class
            Statement st = c.statement;
            ResultSet rs = st.executeQuery(query);
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading room data!");
        }
    }

    // ===== Search rooms by availability =====
    private void searchRooms() {
        try {
            Connector c = new Connector();
            String q = "SELECT * FROM Room WHERE Availability = ?";
            PreparedStatement pst = c.connection.prepareStatement(q);
            pst.setString(1, choice.getSelectedItem());
            ResultSet rs = pst.executeQuery();
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error searching rooms!");
        }
    }
}
