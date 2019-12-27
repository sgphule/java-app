package com.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.client.*;
import com.nutritionist.*;

public class OperationsNutri {
	int addNutri(Nutritionist nt) {
		int rowsInserted = 0;
		
	    Connection con = DB.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("insert into nutri(idNutri, nameNutri, nutriUname, nutriPwd, nutriEmail) values(?,?,?,?,?)");
			ps.setInt(1, nt.idNutri);
			ps.setString(2, nt.nameNutri);
			ps.setString(3, nt.unameNutri);
			ps.setString(4, nt.pwdNutri);
			ps.setString(5, nt.emailNutri);
			
			//fName,uName,pwd,email
			
			rowsInserted = ps.executeUpdate();
			
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rowsInserted;
	}
	ArrayList<Nutritionist> display(){
		ArrayList<Nutritionist> allData = new ArrayList<Nutritionist>();
		
		Connection con = DB.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("Select * from nutri");
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				Nutritionist nt = new Nutritionist(rs.getInt("idNutri"), rs.getString("nameNutri"), rs.getString("nutriUname"),rs.getString("nutriPwd"),rs.getString("nutriEmail"));

				allData.add(nt);
			}
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return allData;
	}
	int deleteNutri(int idNutri) {
		int rowsDeleted = 0;
		Connection con = DB.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("delete from nutri where idNutri = ?");
			ps.setInt(1, idNutri);
			
			rowsDeleted = ps.executeUpdate();
			
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		System.out.println("rowsDeleted:"+rowsDeleted);
		return rowsDeleted;
		
	}
	public Nutritionist searchNutritionist(int idNutri) {
		Nutritionist nt = null;
		
		Connection con = DB.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("Select * from nutri where idNutri = ?");
			ps.setInt(1, idNutri);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				nt = new Nutritionist(rs.getInt("idNutri"), rs.getString("nameNutri"), rs.getString("nutriUname"),rs.getString("nutriPwd"),rs.getString("nutriEmail"));
			}
			
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nt;
	}
}
