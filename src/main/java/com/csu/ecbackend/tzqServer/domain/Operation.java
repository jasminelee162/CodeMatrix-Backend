package com.csu.ecbackend.tzqServer.domain;

import java.util.ArrayList;
import java.util.List;

public class Operation {
	private String name;
	private String visibility;
	private List<Parameter> parameters;


	public Operation() {
		parameters = new ArrayList<Parameter>();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getVisibility() {
		return visibility;
	}
	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}
	public List<Parameter> getParameters() {
		return parameters;
	}
	public void addPrameter(Parameter parameter) {
		parameters.add(parameter);
	}


}
