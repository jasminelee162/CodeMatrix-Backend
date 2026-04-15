package com.csu.ecbackend.tzqServer.method;


import com.csu.ecbackend.bean.Association;
import com.csu.ecbackend.bean.Attribute;
import com.csu.ecbackend.bean.Operation;
import com.csu.ecbackend.commom.CommonResponse;
import com.csu.ecbackend.service.LkCkService;
import com.csu.ecbackend.service.impl.LkCkServiceImpl;
import com.csu.ecbackend.tzqServer.method.domain.MOODDTO;
import com.csu.ecbackend.bean.Class;


import org.springframework.stereotype.Service;


import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class MOODMethod {
	//private Class clazz;
	private ArrayList<Class> lists;
	private List<Association>associationList = new ArrayList<>();

	public MOODMethod() {
	}

	public MOODMethod(ArrayList<Class> lists) {
		super();
		this.lists = lists;
	}
	public CommonResponse<MOODDTO>doMetric(List<String> fileNames) throws FileNotFoundException {
		MOODDTO moodto = new MOODDTO();
		LkCkService lkCkService = new LkCkServiceImpl();

		for (String fileName:fileNames){
			lists = lkCkService.getClassList(fileName);
			List<Association>associations= lkCkService.getAssociationList(fileName);
			associationList.addAll(associations);
		}
//		度量CF
		double TC = lists.size();
		System.out.println(TC);
		double totalRelationShip = 0;
		int associationSize=0;
		for (Class element : lists) {
			for (Association association:associationList)
			{
				if (Objects.equals(association.getBeginId(), element.getId()) || Objects.equals(association.getEndId(), element.getId()))
				{
					associationSize++;
				}
			}
			if(associationSize!=0){
				totalRelationShip = associationSize + totalRelationShip;
			}
		}
		moodto.setCF(totalRelationShip / (TC*TC -TC));
//		度量PF
		double newMethod = 0;
		double overridingMethod = 0;
		double descending = 0;
		double md = 0;
		for(Class cl:lists){
			int noo=0,noa=0,noc=0;
			if(lkCkService.getParent(lists,cl.getParent())!=null){
				Class parent1 = lkCkService.getParent(lists,cl.getParent());
				ArrayList<com.csu.ecbackend.bean.Operation> operationsTemp1 = cl.getOperations();
				ArrayList<com.csu.ecbackend.bean.Operation> operationsTemp2 = parent1.getOperations();
				for (com.csu.ecbackend.bean.Operation op: operationsTemp1){
					if(operationsTemp2.contains(op)){
						System.out.println(cl.getName()+" "+op.getName());
						noo++;
					}
					else if(!operationsTemp2.contains(op)){
						noa++;
					}
				}
			}
			for(Class c:lists){
				if(c.getParent()!=null&&c.getParent().equals(cl.getId())){
					noc++;
				}
			}
			newMethod+=noa;
			overridingMethod = noo;
			descending = noc;
			md += descending * overridingMethod;
		}
		if(md <= 0.001) {
			moodto.setPF(0);
		}else {
			moodto.setPF(newMethod / md);
		}


//		度量AIF
		double fatherVisiableAttributes = 0;
		double declareAttribute = 0;
		for (Class element : lists){
			Class father = lkCkService.getParent(lists,element.getId());
			if(element.getAttributes()!=null){
				declareAttribute += element.getAttributes().size();
			}
			if(father != null){
				ArrayList<com.csu.ecbackend.bean.Attribute> attributes = father.getAttributes();
				for (com.csu.ecbackend.bean.Attribute attribute : attributes) {
					//求所有能被子类继承的属性
					if(!"private".equals(attribute.getVisibility())){
						fatherVisiableAttributes++;
					}
				}
			}
		}
		moodto.setAIF(fatherVisiableAttributes / (fatherVisiableAttributes + declareAttribute));
//		度量MIF
		double inheriatedMethod = 0;
		double parentMethod = 0;
		for (Class element : lists) {
			Class father = lkCkService.getParent(lists,element.getId());
			if (father != null) {
				ArrayList<com.csu.ecbackend.bean.Operation> fatherOperations = father.getOperations();
				for (com.csu.ecbackend.bean.Operation operation : fatherOperations) {
					if (!"private".equals(operation.getVisibility())) {
						parentMethod++;
					}
				}
				int noo = 0;
				for(Class cl:lists) {
					if (lkCkService.getParent(lists, cl.getParent()) != null) {
						Class parent1 = lkCkService.getParent(lists, cl.getParent());
						ArrayList<com.csu.ecbackend.bean.Operation> operationsTemp1 = cl.getOperations();
						ArrayList<com.csu.ecbackend.bean.Operation> operationsTemp2 = parent1.getOperations();
						for (com.csu.ecbackend.bean.Operation op : operationsTemp1) {
							if (operationsTemp2.contains(op)) {
								System.out.println(cl.getName() + " " + op.getName());
								noo++;
							}
						}
					}
				}
				inheriatedMethod += parentMethod
						- noo;
			}
			parentMethod = 0;
		}
		moodto.setMIF(inheriatedMethod /(inheriatedMethod + newMethod));
//		度量AHF
		double totalAttribute = 0;
		double hideAttribute = 0;
		for (Class element : lists) {
			ArrayList<Attribute> attributes = element.getAttributes();
			for (com.csu.ecbackend.bean.Attribute attribute : attributes) {
				totalAttribute++;
				if ("private".equals(attribute.getVisibility())) {
					hideAttribute++;
				}
			}
		}
		moodto.setAHF(hideAttribute / totalAttribute);
//		度量MHF
		double totalMethod = 0;
		double hideMethod = 0;
		for (Class element : lists) {
			ArrayList<Operation> operations = element.getOperations();
			for (com.csu.ecbackend.bean.Operation operation : operations) {
				totalMethod++;
				if ("private".equals(operation.getVisibility())) {
					hideMethod++;
				}
			}
		}
		moodto.setMHF(hideMethod / totalMethod);
		return CommonResponse.createForSuccess("success",moodto);
	}
}
