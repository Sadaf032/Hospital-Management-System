package hospital.management.system;

import javax.swing.*;      // <-- You forgot this import
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.*;


public class Patient_Discharge extends JFrame {

    public Patient_Discharge() {

        JPanel panel = new JPanel();
        panel.setBounds(5, 5, 790, 390);
        panel.setBackground(new Color(90, 156, 163));
        panel.setLayout(null);
        add(panel);

        JLabel label = new JLabel("CHECK-OUT");
        label.setBounds(100, 20, 200, 30);
        label.setFont(new Font("Tahoma", Font.BOLD, 22));
        label.setForeground(Color.white);
        panel.add(label);

        JLabel label2 = new JLabel("Patient Number");
        label2.setBounds(30, 80, 150, 20);
        label2.setFont(new Font("Tahoma", Font.BOLD, 14));
        label2.setForeground(Color.white);
        panel.add(label2);

        Choice choice = new Choice();
        choice.setBounds(200, 80, 150, 25);
        panel.add(choice);

        // ===== Load Patient Numbers =====
        try {
            Connector c = new Connector();
            ResultSet rs = c.statement.executeQuery("SELECT Patient_Number FROM hr.Patient_Info");

            while (rs.next()) {
                choice.add(rs.getString("Patient_Number"));
            }
            rs.close();
            c.statement.close();
            c.connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        JLabel label3 = new JLabel("Room Number");
        label3.setBounds(30, 130, 150, 20);
        label3.setFont(new Font("Tahoma", Font.BOLD, 14));
        label3.setForeground(Color.white);
        panel.add(label3);

        JLabel RNo = new JLabel();
        RNo.setBounds(200, 130, 150, 20);
        RNo.setFont(new Font("Tahoma", Font.BOLD, 14));
        RNo.setForeground(Color.white);
        panel.add(RNo);

        JLabel label4 = new JLabel("In Time");
        label4.setBounds(30, 180, 150, 20);
        label4.setFont(new Font("Tahoma", Font.BOLD, 14));
        label4.setForeground(Color.white);
        panel.add(label4);

        JLabel INTime = new JLabel();
        INTime.setBounds(200, 180, 250, 20);
        INTime.setFont(new Font("Tahoma", Font.BOLD, 14));
        INTime.setForeground(Color.white);
        panel.add(INTime);

        JLabel label5 = new JLabel("Out Time");
        label5.setBounds(30, 230, 150, 20);
        label5.setFont(new Font("Tahoma", Font.BOLD, 14));
        label5.setForeground(Color.white);
        panel.add(label5);

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        JLabel OUTTime = new JLabel(sdf.format(date));
        OUTTime.setBounds(200, 230, 250, 20);
        OUTTime.setFont(new Font("Tahoma", Font.BOLD, 14));
        OUTTime.setForeground(Color.white);
        panel.add(OUTTime);

        // ===== Check Button =====
        JButton Check = new JButton("Check");
        Check.setBounds(170, 300, 120, 30);
        Check.setBackground(Color.black);
        Check.setForeground(Color.white);
        panel.add(Check);

        Check.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connector c = new Connector();
                    PreparedStatement pst = c.connection.prepareStatement(
                            "SELECT Room, Admission_Time FROM hr.Patient_Info WHERE Patient_Number=?"
                    );
                    pst.setString(1, choice.getSelectedItem());
                    ResultSet rs = pst.executeQuery();

                    if (rs.next()) {
                        RNo.setText(rs.getString("Room"));
                        INTime.setText(rs.getString("Admission_Time"));
                    } else {
                        JOptionPane.showMessageDialog(null, "No data found!");
                    }

                    rs.close();
                    pst.close();
                    c.connection.close();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        // ===== Discharge Button =====
        JButton discharge = new JButton("Discharge");
        discharge.setBounds(30, 300, 120, 30);
        discharge.setBackground(Color.black);
        discharge.setForeground(Color.white);
        panel.add(discharge);

        discharge.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (RNo.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please check patient details first!");
                    return;
                }

                try {
                    Connector c = new Connector();

                    // Delete patient
                    PreparedStatement pst = c.connection.prepareStatement(
                            "DELETE FROM hr.Patient_Info WHERE Patient_Number=?"
                    );
                    pst.setString(1, choice.getSelectedItem());
                    pst.executeUpdate();
                    pst.close();

                    // Update room availability
                    PreparedStatement pst2 = c.connection.prepareStatement(
                            "UPDATE hr.Room SET Availability='Available' WHERE Room_Number=?"
                    );
                    pst2.setString(1, RNo.getText());
                    pst2.executeUpdate();
                    pst2.close();

                    c.connection.close();

                    JOptionPane.showMessageDialog(null, "Patient Discharged Successfully!");
                    setVisible(false);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        // ===== Back Button =====
        JButton Back = new JButton("Back");
        Back.setBounds(300, 300, 120, 30);
        Back.setBackground(Color.black);
        Back.setForeground(Color.white);
        panel.add(Back);

        Back.addActionListener(e -> setVisible(false));

        setUndecorated(true);
        setSize(800, 400);
        setLayout(null);
        setLocation(400, 250);
        setVisible(true);
    }
}
