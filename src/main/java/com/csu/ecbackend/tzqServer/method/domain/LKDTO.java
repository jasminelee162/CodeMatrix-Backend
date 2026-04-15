package com.csu.ecbackend.tzqServer.method.domain;

public class LKDTO {
	/** number of operation*/
	private double operation;
	/** number of attribute*/
	private double attribute;
	/** number of overridden*/
	private double NOO;
	/** number of add*/
	private double NOA;
	/** specialization index*/
	private double SI;
	public LKDTO(double operation, double attribute, double nOO, double nOA,
			double sI) {
		super();
		this.operation = operation;
		this.attribute = attribute;
		NOO = nOO;
		NOA = nOA;
		SI = sI;
	}
	public LKDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public double getOperation() {
		return operation;
	}
	public void setOperation(double operation) {
		this.operation = operation;
	}
	public double getAttribute() {
		return attribute;
	}
	public void setAttribute(double attribute) {
		this.attribute = attribute;
	}
	public double getNOO() {
		return NOO;
	}
	public void setNOO(double nOO) {
		NOO = nOO;
	}
	public double getNOA() {
		return NOA;
	}
	public void setNOA(double nOA) {
		NOA = nOA;
	}
	public double getSI() {
		return SI;
	}
	public void setSI(double sI) {
		SI = sI;
	}

}
