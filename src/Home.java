/*
 * Project NutriSpec
 * Author: Sudarshan Phule
 * Date: 21-12-2019
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;



public class Home extends JFrame implements ActionListener{
	JPanel mainPanel;
	JPanel clientPanel;
	
	JLabel userNameLabel;
	JLabel passwordLabel;
	JLabel displayNutriLabel;
	
	JTextField userName;
	JTextField password;
	JTextField nutriSearch;
	JButton loginButton;
	JButton nutriSearchButton;
	
	JTable table;
	String[] columnNames = {"Nutritionist Name", "Contact Email"};
	
	int X=30;
	int Y=30;
	int WIDTH=100;
	int HEIGHT=25;	

	public void myFrame(){
		initialize();
		createMainPanel();
		createClientPanel();		
		setVisible(true);
		setTitle("NutriSpec Application");
	}	
	private void createClientPanel() {
		initializeClientPanel();
		showUserName();
		showSearchTextField();
		showSearchButton();
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(columnNames);
		table = new JTable(model);
		//table.setModel(model);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setFillsViewportHeight(true);
		table.setShowGrid(false);
		JScrollPane scroll = new JScrollPane(table);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setBorder(BorderFactory.createEmptyBorder());
		
		 Connection con = DB.getConnection();
		 try {
				PreparedStatement ps = con.prepareStatement("Select * from nutri");
				ResultSet rs = ps.executeQuery();
				while(rs.next()){
					String name=rs.getString(2);
					String email=rs.getString(5);				
				 
					model.addRow(new Object[] {name,email});
					displayNutriLabel = new JLabel("Displaying list of all Nutritioninst");
					displayNutriLabel.setBounds(X, Y*3, WIDTH*2, HEIGHT);
					clientPanel.add(displayNutriLabel);
					scroll.setBounds( X, Y*4, WIDTH*3+20, HEIGHT*3 );
					clientPanel.add(scroll);
					}			
				con.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}		
		clientPanel.setVisible(false);
		this.add(clientPanel);
	}
	private void showUserName() {
		if(!mainPanel.isVisible())
		{
			userNameLabel.setText("Welcome "+userName);
			clientPanel.add(userNameLabel);
		}
		add(clientPanel);		
	}
	private void showSearchButton() {
		nutriSearchButton = new JButton("Search Nutritioninst");
		nutriSearchButton.setBounds(X+WIDTH+70, Y+Y, WIDTH+50, HEIGHT);
		nutriSearchButton.addActionListener(this);
		clientPanel.add(nutriSearchButton);		
	}
	private void showSearchTextField() {
		nutriSearch= new JTextField();
		nutriSearch.setBounds(X, Y+Y, WIDTH+60, HEIGHT);
		clientPanel.add(nutriSearch);		
	}
	private void initializeClientPanel() {
		clientPanel =new JPanel();
		clientPanel.setBounds(0, 0, 400, 400);
		clientPanel.setLayout(null);		
	}
	private void createMainPanel() {
		initializeMainPanel();
		showLabels();
		showTextFields();
		showLoginButton();		
		mainPanel.setVisible(true); 
		add(mainPanel);
	}

	private void showLoginButton() {
		loginButton = new JButton("Login");
		loginButton.setBounds(X+100,Y+70,WIDTH, HEIGHT);
		loginButton.addActionListener(this);
		mainPanel.add(loginButton);		
	}
	private void showTextFields() {
		userName = new JTextField();
		userName.setBounds(X+100, Y, WIDTH, HEIGHT);
		mainPanel.add(userName);		
		
		password = new JTextField();
		password.setBounds(X+100, Y*2,WIDTH, HEIGHT);
		mainPanel.add(password);		
	}
	private void showLabels() {
		userNameLabel= new JLabel("User Name");
		userNameLabel.setBounds(X+5, Y, WIDTH, HEIGHT);
		mainPanel.add(userNameLabel);
		
		passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(X+5, Y+HEIGHT+5, WIDTH, HEIGHT);
		mainPanel.add(passwordLabel);
	}
	private void initializeMainPanel() {
		mainPanel = new JPanel(); 		
		mainPanel.setBounds(0, 0, 400, 400);
		mainPanel.setLayout(null);		
	}
	private void initialize() {
		setSize(400, 400);
		setLocationRelativeTo(null);
		setLayout(null);		
	}

	public static void main(String[] args) {
		Home obj = new Home();		
		obj.myFrame();
	}

	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == loginButton){
			
			 Connection con = DB.getConnection();
			 try {
					PreparedStatement ps = con.prepareStatement("Select * from cli");
					ResultSet rs = ps.executeQuery();
					while(rs.next()) {
						if(rs.getString(3).equals(userName.getText()))
						{
							mainPanel.setVisible(false);
							giveAccessToClientPanel(rs.getString(2));	
						}
						else
						{
							JOptionPane.showMessageDialog(this, "Enter correct username\n" , " ",JOptionPane.INFORMATION_MESSAGE); 
						}
					}
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		else
		{			
			mainPanel.setVisible(true);
			clientPanel.setVisible(false);
		}
	}
	private void giveAccessToClientPanel(String name) {
		welcomeClient(name);		
		clientPanel.setVisible(true);		
	}
	private void welcomeClient(String name) {
		userNameLabel.setBounds(X, Y-20, WIDTH*2, HEIGHT);
		userNameLabel.setText("Welcome "+name);
		clientPanel.add(userNameLabel);		
	}
}