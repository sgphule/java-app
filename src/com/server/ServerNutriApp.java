package com.server;
import com.client.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.nutritionist.*;
import com.client.*;

public class ServerNutriApp {
	int port;
	ServerSocket ss = null;
	Socket cs = null;
	//OperationsClient obj = null;
	OperationsNutri obj=null;
	OperationsClient objCli=null;
	ExecutorService pool = null;
	
	public ServerNutriApp(int port){
		this.port = port;
		obj = new OperationsNutri();
		objCli = new OperationsClient();
		pool = Executors.newFixedThreadPool(5);
	}
	
	public void startServer(){
		try {
			//creating one server socket 
			ss = new ServerSocket(5000);
			//for accepting multiple clients
			while(true){
				System.out.println("Server waiting for client....");
				cs = ss.accept();
				
				System.out.println("Got one client.Creating thread for this client...");
				ServerThreadBody runnable = new ServerThreadBody(cs, obj, objCli, this);
				//assigning thread to pool
				pool.execute(runnable);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new ServerNutriApp(5000).startServer();
	}
}
class ServerThreadBody implements Runnable{
	ServerNutriApp server = null;
	Socket cs = null;
	DataOutputStream dout = null;
	DataInputStream din = null;
	ObjectOutputStream oout = null;
	ObjectInputStream oin = null;
	Client cl = null;
	Nutritionist nt=null;
	OperationsNutri obj = null;
	OperationsClient objCli = null;
	
	ServerThreadBody(Socket cs, OperationsNutri obj, OperationsClient objCli, ServerNutriApp server) {
		// TODO Auto-generated constructor stub
		this.cs = cs;
		this.obj = obj;
		this.objCli = objCli;
		this.server = server;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
			dout = new DataOutputStream(cs.getOutputStream());
			din = new DataInputStream(cs.getInputStream());
			
			oout = new ObjectOutputStream(
					cs.getOutputStream());
			oin = new ObjectInputStream(cs.getInputStream());
			
			
	
			while (true) {
	
				int ch = din.readInt();
				if (ch == 2) {
						nt = (Nutritionist) oin.readObject();
						obj.addNutri(nt);
						oout.writeObject(obj.display());
					
				} 
				else if (ch == 3) {
					 int idNutri = din.readInt();
					 obj.deleteNutri(idNutri); 
					 oout.writeObject(obj.display()); 
				}
				else if (ch == 0)
				{					  
					int idNutri = din.readInt();					  
					nt = obj.searchNutritionist(idNutri); 
					if (nt != null) {					  
						  oout.writeObject(nt); 
					} 
					else
					{					  
						oout.writeObject(null); 
					} 
				} 
				else if (ch == 1) 
				{
					  oout.writeObject(obj.display()); 
				}
				else if (ch == 4)
				{
					oout.writeObject(objCli.display()); 
				}
				else if(ch == 5)
				{
					cl = (Client) oin.readObject();
					objCli.addClient(cl);
					oout.writeObject(objCli.display());
				}
				else if (ch == 6) {
					 int idCli = din.readInt();
					 objCli.deleteClient(idCli); 
					 oout.writeObject(objCli.display()); 
				}
				/*else if(ch == 6)
				{
					  dout.writeUTF("Bye Bye Client from server!!! ");
					  System.out.println("Socket Closed !!!"); cs.close(); break; 
				}*/
					 
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
