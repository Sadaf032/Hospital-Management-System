package hospital.management.system;

import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class All_Patient_info extends JFrame {

    public All_Patient_info() {

        // ===== Panel =====
        JPanel panel = new JPanel();
        panel.setBounds(5, 5, 890, 590);
        panel.setBackground(new Color(90, 156, 163));
        panel.setLayout(null);
        add(panel);

        // ===== Table =====
        JTable table = new JTable();
        table.setBackground(new Color(255, 255, 255));
        table.setFont(new Font("Tahoma", Font.BOLD, 12));
        table.setRowHeight(20);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(10, 40, 870, 450);
        panel.add(scroll);

        // ===== Column Labels =====
        String[] labels = {"ID", "Patient Number", "Name", "Gender", "Disease", "Room", "Admission Time", "Deposit"};
        int x = 25;
        for (String s : labels) {
            JLabel lbl = new JLabel(s);
            lbl.setFont(new Font("Tahoma", Font.BOLD, 14));
            lbl.setBounds(x, 10, 130, 20);
            panel.add(lbl);
            x += 110;
        }

        // ===== Fetch data from Oracle =====
        try {
            Connector c = new Connector();       // Create Connector instance
            Connection con = c.connection;
            Statement st = c.statement;
            ResultSet rs = st.executeQuery("SELECT * FROM Patient_Info");
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading patient data!");
        }

        // ===== Back Button =====
        JButton button = new JButton("BACK");
        button.setBounds(450, 510, 120, 30);
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        panel.add(button);

        button.addActionListener(e -> setVisible(false));

        // ===== Frame Settings =====
        setUndecorated(true);
        setSize(900, 600);
        setLayout(null);
        setLocation(300, 200);
        setVisible(true);
    }

}
