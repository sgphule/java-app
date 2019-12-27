package com.nutritionist;

import java.io.Serializable;

public class Nutritionist implements Serializable{
	public int idNutri;
	public String nameNutri;
	public String unameNutri;
	public String pwdNutri;
	public String emailNutri;
	public Nutritionist(int idNutri, String nameNutri, String unameNutri, String pwdNutri, String emailNutri) {
		this.idNutri = idNutri;
		this.nameNutri = nameNutri;
		this.unameNutri = unameNutri;
		this.pwdNutri = pwdNutri;
		this.emailNutri = emailNutri;
	}
	@Override
	public String toString() {
		return "Nutritionist [idNutri=" + idNutri + ", nameNutri=" + nameNutri + ", unameNutri=" + unameNutri + ", pwdNutri=" + pwdNutri + ", emailNutri=" + emailNutri + "]";
	}
	

}
