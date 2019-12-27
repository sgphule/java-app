/*
 * Project NutriSpec
 * Author: Sudarshan Phule
 * Date: 21-12-2019
 */
package com.clientUI;
import com.server.*;
import com.nutritionist.*;
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

import com.client.Client;
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
	JLabel displayClientLabel;
	JLabel userTypeLabel;
	
	JLabel idClientLabel;
	
	JLabel fullNameClientLabel;
	JLabel userNameClientLabel;
	JLabel passwordClientLabel;
	JLabel emailIdClientLabel;
	JLabel idNutriLabel;
	
	JTextField idTextField;
	JTextField fullNameTextField;
	JTextField userName;
	JTextField password;
	JTextField emailIdTextField;
	
	JTextField idClientTextField;
	JTextField fullNameClientTextField;
	JTextField userNameClientTextField;
	JTextField passwordClientTextField;
	JTextField emailIdClientTextField;
	JTextField idNutriTextField;
	

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
	JButton addNewNutri;
	JButton addNewClient;
	JButton removeNutriButton;
	JButton removeClientButton;

	JComboBox userTypeCombo;
	
	JTable table;
	String[] columnNames = {"Name", "Contact Email"};
	String[] userTypes = {"Client","Nutritionist","Admin"};
	int X=30;
	int Y=30;
	int WIDTH=100;
	int HEIGHT=25;	
	DataOutputStream dout = null;
	DataInputStream din = null;
	ObjectOutputStream oout = null;
	ObjectInputStream oin = null;
	Socket cs = null;
	ArrayList<Nutritionist> alNutri = new ArrayList<Nutritionist>();
	ArrayList<Nutritionist> alClient;
	public static void main(String[] args) {
		Home obj = new Home();
		obj.initialize();
		obj.myFrame();
	}
	public void myFrame(){
		setSize(400, 400);
		setLocationRelativeTo(null);
		setLayout(null);
		
		createMainPanel();
		
		setVisible(true);
		setTitle("NutriSpec Application");
	}	
	private void createRemoveNutriPanel()
	{
		System.out.println("1");
		removeNutriPanel=initializePanel(removeNutriPanel);
		System.out.println("2");
		idLabel= new JLabel("ID*");
		idLabel.setBounds(X, Y*2, WIDTH, HEIGHT);
		removeNutriPanel.add(idLabel);
		
		idTextField= new JTextField();
		idTextField.setBounds(X*4, Y*2, WIDTH*2, HEIGHT);
		removeNutriPanel.add(idTextField);	
		
		removeNutriButton= new JButton("Remove");
		removeNutriButton.setBounds(X*3, Y*8, WIDTH*2, HEIGHT);
		removeNutriButton.addActionListener(this);
		removeNutriPanel.add(removeNutriButton);	
		
		removeNutriPanel.add(homeButton);
		removeNutriPanel.add(logoutButton);
		removeNutriPanel.setVisible(false);
		add(removeNutriPanel);	
		
	}
	private void createRemoveClientPanel()
	{
		removeClientPanel=initializePanel(removeClientPanel);
		idClientLabel= new JLabel("Client ID*");
		idClientLabel.setBounds(X, Y*2, WIDTH, HEIGHT);
		removeClientPanel.add(idClientLabel);
		
		idClientTextField= new JTextField();
		idClientTextField.setBounds(X*4, Y*2, WIDTH*2, HEIGHT);
		removeClientPanel.add(idClientTextField);	
		
		removeClientButton= new JButton("Delete this Client");
		removeClientButton.setBounds(X*3, Y*8, WIDTH*2, HEIGHT);
		removeClientButton.addActionListener(this);
		removeClientPanel.add(removeClientButton);	
		
		removeClientPanel.add(homeButton);
		removeClientPanel.add(logoutButton);
		removeClientPanel.setVisible(false);
		add(removeClientPanel);	
		
	}
	private void createAddNewNutriPanel() {
		addNewNutriPanel=initializePanel(addNewNutriPanel);
		showAllLabels();
		showAllTextFields();
		//showSubmitButton();
		showAddNutriButton();
		addNewNutriPanel.add(homeButton);
		addNewNutriPanel.add(logoutButton);
		addNewNutriPanel.setVisible(false);
		add(addNewNutriPanel);
	}
	private void createAddNewClientPanel() {
		addNewClientPanel=initializePanel(addNewClientPanel);
		showAllClientLabels();
		showAllClientTextFields();
		//showSubmitButton();
		showAddClientButton();
		addNewClientPanel.add(homeButton);
		addNewClientPanel.add(logoutButton);
		addNewClientPanel.setVisible(false);
		add(addNewClientPanel);
	}
	private void showAllClientTextFields() {
		idClientTextField= new JTextField();
		idClientTextField.setBounds(X*4, Y*2, WIDTH*2, HEIGHT);
		addNewClientPanel.add(idClientTextField);		
		fullNameClientTextField= new JTextField();
		fullNameClientTextField.setBounds(X*4, Y*3, WIDTH*2, HEIGHT);
		addNewClientPanel.add(fullNameClientTextField);
		userNameClientTextField = new JTextField();
		userNameClientTextField.setBounds(X*4, Y*4, WIDTH*2, HEIGHT);
		addNewClientPanel.add(userNameClientTextField);	
		passwordClientTextField= new JTextField();
		passwordClientTextField.setBounds(X*4, Y*5, WIDTH*2, HEIGHT);
		addNewClientPanel.add(passwordClientTextField);	
		emailIdClientTextField = new JTextField();
		emailIdClientTextField.setBounds(X*4, Y*6, WIDTH*2, HEIGHT);
		addNewClientPanel.add(emailIdClientTextField);	
		idNutriTextField= new JTextField();
		idNutriTextField.setBounds(X*4, Y*7, WIDTH*2, HEIGHT);
		addNewClientPanel.add(idNutriTextField);	
		
	}
	private void showAllClientLabels() {
		idClientLabel= new JLabel("ID*");
		idClientLabel.setBounds(X, Y*2, WIDTH, HEIGHT);
		addNewClientPanel.add(idClientLabel);
		fullNameClientLabel= new JLabel("Full Name*");
		fullNameClientLabel.setBounds(X, Y*3, WIDTH, HEIGHT);
		addNewClientPanel.add(fullNameClientLabel);		
		userNameClientLabel= new JLabel("User Name*");
		userNameClientLabel.setBounds(X, Y*4, WIDTH, HEIGHT);
		addNewClientPanel.add(userNameClientLabel);
		passwordClientLabel= new JLabel("Password*");
		passwordClientLabel.setBounds(X, Y*5, WIDTH, HEIGHT);
		addNewClientPanel.add(passwordClientLabel);
		emailIdClientLabel= new JLabel("Email ID*");
		emailIdClientLabel.setBounds(X, Y*6, WIDTH, HEIGHT);
		addNewClientPanel.add(emailIdClientLabel);
		idNutriLabel =new JLabel("Nutritionist ID ");
		idNutriLabel.setBounds(X, Y*7, WIDTH, HEIGHT);
		addNewClientPanel.add(idNutriLabel);
		
	}
	private void showAddNutriButton()
	{	
		addNewNutri= new JButton("Add Nutritionist");
		addNewNutri.setBounds(X*4, Y*8, WIDTH*2, HEIGHT);
		addNewNutri.addActionListener(this);
		addNewNutriPanel.add(addNewNutri);	
	}
	private void showAddClientButton()
	{	
		addNewClient= new JButton("Add Client");
		addNewClient.setBounds(X*4, Y*8, WIDTH*2, HEIGHT);
		addNewClient.addActionListener(this);
		addNewClientPanel.add(addNewClient);	
	}

	/*
	 * 
	 * private void showSubmitButton() { addNutri.setText("Add Nutritionist");
	 * addNutri.setBounds(X*3, Y*8, WIDTH*2, HEIGHT);
	 * addNutri.addActionListener(this); addNewNutriPanel.add(addNutri); }
	 */
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
	private void createAllClientPanel() {
		allClientPanel=initializePanel(allClientPanel);
		showHomeButton();
		displayAllClients();
		//getMyClients(con, id, model, scroll);
		allClientPanel.setVisible(false);
		add(allClientPanel);
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
		try {
			cs = new Socket("localhost", 5000);
			dout = new DataOutputStream(cs.getOutputStream());
			din = new DataInputStream(cs.getInputStream());

			oout = new ObjectOutputStream(
					cs.getOutputStream());
			oin = new ObjectInputStream(cs.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
			dout.writeInt(1);		
			ArrayList<Nutritionist> alNutri = (ArrayList<Nutritionist>) oin.readObject();
			for(Nutritionist nt : alNutri){
				model.addRow(new Object[] {nt.nameNutri,nt.emailNutri});
				displayNutriLabel = new JLabel("Displaying list of all Nutritioninst");
				displayNutriLabel.setBounds(X, Y*3, WIDTH*2, HEIGHT);
				scroll.setBounds( X, Y*4, WIDTH*3+20, HEIGHT*3 );
			}
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
			if(addNewClientPanel!=null)
			{
				addNewClientPanel.setVisible(false);
			}
			if(clientPanel!=null)
			{
				clientPanel.setVisible(false);
			}
			if(nutritionistPanel!=null)
			{
				nutritionistPanel.setVisible(false);
			}
			if(adminPanel!=null)
			{
				adminPanel.setVisible(false);
			}
			if(allNutriPanel!=null)
			{
				allNutriPanel.setVisible(false);
			}
			if(addNewNutriPanel!=null)
			{
				addNewNutriPanel.setVisible(false);
			}
			if(removeNutriPanel!=null)
			{
				removeNutriPanel.setVisible(false);
			}
			if(removeClientPanel!=null)
			{
				removeClientPanel.setVisible(false);
			}
			if(allClientPanel!=null)
			{
				allClientPanel.setVisible(false);
			}
			userNameLabel.setText("User Name*");
			userNameLabel.setBounds(X+5, Y, WIDTH, HEIGHT);
			mainPanel.add(userNameLabel);
			mainPanel.setVisible(true);
		}
		if(((JButton)arg0.getSource()).getText().equals("Show All Nutritionists"))
		{
			adminPanel.setVisible(false);
			createAllNutriPanel();
			allNutriPanel.add(logoutButton);
			allNutriPanel.setVisible(true);
		}
		if(((JButton)arg0.getSource()).getText().equals("Show All Clients"))
		{
			adminPanel.setVisible(false);
			createAllClientPanel();
			allClientPanel.add(logoutButton);
			allClientPanel.add(homeButton);
			allClientPanel.setVisible(true);
		}
		
		if(((JButton)arg0.getSource()).getText().equals("Home"))
		{
				allNutriPanel.setVisible(false);
				if(addNewClientPanel!=null)
				{
					addNewClientPanel.setVisible(false);
				}
				if(allClientPanel!=null)
				{
					allClientPanel.setVisible(false);
				}
				if(addNewNutriPanel!=null)
				{
					addNewNutriPanel.setVisible(false);
				}
				if(removeNutriPanel!=null)
				{
					removeNutriPanel.setVisible(false);
				}
				if(removeClientPanel!=null)
				{
					removeClientPanel.setVisible(false);
				}
				adminPanel.add(logoutButton);
				adminPanel.setVisible(true);
		}
		if(((JButton)arg0.getSource()).getText().equals("Add New Nutritionist"))
		{
			createAddNewNutriPanel();
			adminPanel.setVisible(false);
			addNewNutriPanel.setVisible(true);
		}
		if(((JButton)arg0.getSource()).getText().equals("Add New Client"))
		{
			createAddNewClientPanel();
			adminPanel.setVisible(false);
			addNewClientPanel.setVisible(true);
		}
		if(((JButton)arg0.getSource()).getText().equals("Add Nutritionist"))
		{
			try {
				dout.writeInt(2);				
				Nutritionist nt = null;
				int id = Integer.parseInt(idTextField.getText());
				String fName = fullNameTextField.getText();
				String uName = userName.getText();
				String pwd = password.getText();
				String email=emailIdTextField.getText();
				nt = new Nutritionist(id,fName,uName,pwd,email);
				oout.writeObject(nt);
				ArrayList<Nutritionist> al = (ArrayList<Nutritionist>) oin.readObject();
				String print = ""; for(Nutritionist n : al){ print = print + "\n" + n.idNutri + " -:- " + n.nameNutri + " -:- " + n.unameNutri; }
				JOptionPane.showMessageDialog(this, "Added Successfully !!!\n" + print,"Current Nutritionist List", JOptionPane.INFORMATION_MESSAGE);
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		if(((JButton)arg0.getSource()).getText().equals("Add Client"))
		{
			try {
				dout.writeInt(5);				
				Client cl = null;
				int id = Integer.parseInt(idClientTextField.getText());
				String fName = fullNameClientTextField.getText();
				String uName = userNameClientTextField.getText();
				String pwd = passwordClientTextField.getText();
				String email=emailIdClientTextField.getText();
				int idNutri = Integer.parseInt(idNutriTextField.getText());
				cl = new Client(id,fName,uName,pwd,email,idNutri);
				oout.writeObject(cl);
				ArrayList<Client> al = (ArrayList<Client>) oin.readObject();
				String print = ""; for(Client c : al){ print = print + "\n" + c.idCli + " -:- " + c.nameCli + " -:- " + c.unameCli; }
				JOptionPane.showMessageDialog(this, "Added Successfully !!!\n" + print,"Current Clients List", JOptionPane.INFORMATION_MESSAGE);
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		if(((JButton)arg0.getSource()).getText().equals("Remove Nutritionist"))
		{
			createRemoveNutriPanel();
			adminPanel.setVisible(false);
			removeNutriPanel.setVisible(true);
		}
		if(((JButton)arg0.getSource()).getText().equals("Remove Client"))
		{
			createRemoveClientPanel();
			adminPanel.setVisible(false);
			removeClientPanel.setVisible(true);
		}
		
		if(((JButton)arg0.getSource()).getText().equals("Delete this Client"))
		{
			try {
				dout.writeInt(6);				
				Client cl = null;
				int id = Integer.parseInt(idClientTextField.getText());
				dout.writeInt(id);
				ArrayList<Client> al = (ArrayList<Client>) oin.readObject();
				String print = ""; 
				for(Client c : al){ 
					print = print + "\n" + c.idCli + " -:- " + c.nameCli + " -:- " + c.unameCli; 
				}
				JOptionPane.showMessageDialog(this, "Deleted Successfully !!!\n" + print,"Current Client List", JOptionPane.INFORMATION_MESSAGE);
			} catch (IOException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			if(((JButton)arg0.getSource()).getText().equals("Remove"))
			{
				try {
					dout.writeInt(3);				
					Nutritionist nt = null;
					int id = Integer.parseInt(idTextField.getText());
					System.out.println("id:"+id);
					dout.writeInt(id);
					ArrayList<Nutritionist> al = (ArrayList<Nutritionist>) oin.readObject();
					String print = ""; for(Nutritionist s : al){ print = print + "\n" + s.idNutri + " -:- " + s.nameNutri + " -:- " + s.unameNutri; }
					JOptionPane.showMessageDialog(this, "Deleted Successfully !!!\n" + print,"Current Nutritionist List", JOptionPane.INFORMATION_MESSAGE);
					 
					//adminPanel.setVisible(true);
					//addNewNutriPanel.setVisible(false);
				} catch (IOException | ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
	private void displayAllClients()
	{
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(columnNames);
		table = getNewTable(model);
		JScrollPane scroll = getNewJScrollPane();		
		Connection con = DB.getConnection();	
		try
		{
			dout.writeInt(4);		
			ArrayList<Client> alClient = (ArrayList<Client>) oin.readObject();
			displayClientLabel = new JLabel("Displaying list of all Clients");
			displayClientLabel.setBounds(X, Y*3, WIDTH*2, HEIGHT);
			scroll.setBounds( X, Y*4, WIDTH*3+20, HEIGHT*3 );
			for(Client cl : alClient){
				model.addRow(new Object[] {cl.nameCli,cl.emailCli});
				
			}
			allClientPanel.add(displayClientLabel);
			allClientPanel.add(scroll);			
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