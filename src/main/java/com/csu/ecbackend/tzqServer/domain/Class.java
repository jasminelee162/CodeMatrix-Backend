package com.csu.ecbackend.tzqServer.domain;

import java.util.ArrayList;
import java.util.List;

public class Class {
	private String id;
	private String name;
	private List<Attribute> attributes;
	private List<Operation> operations;
	private Class father;
	private String generalization;
	private List<Class> children;
	private List<String> dependencies;
	private List<String> associations;

	public Class() {
		super();
		attributes = new ArrayList<Attribute>();
		operations = new ArrayList<Operation>();
		dependencies = new ArrayList<String>();
		associations = new ArrayList<String>();
		children = new ArrayList<Class>();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Attribute> getAttributes() {
		return attributes;
	}
	public void addAttribute(Attribute attribute){
		attributes.add(attribute);
	}
	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}
	public void setOperations(List<Operation> operations) {
		this.operations = operations;
	}
	public List<Operation> getOperations() {
		return operations;
	}
	public void addOperation(Operation operation){
		operations.add(operation);
	}
	public Class getFather() {
		return father;
	}
	public void setFather(Class father) {
		this.father = father;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGeneralization() {
		return generalization;
	}
	public void setGeneralization(String generalization) {
		this.generalization = generalization;
	}
	public List<String> getDependencies() {
		return dependencies;
	}

	public void addDependency(String classID){
		dependencies.add(classID);
	}
	public List<String> getAssociations() {
		return associations;
	}

	public void addAssociation(String classID){
		associations.add(classID);
	}
	public List<Class> getChildren() {
		return children;
	}
	public void addChildren(Class clazz){
		children.add(clazz);
	}
}
