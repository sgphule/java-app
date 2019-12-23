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
	
	JLabel userNameLabel;
	JLabel passwordLabel;
	JLabel displayNutriLabel;
	JLabel userTypeLabel;
	
	JTextField userName;
	JTextField password;
	JTextField nutriSearch;
	JButton loginButton;
	JButton nutriSearchButton;
	JButton clientSearchButton;
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
		createClientPanel();
		createAdminPanel();
		createNutriPanel();
		setVisible(true);
		setTitle("NutriSpec Application");
	}	
	private void initialize() {
		setSize(400, 400);
		setLocationRelativeTo(null);
		setLayout(null);		
	}
	private void createMainPanel() {
		initializeMainPanel();
		showLabels();
		showTextFields();
		selectUserType();
		showLoginButton();		
		mainPanel.setVisible(true); 
		add(mainPanel);
	}
	private void initializeMainPanel() {
		mainPanel = new JPanel(); 		
		mainPanel.setBounds(0, 0, 400, 400);
		mainPanel.setLayout(null);		
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
		initializeClientPanel();
		//showUserName('C');
		showSearchTextField('C');
		showSearchButton('C');
		displayAllNutritionist();
		
		clientPanel.setVisible(false);
		this.add(clientPanel);
	}
	private void initializeClientPanel() {
		clientPanel =new JPanel();
		clientPanel.setBounds(0, 0, 400, 400);
		clientPanel.setLayout(null);		
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
	private void displayAllNutritionist() {
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(columnNames);
		table = getNewTable(model);
		JScrollPane scroll = getNewJScrollPane();		
		Connection con = DB.getConnection();		
		getAllNutritionist(con,model,scroll);		
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
	private void getAllNutritionist(Connection con, DefaultTableModel model, JScrollPane scroll) {
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
				clientPanel.add(displayNutriLabel);
				scroll.setBounds( X, Y*4, WIDTH*3+20, HEIGHT*3 );
				clientPanel.add(scroll);
			}
			con.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	private void createAdminPanel() {
		initializeAdminPanel();
		
		showAllNutritionistButton();
		showAddNewNutritionistButton();
		showRemoveNutritionistButton();
		
		showAllClientsButton();		
		showAddNewClientButton();
		showRemoveClientButton();
			
		adminPanel.setVisible(false);
		this.add(adminPanel);
		
	}
	private void initializeAdminPanel() {
		adminPanel =new JPanel();
		adminPanel.setBounds(0, 0, 400, 400);
		adminPanel.setLayout(null);				
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
		initializeNutriPanel();
		showSearchTextField('N');
		showSearchButton('N');
		
		nutritionistPanel.setVisible(false);
		this.add(nutritionistPanel);
	}
	private void initializeNutriPanel() {
		nutritionistPanel =new JPanel();
		nutritionistPanel.setBounds(0, 0, 400, 400);
		nutritionistPanel.setLayout(null);		
		
	}
	
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == loginButton){
			if(userTypeCombo.getSelectedItem().equals("Client"))
			{
				System.out.println("I am in clientView");
				showClientView();			 
			}
			if(userTypeCombo.getSelectedItem().equals("Nutritionist"))
			{
				System.out.println("I am in Nutritionist View");
				showNutritionistView();
			}
			if(userTypeCombo.getSelectedItem().equals("Admin"))
			{
				System.out.println("I am in Admin View");
				showAdminView();
			}
		}
		/*
		 * else { mainPanel.setVisible(true); clientPanel.setVisible(false); }
		 */
	}
	
	private void showClientView() {
		Connection con = DB.getConnection();
		if(!userExists(con,'C'))
		{
			JOptionPane.showMessageDialog(this, "Enter correct username\n" , " ",JOptionPane.INFORMATION_MESSAGE); 
		}
		/*
		 * try { PreparedStatement ps = con.prepareStatement("Select * from cli");
		 * ResultSet rs = ps.executeQuery(); while(rs.next()) {
		 * if(rs.getString(3).equals(userName.getText())) { mainPanel.setVisible(false);
		 * adminPanel.setVisible(false); nutritionistPanel.setVisible(false);
		 * giveAccessToClientPanel(rs.getString(2)); } else {
		 * JOptionPane.showMessageDialog(this, "Enter correct username\n" ,
		 * " ",JOptionPane.INFORMATION_MESSAGE); } } con.close(); } catch (SQLException
		 * e) { e.printStackTrace(); }
		 */	
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
		userNameLabel.setBounds(X, Y-20, WIDTH*2, HEIGHT);
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
				//System.out.println("name1:"+name1);
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
		/*
		 * Connection con = DB.getConnection();
		 * 
		 * if(!userExists(con)) { JOptionPane.showMessageDialog(this,
		 * "Enter correct username\n" , " ",JOptionPane.INFORMATION_MESSAGE); }
		 */
		mainPanel.setVisible(false);
		//welcomeAdmin(name);		
		adminPanel.setVisible(true);	
		
	}	
	
	/*
	 * private void showUserName(char z) {
	 * System.out.println("I am not coming here?"); if(!mainPanel.isVisible() &&
	 * z=='C') { System.out.println("Hello Am I coming here?");
	 * userNameLabel.setText("Welcome "+userName); clientPanel.add(userNameLabel);
	 * add(clientPanel); System.out.println("Hello"); } if(!mainPanel.isVisible() &&
	 * z=='N') { System.out.println("Hello Yaar");
	 * userNameLabel.setText("Welcome1 "+userName);
	 * nutritionistPanel.add(userNameLabel); System.out.println("Hello Yaar");
	 * System.out.println("namNutri1:"+userName.getText());
	 * 
	 * } }
	 */
}