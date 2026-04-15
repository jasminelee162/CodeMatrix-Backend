package com.csu.ecbackend.tzqServer.method;

import com.csu.ecbackend.tzqServer.domain.Attribute;
import com.csu.ecbackend.tzqServer.domain.Operation;
import com.csu.ecbackend.tzqServer.method.domain.CKDTO;
import com.csu.ecbackend.tzqServer.domain.Class;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Stack;

@Service
public class CKMethod {
	private CKDTO ck;
	private Class clazz;
	private List<Class> lists;
	private String methodString;

	public CKMethod(Class clazz, List<Class> lists) {
		super();
		ck = new CKDTO();
		this.clazz = clazz;
		this.lists = lists;
	}

	public CKMethod(Class clazz, List<Class> lists, String methodString) {
		super();
		ck = new CKDTO();
		this.clazz = clazz;
		this.lists = lists;
		this.methodString = methodString;
	}

	public CKMethod() {
		ck = new CKDTO();
	}

	public CKDTO getCk() {
		parse();
		return ck;
	}

	private void parse() {
		System.out.println(("ck==null") + (ck == null));
		ck.setWMC(getWMC());
		ck.setRFC(getRFC(methodString));
		ck.setCBO(getCBO());
		ck.setDIT(getDIT());
		ck.setNOC(getNOC());
		ck.setLCOM(getLCOM());
	}

	private double getLCOM() {
		// 方法名

		/*
		 * for (int j = 0; j < methodContents.length; j++) {
		 * System.out.println(methodContents[j]); }
		 *
		 * //System.out.println(handleMethod(methodContents));
		 */int LCOM = 0;
		if (methodString != null) {
			System.out.println(methodString == null + "@@@@@@@@@@@@@");
			methodString = methodString.replace("    ", " ");
			String[] elements = methodString.split(" ");
			String[] tempelem = elements;// 临时用来做处理的
			List<Operation> operations = clazz.getOperations();
			for (String string : elements) {
				System.out.println(string);
			}
			System.out.println("method 大小" + operations.size());
			// 存放 method 源代码
			String[] methodContents = new String[operations.size()];
			for (int i = 0; i < operations.size(); i++) {

				// int result = methodString.indexOf(operations.get(i).getName()
				// + "(");
				String tempContent = "";

				// System.out.println(methods[i] + "(存在， 位置 " + result);
				// 从该下标处搜索方法的长度
				// System.out.println("匹配第一个方法 " + operations.get(i).getName());

				for (int j = 0; j < elements.length; j++) {

					if (elements[j].contains(operations.get(i).getName() + "(")) {
						// 如果存在的话
						// 需要判断是方法声明，还是被调用

						int currentIndex = j;
						boolean flag = true;
						boolean isMethod = false;
						while (flag) {

							if (elements[currentIndex].contains(")")) {
								// 如果后面紧跟着{ 表明是方法体，则需要进一步判断
								if (elements[currentIndex].contains("{")
										|| elements[currentIndex + 1]
												.contains("{")) {
									// 下一个或者当前存在{ 表示是方法

									if ((elements[currentIndex + 1]
											.contains("{"))) {
										// 如果是当前行就已经配对到
										currentIndex++;

									}
									// stack
									StringBuffer sb = new StringBuffer();

									Stack<String> stack = new Stack<String>();
									// stack.push("{");

									// 再定义一个flag
									// boolean flag2=false;//判断是否匹配到 }

									while (true) {

										if (elements[currentIndex]
												.contains("{")) {
											stack.push("{");

										}

										// 处理
										sb.append(elements[currentIndex]);
										currentIndex++;

										// 判断是否能跳出
										if (elements[currentIndex]
												.contains("}")) {
											sb.append(elements[currentIndex]);
											tempContent = sb.toString();
											// System.out.println(sb.toString());
											stack.pop();
											// System.out.println("匹配一个右括号");

											if (stack.isEmpty()) {

												flag = false;
												break;
											}

										}

									}

								} else
									break;// 不是方法体跳出循环

							}

							currentIndex++;

						}

					}

					methodContents[i] = tempContent;

				}

			}
			System.out.println("----------------------");
			LCOM = handleMethod(methodContents);

			for (int j = 0; j < methodContents.length; j++) {
				System.out.println(methodContents[j]);
			}
		}
		else{

			System.out.println("xxxx");

		}
		return LCOM;
	}

	public int handleMethod(String[] methodContect) {

		int q = 0, p = 0;
		List<Attribute> attributes = clazz.getAttributes();
		for (int i = 0; i < methodContect.length; i++) {
			for (int j = i + 1; j < methodContect.length; j++) {

				for (int j2 = 0; j2 < attributes.size(); j2++) {

					if (methodContect[i].contains(attributes.get(j2).getName())
							&& methodContect[j].contains(attributes.get(j2)
									.getName())) {
						q++;
						break;
					}

				}

			}
		}

		int total = (methodContect.length) * (methodContect.length - 1) / 2;
		p = total - q;
		System.out.println("p = " + p + " q = " + q);
		return (p - q > 0 ? p - q : 0);

	}

	private double getNOC() {
		// TODO Auto-generated method stub
		if (clazz.getChildren() != null) {

			return clazz.getChildren().size();
		} else {
			return 0;
		}
	}

	private double getDIT() {
		int DIT = 0;
		String generalization = clazz.getGeneralization();
		while (generalization != null) {
			for (Class element : lists) {
				if (generalization.equals(element.getId())) {
					generalization = element.getGeneralization();
					DIT++;
					break;
				}
			}
		}
		return DIT;
	}

	private double getCBO() {
		// TODO Auto-generated method stub
		int CBO = 0;
		if (clazz.getAssociations() != null) {
			CBO += clazz.getAssociations().size();
		}
		if (clazz.getDependencies() != null) {
			CBO += clazz.getDependencies().size();
		}
		return CBO;
	}

	private double getRFC(String methodString) {
		// TODO Auto-generated method stub
		return clazz.getAssociations().size()+clazz.getDependencies().size();
	}

	private double getWMC() {
		// TODO Auto-generated method stub
		if (clazz.getGeneralization() != null) {

			return clazz.getOperations().size();
		} else {
			return 0;
		}
	}

}
