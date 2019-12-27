package com.client;

import java.io.Serializable;

public class Client implements Serializable {
	public int idCli;
	public String nameCli;
	public String unameCli;
	public String pwdCli;
	public String emailCli;
	public int idNutri;
	public Client(int idCli, String nameCli, String unameCli, String pwdCli, String emailCli, int idNutri) {
		super();
		this.idCli = idCli;
		this.nameCli = nameCli;
		this.unameCli = unameCli;
		this.pwdCli = pwdCli;
		this.emailCli = emailCli;
		this.idNutri = idNutri;
	}
	@Override
	public String toString() {
		return "Client [idCli=" + idCli + ", nameCli=" + nameCli + ", unameCli=" + unameCli + ", pwdCli=" + pwdCli
				+ ", emailCli=" + emailCli + ", idNutri=" + idNutri + "]";
	}
	
}
