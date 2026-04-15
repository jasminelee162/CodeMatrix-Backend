package com.csu.ecbackend.tzqServer.method.domain;

import lombok.Data;

@Data
public class MOODDTO {
	/**Method Hiding Factor*/
	private double MHF;
	/**Attribute Hiding Factor*/
	private double AHF;
	/**Method Inheritance Factor*/
	private double MIF;
	/**Attribute Inheritance Factor*/
	private double AIF;
	/**Polymorphism Factor*/
	private double PF;
	/**Coupling Factor*/
	private double CF;
}
