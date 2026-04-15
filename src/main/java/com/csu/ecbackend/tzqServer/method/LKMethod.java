package com.csu.ecbackend.tzqServer.method;



import com.csu.ecbackend.tzqServer.domain.Operation;
import com.csu.ecbackend.tzqServer.method.domain.CKDTO;
import com.csu.ecbackend.tzqServer.method.domain.LKDTO;
import com.csu.ecbackend.tzqServer.domain.Class;
import java.util.List;

public class LKMethod {
	private LKDTO lk;
	private Class clazz;
	private List<Class> lists;

	public LKMethod() {
		super();
		lk = new LKDTO();
	}

	public LKMethod(Class clazz, List<Class> lists) {
		super();
		lk = new LKDTO();
		this.clazz = clazz;
		this.lists = lists;
	}

	public LKDTO getLk() {
		parse();
		return lk;
	}

	private void parse() {
		lk.setOperation(getOperation());
		lk.setAttribute(getAttribute());
		lk.setNOA(getNOA());
		lk.setNOO(getNOO());
		lk.setSI(getSI());
	}

	private double getSI() {
		CKMethod ckMethod = new CKMethod(clazz, lists);
		CKDTO ck = ckMethod.getCk();
		double DIT = ck.getDIT();
		double NOO = getNOO();
		double totalMethod = getNOA();
		Class father = clazz.getFather();
		if(father != null){
			totalMethod += father.getOperations().size();
		}
		double SI = (NOO * DIT) / totalMethod;
		return SI;
	}

	private double getNOO() {
		Class father = clazz.getFather();
		int NOO = 0;
		if (father == null) {
			return NOO;
		} else {
			List<Operation> fatherOperation = father.getOperations();
			List<Operation> childOperation = clazz.getOperations();
			for (int i = 0; i < childOperation.size(); i++) {
				for (int j = 0; j < fatherOperation.size(); j++) {
					if (childOperation.get(i).getName()
							.equals(fatherOperation.get(j).getName())) {
						NOO++;
					}
				}
			}
		}

		return NOO;
	}

	private double getNOA() {
		double NOA = 0;
		Class father = clazz.getFather();

		if(father == null){
			NOA = clazz.getOperations().size();
		} else{
			NOA = clazz.getOperations().size() - getNOO();
		}
		return NOA;
	}

	private double getAttribute() {
		return clazz.getAttributes().size();
	}
	private double getOperation() {
		return clazz.getAttributes().size();
	}
}
