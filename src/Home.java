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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.mysql.jdbc.Statement;

public class Home extends JFrame implements ActionListener{
	JPanel mainPanel;
	JPanel clientPanel;
	JPanel adminPanel;
	JPanel nutritionistPanel;
	JPanel allNutriPanel;
	JPanel addNewNutriPanel;
	JPanel removeNutriPanel;
	JPanel allClientPanel;
	JPanel addNewClientPanel;
	JPanel removeClientPanel;
	
	JLabel idLabel;
	JLabel fullNameLabel;
	JLabel userNameLabel;
	JLabel passwordLabel;
	JLabel emailIdLabel;
	JLabel displayNutriLabel;
	JLabel userTypeLabel;
	
	JTextField idTextField;
	JTextField fullNameTextField;
	JTextField userName;
	JTextField password;
	JTextField emailIdTextField;

	JTextField nutriSearch;
	JButton loginButton;
	JButton nutriSearchButton;
	JButton clientSearchButton;
	JButton logoutButton;
	JButton homeButton;

	JButton showAllNutri;
	JButton showAllClients;
	JButton addNutri;
	JButton addClient;
	JButton removeNutri;
	JButton removeClient;

	JComboBox userTypeCombo;
	
	JTable table;
	String[] columnNames = {"Name", "Contact Email"};
	String[] userTypes = {"Client","Nutritionist","Admin"};
	int X=30;
	int Y=30;
	int WIDTH=100;
	int HEIGHT=25;	
	public static void main(String[] args) {
		Home obj = new Home();		
		obj.myFrame();
	}
	public void myFrame(){
		
		initialize();
		createMainPanel();
		
		setVisible(true);
		setTitle("NutriSpec Application");
	}	
	private void createAddNewNutriPanel() {
		addNewNutriPanel=initializePanel(addNewNutriPanel);
		showAllLabels();
		showAllTextFields();
		showSubmitButton();
		addNewNutriPanel.setVisible(false);
		add(addNewNutriPanel);
	}
	private void showSubmitButton() {
		addNutri.setText("Add Nutritionist");
		addNutri.setBounds(X*3, Y*8, WIDTH*2, HEIGHT);
		addNutri.addActionListener(this);
		addNewNutriPanel.add(addNutri);		
	}
	private void showAllTextFields() {
		idTextField= new JTextField();
		idTextField.setBounds(X*4, Y*2, WIDTH*2, HEIGHT);
		addNewNutriPanel.add(idTextField);		
		fullNameTextField= new JTextField();
		fullNameTextField.setBounds(X*4, Y*3, WIDTH*2, HEIGHT);
		addNewNutriPanel.add(fullNameTextField);		
		userName.setBounds(X*4, Y*4, WIDTH*2, HEIGHT);
		addNewNutriPanel.add(userName);		
		password.setBounds(X*4, Y*5, WIDTH*2, HEIGHT);
		addNewNutriPanel.add(password);		
		emailIdTextField = new JTextField();
		emailIdTextField.setBounds(X*4, Y*6, WIDTH*2, HEIGHT);
		addNewNutriPanel.add(emailIdTextField);		
	}
	private void showAllLabels() {
		idLabel= new JLabel("ID*");
		idLabel.setBounds(X, Y*2, WIDTH, HEIGHT);
		addNewNutriPanel.add(idLabel);
		fullNameLabel= new JLabel("Full Name*");
		fullNameLabel.setBounds(X, Y*3, WIDTH, HEIGHT);
		addNewNutriPanel.add(fullNameLabel);		
		userNameLabel.setBounds(X, Y*4, WIDTH, HEIGHT);
		addNewNutriPanel.add(userNameLabel);
		passwordLabel.setBounds(X, Y*5, WIDTH, HEIGHT);
		addNewNutriPanel.add(passwordLabel);
		emailIdLabel= new JLabel("Email ID*");
		emailIdLabel.setBounds(X, Y*6, WIDTH, HEIGHT);
		addNewNutriPanel.add(emailIdLabel);
	}
	private void createAllNutriPanel() {
		allNutriPanel=initializePanel(allNutriPanel);
		showHomeButton();
		displayAllNutritionist('N');
		allNutriPanel.setVisible(false);
		add(allNutriPanel);
	}
	private void showHomeButton() {
		homeButton = new JButton("Home");
		homeButton.setBounds(X-20, Y-20, WIDTH-20, HEIGHT);
		homeButton.addActionListener(this);
		allNutriPanel.add(homeButton);		
	}
	private void initialize() {
		setSize(400, 400);
		setLocationRelativeTo(null);
		setLayout(null);		
	}
	private void createMainPanel() {
		mainPanel=initializePanel(mainPanel);
		showLabels();
		showTextFields();
		selectUserType();
		showLoginButton();		
		mainPanel.setVisible(true); 
		add(mainPanel);
	}
	private JPanel initializePanel(JPanel panel) {
		panel = new JPanel(); 		
		panel.setBounds(0, 0, 400, 400);
		panel.setLayout(null);	
		return panel;
	}
	private void showLabels() {
		userNameLabel= new JLabel("User Name*");
		userNameLabel.setBounds(X+5, Y, WIDTH, HEIGHT);
		mainPanel.add(userNameLabel);
		
		passwordLabel = new JLabel("Password*");
		passwordLabel.setBounds(X+5, Y+HEIGHT+5, WIDTH, HEIGHT);
		mainPanel.add(passwordLabel);
		
		userTypeLabel=new JLabel("I am");
		userTypeLabel.setBounds(X+5, Y*3, WIDTH, HEIGHT);
		mainPanel.add(userTypeLabel);
	}
	private void showTextFields() {
		userName = new JTextField();
		userName.setBounds(X+100, Y, WIDTH, HEIGHT);
		mainPanel.add(userName);		
		
		password = new JTextField();
		password.setBounds(X+100, Y*2,WIDTH, HEIGHT);
		mainPanel.add(password);		
	}
	private void selectUserType() {
		userTypeCombo = new JComboBox(userTypes);
		userTypeCombo.setBounds(X+100, Y*3, WIDTH, HEIGHT);
		userTypeCombo.setSelectedIndex(0);
		//userTypeCombo.addActionListener(this);
		mainPanel.add(userTypeCombo);
	}
	private void showLoginButton() {
		loginButton = new JButton("Login");
		loginButton.setBounds(X+100,Y*4,WIDTH, HEIGHT);
		loginButton.addActionListener(this);
		mainPanel.add(loginButton);	
	}
	
	private void createClientPanel() {
		clientPanel=initializePanel(clientPanel);
		showSearchTextField('C');
		showSearchButton('C');
		showLogoutButton('C');
		displayAllNutritionist('C');
		
		clientPanel.setVisible(false);
		this.add(clientPanel);
	}
	private void showSearchTextField(char z) {
		nutriSearch= new JTextField();
		nutriSearch.setBounds(X, Y+Y, WIDTH+60, HEIGHT);
		if(z=='C')
		{
			clientPanel.add(nutriSearch);
		}
		else
		{
			nutritionistPanel.add(nutriSearch);
		}
	}
	private void showSearchButton(char z) {
		if(z=='C')
		{
			nutriSearchButton = new JButton("Search Nutritioninst");
			nutriSearchButton.setBounds(X+WIDTH+70, Y+Y, WIDTH+50, HEIGHT);
			nutriSearchButton.addActionListener(this);
			clientPanel.add(nutriSearchButton);
		}
		if(z=='N')
		{
			clientSearchButton = new JButton("Search Client");
			clientSearchButton.setBounds(X+WIDTH+70, Y+Y, WIDTH+50, HEIGHT);
			clientSearchButton.addActionListener(this);
			nutritionistPanel.add(clientSearchButton);
		}
	}
	private void showLogoutButton(char z) {
		logoutButton = new JButton("Logout");
		logoutButton.setBounds(X*10, Y-20, WIDTH-20, HEIGHT);
		logoutButton.addActionListener(this);
		
		if(z=='C')
		{ 		
			clientPanel.add(logoutButton); 
		}		 
		if(z=='N')
		{			
			nutritionistPanel.add(logoutButton);
		}
		if(z=='A')
		{
			adminPanel.add(logoutButton);
		}
	}
	private void displayAllNutritionist(char z) {
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(columnNames);
		table = getNewTable(model);
		JScrollPane scroll = getNewJScrollPane();		
		Connection con = DB.getConnection();	
		getAllNutritionist(con,model,scroll,z);		
	}
	private JTable getNewTable(DefaultTableModel model) {
		table=new JTable(model);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setFillsViewportHeight(true);
		table.setShowGrid(false);
		return table;
	}
	private JScrollPane getNewJScrollPane() {
		
		JScrollPane scroll=new JScrollPane(table);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setBorder(BorderFactory.createEmptyBorder());
		return scroll;
	}
	private void getAllNutritionist(Connection con, DefaultTableModel model, JScrollPane scroll, char z) {
		try
		{
			PreparedStatement ps = con.prepareStatement("Select * from nutri");
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				String name=rs.getString(2);
				String email=rs.getString(5);				
				model.addRow(new Object[] {name,email});
				displayNutriLabel = new JLabel("Displaying list of all Nutritioninst");
				displayNutriLabel.setBounds(X, Y*3, WIDTH*2, HEIGHT);
				scroll.setBounds( X, Y*4, WIDTH*3+20, HEIGHT*3 );
				if(z=='C')
				{
					clientPanel.add(displayNutriLabel);
					clientPanel.add(scroll);
				}
				else if(z=='N')
				{
					allNutriPanel.add(displayNutriLabel);
					allNutriPanel.add(scroll);				   
				}					
			}
			con.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}		
	}
	private void createAdminPanel() {
		adminPanel=initializePanel(adminPanel);
		showAllNutritionistButton();
		showAddNewNutritionistButton();
		showRemoveNutritionistButton();
		showAllClientsButton();		
		showAddNewClientButton();
		showRemoveClientButton();
		showLogoutButton('A');
		adminPanel.setVisible(false);
		this.add(adminPanel);
	}
	private void showAllNutritionistButton() {
		showAllNutri = new JButton("Show All Nutritionists");
		showAllNutri.setBounds(X, Y, WIDTH*2, HEIGHT);
		showAllNutri.addActionListener(this);
		adminPanel.add(showAllNutri);			
	}
	private void showAddNewNutritionistButton() {
		addNutri = new JButton("Add New Nutritionist");
		addNutri.setBounds(X, Y*2, WIDTH*2, HEIGHT);
		addNutri.addActionListener(this);
		adminPanel.add(addNutri);			
	}
	private void showRemoveNutritionistButton() {
		removeNutri = new JButton("Remove Nutritionist");
		removeNutri.setBounds(X, Y*3, WIDTH*2, HEIGHT);
		removeNutri.addActionListener(this);
		adminPanel.add(removeNutri);		
	}
	private void showAllClientsButton() {
		showAllClients = new JButton("Show All Clients");
		showAllClients.setBounds(X, Y*4, WIDTH*2, HEIGHT);
		showAllClients.addActionListener(this);
		adminPanel.add(showAllClients);			
	}
	private void showAddNewClientButton() {
		addClient = new JButton("Add New Client");
		addClient.setBounds(X, Y*5, WIDTH*2, HEIGHT);
		addClient.addActionListener(this);
		adminPanel.add(addClient);		
	}
	private void showRemoveClientButton() {
		removeClient = new JButton("Remove Client");
		removeClient.setBounds(X, Y*6, WIDTH*2, HEIGHT);
		removeClient.addActionListener(this);
		adminPanel.add(removeClient);
	}
	private void createNutriPanel() {
		nutritionistPanel=initializePanel(nutritionistPanel);
		showSearchTextField('N');
		showSearchButton('N');
		showLogoutButton('N');
		nutritionistPanel.setVisible(false);
		this.add(nutritionistPanel);
	}
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == loginButton){			
			if(userTypeCombo.getSelectedItem().equals("Client"))
			{
				createClientPanel();
				showClientView();	
			}
			if(userTypeCombo.getSelectedItem().equals("Nutritionist"))
			{
				createNutriPanel();
				showNutritionistView();
			}
			if(userTypeCombo.getSelectedItem().equals("Admin"))
			{
				createAllNutriPanel();
				createAdminPanel();
				showAdminView();
			}
		}
		if(((JButton)arg0.getSource()).getText().equals("Logout"))
		{
			if(clientPanel!=null)
			{
				clientPanel.setVisible(false);
			}
			if(adminPanel!=null)
			{
				adminPanel.setVisible(false);
			}
			if(allNutriPanel!=null)
			{
				allNutriPanel.setVisible(false);
			}
			userNameLabel.setText("User Name*");
			userNameLabel.setBounds(X+5, Y, WIDTH, HEIGHT);
			mainPanel.add(userNameLabel);
			mainPanel.setVisible(true);
		}
		if(((JButton)arg0.getSource()).getText().equals("Show All Nutritionists"))
		{
			adminPanel.setVisible(false);
			allNutriPanel.add(logoutButton);
			allNutriPanel.setVisible(true);
		}
		if(((JButton)arg0.getSource()).getText().equals("Add New Nutritionist"))
		{
			createAddNewNutriPanel();
			adminPanel.setVisible(false);
			addNewNutriPanel.setVisible(true);
		}
		if(((JButton)arg0.getSource()).getText().equals("Home"))
		{
				allNutriPanel.setVisible(false);
				adminPanel.setVisible(true);
		}
	}
	private void showClientView() {
		Connection con = DB.getConnection();
		if(!userExists(con,'C'))
		{
			JOptionPane.showMessageDialog(this, "Enter correct username\n" , " ",JOptionPane.INFORMATION_MESSAGE); 
		}
	}
	private void giveAccessToClientPanel(String name) {
		welcomeClient(name);		
		clientPanel.setVisible(true);		
	}
	private void welcomeClient(String name) {
		userNameLabel.setBounds(X*5, Y-20, WIDTH*2, HEIGHT);
		userNameLabel.setText("Welcome "+name);
		clientPanel.add(userNameLabel);		
	}
	private void showNutritionistView() {
		Connection con = DB.getConnection();
			 if(!userExists(con,'N'))
			 {
				JOptionPane.showMessageDialog(this, "Enter correct username\n" , " ",JOptionPane.INFORMATION_MESSAGE); 
			 }
	}
	private boolean userExists(Connection con, char z) {
		PreparedStatement ps;
		try {
			if(z == 'N')
			{
				ps = con.prepareStatement("Select * from nutri");
			}
			else
			{
				ps = con.prepareStatement("Select * from cli");
			}
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				if(rs.getString(3).equals(userName.getText()))
				{
					mainPanel.setVisible(false);
					if(z == 'N')
					{
						giveAccessToNutritionistPanel(rs.getString(2));							
					}
					else
					{
						giveAccessToClientPanel(rs.getString(2));
						
					}
					return true;
				}					
			}			
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return false;
	}
	private void giveAccessToNutritionistPanel(String name) {
		welcomeNutritionist(name);		
		nutritionistPanel.setVisible(true);	
	}
	private void welcomeNutritionist(String name) {
		userNameLabel.setBounds(X*5, Y-20, WIDTH*2, HEIGHT);
		userNameLabel.setText("Welcome "+name);
		nutritionistPanel.add(userNameLabel);	
		displayMyClientsOnly();		
	}
	private void displayMyClientsOnly() {
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(columnNames);
		table = getNewTable(model);				
		JScrollPane scroll = getNewJScrollPane();				
		Connection con = DB.getConnection();		 
		int	id=getMyId(con);
		getMyClients(con,id,model,scroll);
	}
	private int getMyId(Connection con) {
		int id=0;
		try
		{
			Statement s = (Statement) con.createStatement();
			ResultSet rs = s.executeQuery("select idNutri from nutri where nutriUname='"+userName.getText()+"'");
	 	
			while(rs.next()){
				id=Integer.parseInt(rs.getString(1));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return id;
	}
	private void getMyClients(Connection con, int id,DefaultTableModel model,JScrollPane scroll) {
		try
		{
			PreparedStatement ps1 = con.prepareStatement("Select * from cli where idNutri=?");
			ps1.setObject(1,id);
			ResultSet rs1 = ps1.executeQuery();
			while(rs1.next()){
				String name1=rs1.getString(2);
				String email=rs1.getString(5);				
				model.addRow(new Object[] {name1,email});
				displayNutriLabel = new JLabel("Displaying list of all Clients enrolled with you");
				displayNutriLabel.setBounds(X, Y*3, WIDTH*4, HEIGHT);
				nutritionistPanel.add(displayNutriLabel);
				scroll.setBounds( X, Y*4, WIDTH*3+20, HEIGHT*3 );
				nutritionistPanel.add(scroll);
			}
			con.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}		
	}
	private void showAdminView() {
		mainPanel.setVisible(false);
		//welcomeAdmin(name);		
		adminPanel.setVisible(true);	
	}	
}