package com.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.client.*;

public class OperationsClient {

	int addClient(Client cl) {
		int rowsInserted = 0;
		
	    Connection con = DB.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("insert into Cli(idCli, nameCli, uname, pwd, cliEmail, idNutri) values(?,?,?,?,?,?)");
			ps.setInt(1, cl.idCli);
			ps.setString(2, cl.nameCli);
			ps.setString(3, cl.unameCli);
			ps.setString(4, cl.pwdCli);
			ps.setString(5, cl.emailCli);
			ps.setInt(6, cl.idNutri);
			
			
			
			rowsInserted = ps.executeUpdate();
			
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rowsInserted;
	}
	
	int deleteClient(int idCli) {
		int rowsDeleted = 0;
		
		Connection con = DB.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("delete from Cli where idCli = ?");
			ps.setInt(1, idCli);
			
			rowsDeleted = ps.executeUpdate();
			
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return rowsDeleted;
	}
	
	Client searchClient(int idCli){
		Client cl = null;
		
		Connection con = DB.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("Select * from Client where idCli = ?");
			ps.setInt(1, idCli);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				cl = new Client(rs.getInt("idCli"), rs.getString("nameCli"), rs.getString("uname"),rs.getString("pwd"),rs.getString("cliEmail"),rs.getInt("idNutri"));
			}
			
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return cl;
	}
	ArrayList<Client> display(){
		ArrayList<Client> allData = new ArrayList<Client>();
		
		Connection con = DB.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("Select * from cli");
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				Client cl = new Client(rs.getInt("idCli"), rs.getString("nameCli"), rs.getString("uname"),rs.getString("pwd"),rs.getString("cliEmail"),rs.getInt("idNutri"));

				allData.add(cl);
			}
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return allData;
	}

}
