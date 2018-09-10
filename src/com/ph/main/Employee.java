package com.ph.main;


import java.awt.*;
import java.text.*;
import java.awt.print.*;
import java.awt.event.*;
import java.sql.*;
import net.proteanit.sql.*;
import javax.swing.*;

import com.ph.db.Database;

public class Employee  extends JFrame 
{
	private JTable table;
	private JPanel Form1;
	private JTextField txtID, txtUser, txtage, txtcontact;
	private JLabel lblID, lblUser, lblage, lblcontact;
	private JButton bAdd, bSearch, bDelete, bPrint;
	private static int width = 200;
	private static int height = 20;
	private JScrollPane scroll;
	
	Connection conn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	
	
	
		public Employee()
		{
			init();
			conn = Database.ConnectDB();
			Update_table();
				
		}
		
		private void init(){
			
			bADD add1 = new bADD();
			bSearch search1 = new bSearch();
			bDelete delete1 = new bDelete();
			bPRINT print1 = new bPRINT();
			
			
			
			setSize(600,400);
			Form1 = new JPanel();
			Form1.setLayout(null);
			
			String[] column = {"Employee Number", "Employee Name", "Age", "Contact"};
			
			Object [][] data = {
					{"null", "null", "null", "null"},
					{"null", "null", "null", "null"},
					{"null", "null", "null", "null"},
				};
			
			
			table = new JTable(data, column);
			table.setSize(300, 200);
			table.setPreferredScrollableViewportSize(new Dimension(500, 100));
			table.setFillsViewportHeight(true);
			
			scroll = new JScrollPane(table);
			
			txtID = new JTextField();
			txtID.setBounds(400, 75, width, height);
			lblID = new JLabel("ID");
			lblID.setBounds(350, 75, width, height);
			
			txtUser = new JTextField();
			txtUser.setBounds(400, 100, width, height);
			lblUser = new JLabel("Name");
			lblUser.setBounds(350, 100, width, height);
			
			txtage = new JTextField();
			txtage.setBounds(400, 125, width, height);
			lblage = new JLabel("Age");
			lblage.setBounds(350, 125, width, height);
			
			txtcontact = new JTextField();
			txtcontact.setBounds(400, 150, width, height);
			lblcontact = new JLabel("Contact");
			lblcontact.setBounds(350, 150, width, height);
			
			bAdd = new JButton("ADD");
			bAdd.setBounds(350, 200, 75, 50);
			bAdd.addActionListener(add1);
			
			bSearch = new JButton("SEARCH");
			bSearch.setBounds(450, 200, 100, 50);
			bSearch.addActionListener(search1);

			bDelete = new JButton("DELETE");
			bDelete.setBounds(570, 200, 100, 50);
			bDelete.addActionListener(delete1);
			
			bPrint = new JButton("PRINT");
			bPrint.setBounds(350, 300, 100, 50);
			bPrint.addActionListener(print1);
			
			Form1.add(table);
			Form1.add(lblUser);
			Form1.add(txtUser);
			Form1.add(lblID);
			Form1.add(txtID);
			Form1.add(lblage);
			Form1.add(txtage);
			Form1.add(lblcontact);
			Form1.add(txtcontact);
			Form1.add(bAdd);
			Form1.add(bSearch);
			Form1.add(bDelete);
			Form1.add(bPrint);
			Form1.add(scroll);
	
		getContentPane().add(Form1);
		pack();
			
		}
		
		private class bPRINT implements ActionListener{
			public void actionPerformed(ActionEvent e){
				
				MessageFormat header = new MessageFormat("Header!");
				MessageFormat footer = new MessageFormat("Footer!");
				
				try{
					table.print(JTable.PrintMode.NORMAL, header, footer);
				}
				catch(Exception ep){
					System.out.println("Error: "+e);
				}
				
			}
		}
		
		private class bADD implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try{
					String sql = "INSERT into employee (employee_no, employee_name, age, contact) VALUES (?,?,?,?)";
					pst = conn.prepareStatement(sql);
					pst.setString(1, txtID.getText());
					pst.setString(2, txtUser.getText());
					pst.setString(3, txtage.getText());
					pst.setString(4, txtcontact.getText());
					
					
					pst.execute();
					JOptionPane.showMessageDialog(null, "SAVED!");
					
				}catch(Exception e){
					System.out.println("Error"+e);
				}
				Update_table();
			}
			
		}
		
		private class bDelete implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String sql = "DELETE from employee WHERE employee_no = ?";
				try{
					
					
					pst = conn.prepareStatement(sql);
					
					pst.setString(1, txtID.getText());
					pst.execute();
					
					JOptionPane.showMessageDialog(null, "DELETED!");
				}
				catch(Exception e){
					JOptionPane.showMessageDialog(null, "Error"+e);
				}
				Update_table();
			}
			
		}
		
		private class bSearch implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try{
					String sql = "SELECT * from employee WHERE employee_no = ?";
					pst = conn.prepareStatement(sql);
					pst.setString(1, txtID.getText());
					
					rs = pst.executeQuery();
						if(rs.next()){
							String add1 = rs.getString("employee_no");
							txtID.setText(add1);
							
							String add2 = rs.getString("employee_name");
							txtUser.setText(add2);
							
							String add3 = rs.getString("age");
							txtage.setText(add3);
							
							String add4 = rs.getString("contact");
							txtcontact.setText(add4);
						}
					
					
				}
				catch(Exception e){
					
					System.out.println("Error" +e);
				}
				
			}
			
		}
		
		private void Update_table(){
			try{
				String sql = "select * from employee";
				pst = conn.prepareStatement(sql);
				rs = pst.executeQuery();
				table.setModel(DbUtils.resultSetToTableModel(rs));
			}
			catch(Exception err){
				System.out.println("Error :" +err);
			}
		}
	
	public static void main(String args[]){
		Employee emp = new Employee();
		emp.setSize(800, 500);
		emp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		emp.setVisible(true);
		emp.setResizable(false);
		emp.setTitle("CRUD Program");
	}

}
