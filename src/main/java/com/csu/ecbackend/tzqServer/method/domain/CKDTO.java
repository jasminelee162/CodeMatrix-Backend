package com.csu.ecbackend.tzqServer.method.domain;

public class CKDTO {
	/** weighted Method per class*/
	private double WMC;
	/** Response For a Class*/
	private double RFC;
	/** Lack of COhesion*/
	private double LCOM;
	/** Coupling Between*/
	private double CBO;
	/** Depth of Inheritance Tree*/
	private double DIT;
	/** Number Of Children*/
	private double NOC;

	public CKDTO(double wMC, double rFC, double lCOM, double cBO, double dIT,
			double nOC) {
		super();
		WMC = wMC;
		RFC = rFC;
		LCOM = lCOM;
		CBO = cBO;
		DIT = dIT;
		NOC = nOC;
	}
	public CKDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public double getWMC() {
		return WMC;
	}
	public void setWMC(double wMC) {
		WMC = wMC;
	}
	public double getRFC() {
		return RFC;
	}
	public void setRFC(double rFC) {
		RFC = rFC;
	}
	public double getLCOM() {
		return LCOM;
	}
	public void setLCOM(double lCOM) {
		LCOM = lCOM;
	}
	public double getCBO() {
		return CBO;
	}
	public void setCBO(double cBO) {
		CBO = cBO;
	}
	public double getDIT() {
		return DIT;
	}
	public void setDIT(double dIT) {
		DIT = dIT;
	}
	public double getNOC() {
		return NOC;
	}
	public void setNOC(double nOC) {
		NOC = nOC;
	}

}
