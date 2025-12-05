package hospital.management.system;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;


import java.sql.*;

public class Update_Patient_Details extends JFrame {

    JTextField textFieldR, textFieldINTIme, textFieldAmount, textFieldPending;
    Choice choice;

    public Update_Patient_Details() {

        // ===== Panel =====
        JPanel panel = new JPanel();
        panel.setBounds(5,5,940,490);
        panel.setBackground(new Color(90, 156, 163));
        panel.setLayout(null);
        add(panel);

        // ===== Image =====
        ImageIcon imageIcon = new ImageIcon(ClassLoader.getSystemResource("icon/updated.png"));
        Image image = imageIcon.getImage().getScaledInstance(300,300,Image.SCALE_DEFAULT);
        JLabel label = new JLabel(new ImageIcon(image));
        label.setBounds(500,60,300,300);
        panel.add(label);

        // ===== Title =====
        JLabel title = new JLabel("Update Patient Details");
        title.setBounds(124,11,260,25);
        title.setFont(new Font("Tahoma",Font.BOLD,20));
        title.setForeground(Color.white);
        panel.add(title);

        // ===== Patient Name Choice =====
        JLabel labelName = new JLabel("Name :");
        labelName.setBounds(25,88,100,14);
        labelName.setFont(new Font("Tahoma",Font.PLAIN,14));
        labelName.setForeground(Color.white);
        panel.add(labelName);

        choice = new Choice();
        choice.setBounds(248,85,140,25);
        panel.add(choice);

        // Load patient names safely
        try {
            Connector c = new Connector();
            ResultSet rs = c.statement.executeQuery("SELECT Name FROM Patient_Info");
            while(rs.next()){
                choice.add(rs.getString("Name"));
            }
            rs.close();
            c.statement.close();
            c.connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error loading patients: " + e.getMessage());
        }

        // ===== Room Number =====
        JLabel labelRoom = new JLabel("Room Number :");
        labelRoom.setBounds(25,129,120,14);
        labelRoom.setFont(new Font("Tahoma",Font.PLAIN,14));
        labelRoom.setForeground(Color.white);
        panel.add(labelRoom);

        textFieldR = new JTextField();
        textFieldR.setBounds(248,129,140,20);
        panel.add(textFieldR);

        // ===== Admission Time =====
        JLabel labelTime = new JLabel("In-Time  :");
        labelTime.setBounds(25,174,100,14);
        labelTime.setFont(new Font("Tahoma",Font.PLAIN,14));
        labelTime.setForeground(Color.white);
        panel.add(labelTime);

        textFieldINTIme = new JTextField();
        textFieldINTIme.setBounds(248,174,140,20);
        panel.add(textFieldINTIme);

        // ===== Deposit Amount =====
        JLabel labelAmount = new JLabel("Amount Paid (Rs) :");
        labelAmount.setBounds(25,216,150,14);
        labelAmount.setFont(new Font("Tahoma",Font.PLAIN,14));
        labelAmount.setForeground(Color.white);
        panel.add(labelAmount);

        textFieldAmount = new JTextField();
        textFieldAmount.setBounds(248,216,140,20);
        panel.add(textFieldAmount);

        // ===== Pending Amount =====
        JLabel labelPending = new JLabel("Pending Amount (Rs) :");
        labelPending.setBounds(25,261,150,14);
        labelPending.setFont(new Font("Tahoma",Font.PLAIN,14));
        labelPending.setForeground(Color.white);
        panel.add(labelPending);

        textFieldPending = new JTextField();
        textFieldPending.setBounds(248,261,140,20);
        textFieldPending.setEditable(false);  // Pending is auto-calculated
        panel.add(textFieldPending);

        // ===== Buttons =====
        JButton check = new JButton("CHECK");
        check.setBounds(281,378,89,23);
        check.setBackground(Color.black);
        check.setForeground(Color.white);
        panel.add(check);

        check.addActionListener(e -> {
            try {
                Connector c = new Connector();
                String selectedName = choice.getSelectedItem();

                // Get patient info
                PreparedStatement pst = c.connection.prepareStatement(
                        "SELECT Room, Admission_Time, Deposit FROM Patient_Info WHERE Name=?"
                );
                pst.setString(1, selectedName);
                ResultSet rs = pst.executeQuery();

                if(rs.next()){
                    textFieldR.setText(rs.getString("Room"));
                    textFieldINTIme.setText(rs.getString("Admission_Time"));
                    textFieldAmount.setText(rs.getString("Deposit"));
                } else {
                    JOptionPane.showMessageDialog(null, "Patient not found!");
                }

                // Calculate pending amount
                PreparedStatement pstRoom = c.connection.prepareStatement(
                        "SELECT Price FROM Room WHERE Room_Number=?"
                );
                pstRoom.setString(1, textFieldR.getText());
                ResultSet rsRoom = pstRoom.executeQuery();
                if(rsRoom.next()){
                    int price = rsRoom.getInt("Price");
                    int paid = Integer.parseInt(textFieldAmount.getText());
                    int pending = price - paid;
                    textFieldPending.setText(String.valueOf(pending));
                }

                rs.close();
                pst.close();
                rsRoom.close();
                pstRoom.close();
                c.connection.close();

            } catch(Exception ex){
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        JButton update = new JButton("UPDATE");
        update.setBounds(56,378,89,23);
        update.setBackground(Color.black);
        update.setForeground(Color.white);
        panel.add(update);

        update.addActionListener(e -> {
            try {
                Connector c = new Connector();
                String selectedName = choice.getSelectedItem();

                PreparedStatement pst = c.connection.prepareStatement(
                        "UPDATE Patient_Info SET Room=?, Admission_Time=?, Deposit=? WHERE Name=?"
                );
                pst.setString(1, textFieldR.getText());
                pst.setString(2, textFieldINTIme.getText());
                pst.setString(3, textFieldAmount.getText());
                pst.setString(4, selectedName);

                int updated = pst.executeUpdate();
                if(updated > 0){
                    JOptionPane.showMessageDialog(null,"Updated Successfully");
                } else {
                    JOptionPane.showMessageDialog(null,"Update failed");
                }

                pst.close();
                c.connection.close();
            } catch(Exception ex){
                JOptionPane.showMessageDialog(null,"Error: "+ex.getMessage());
                ex.printStackTrace();
            }
        });

        JButton back = new JButton("BACK");
        back.setBounds(168,378,89,23);
        back.setBackground(Color.black);
        back.setForeground(Color.white);
        panel.add(back);

        back.addActionListener(e -> setVisible(false));

        // ===== Frame Settings =====
        setUndecorated(true);
        setSize(950,500);
        setLayout(null);
        setLocation(400,250);
        setVisible(true);
    }

}
