package com.csu.ecbackend.service.impl;

import com.csu.ecbackend.bean.*;
import com.csu.ecbackend.bean.Class;
import com.csu.ecbackend.commom.CommonResponse;
import com.csu.ecbackend.service.LkCkService;
import com.csu.ecbackend.tzqServer.method.domain.MOODDTO;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Slf4j
@Service
@MapperScan("com.csu.ecbackend.persistence")
public class LkCkServiceImpl implements LkCkService {

    @Override
    public ArrayList<Class> getClassList(String url){
        Document document = null;
        ArrayList<Class> classes = new ArrayList<Class>();
        try {
            SAXReader saxReader = new SAXReader();
            document = saxReader.read(new File(url));
            Element root = document.getRootElement();

            List elements = root.elements();

            for (Object element: elements){
                if(((Element) element).attribute(0).getValue().equals("uml:Class")){
                    Class classT = new Class();
                    classT.setId(((Element) element).attribute(1).getValue());
                    classT.setName(((Element) element).attribute(2).getValue());
                    List elementsSub =((Element) element).elements();
                    ArrayList<Attribute> attributes = new ArrayList<Attribute>();
                    ArrayList<Operation> operations = new ArrayList<Operation>();
                    for (Object elementSub: elementsSub){
                        if(((Element) elementSub).getName().equals("generalization")){
                            classT.setParent(((Element) elementSub).attribute("general").getValue());
                        }
                        else if(((Element) elementSub).getName().equals("ownedAttribute")){
                            Attribute attribute = new Attribute();
                            attribute.setName(((Element) elementSub).attribute("name").getValue());
                            attribute.setVisibility(((Element) elementSub).attribute("visibility").getValue());
                            attribute.setType(((Element) elementSub).attribute("type").getValue());
                            attributes.add(attribute);
                        }
                        else if(((Element) elementSub).getName().equals("ownedOperation")){
                            Operation operation = new Operation();
                            operation.setName(((Element) elementSub).attribute("name").getValue());
                            operation.setVisibility(((Element) elementSub).attribute("visibility").getValue());
                            List elementsSubSub =((Element) elementSub).elements();
                            ArrayList<Parameter> parameters = new ArrayList<Parameter>();
                            for (Object elementSubSub :elementsSubSub){
                                if(((Element) elementSubSub).getName().equals("ownedParameter")){
                                    Parameter parameter = new Parameter();
                                    parameter.setName(((Element) elementSubSub).attribute("name").getValue());
                                    parameter.setType(((Element) elementSubSub).attribute("type").getValue());
                                    parameters.add(parameter);
                                }
                            }
                            operation.setParameters(parameters);
                            operations.add(operation);
                        }
                        classT.setAttributes(attributes);
                        classT.setOperations(operations);
                    }
                    classes.add(classT);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return classes;
    }


    @Override
    public ArrayList<Association> getAssociationList(String url) {
        Document document = null;
        ArrayList<Association> associations =new ArrayList<Association>();
        try {
            SAXReader saxReader = new SAXReader();
            document = saxReader.read(new File(url));
            Element root = document.getRootElement();

            List elements = root.elements();

            for (Object element: elements){
                if(((Element) element).attribute(0).getValue().equals("uml:Association")){
                    Association association = new Association();
                    List elementsSub =((Element) element).elements();
                    int f=0;
                    for(Object elementSub: elementsSub){
                        if(f==0){
                            association.setBeginId(((Element) elementSub).attribute("type").getValue());
                            f=1;
                        }
                        else if(f==1){
                            association.setEndId(((Element) elementSub).attribute("type").getValue());
                        }
                    }
                    associations.add(association);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return associations;
    }

    @Override
    public CommonResponse<ArrayList<LK>> getLk(String url) {
        ArrayList<Class> classes = new ArrayList<Class>();
        classes = getClassList(url);
        ArrayList<LK> lks = new ArrayList<LK>();

        double mtotal=0;
        for (Class c:classes){
            mtotal = mtotal + c.getOperations().size();
        }

        for(Class cl:classes){
            LK lk = new LK();
            lk.setClassName(cl.getName());
            int cs=0;
            if(cl.getAttributes()!=null&&cl.getOperations()!=null){
                cs = cs +cl.getAttributes().size()+cl.getOperations().size();
            }
            else{
                if(cl.getAttributes()!=null){
                    cs = cs +cl.getAttributes().size();
                }
                if(cl.getOperations()!=null){
                    cs = cs +cl.getOperations().size();
                }
            }
            Class parent = getParent(classes,cl.getParent());
            ArrayList<Attribute> attributesTemp = (ArrayList<Attribute>) cl.getAttributes().clone();
            ArrayList<Operation> operationsTemp = (ArrayList<Operation>) cl.getOperations().clone();
            int l=0;
            while(parent!=null){
                l++;
                ArrayList<Attribute> attributesTempTemp = (ArrayList<Attribute>) parent.getAttributes().clone();
                ArrayList<Operation> operationsTempTemp = (ArrayList<Operation>) parent.getOperations().clone();
                for(Attribute at:attributesTempTemp){
                    if((!attributesTemp.contains(at))&&("public".equals(at.getVisibility())|| "protected".equals(at.getVisibility()))){
                        attributesTemp.add(at);
                        cs ++;
                    }
                }
                for(Operation op:operationsTempTemp){
                    if((!operationsTemp.contains(op))&&("public".equals(op.getVisibility())|| "protected".equals(op.getVisibility()))){
                        operationsTemp.add(op);
                        cs ++;
                    }
                }
                parent = getParent(classes, parent.getParent());
            }
            lk.setCs(cs);
            int noo=0,noa=0;
            if(getParent(classes,cl.getParent())!=null){
                Class parent1 = getParent(classes,cl.getParent());
                ArrayList<Operation> operationsTemp1 = cl.getOperations();
                ArrayList<Operation> operationsTemp2 = parent1.getOperations();
                for (Operation op: operationsTemp1){
                    if(operationsTemp2.contains(op)){
                        noo++;
                    }
                    else if(!operationsTemp2.contains(op)){
                        noa++;
                    }
                }
            }
            lk.setNoo(noo);
            lk.setNoa(noa);

            double si = (noo*l)/mtotal;
            lk.setSi(si);
            lks.add(lk);
        }
        return CommonResponse.createForSuccess("success",lks);
    }

    @Override
    public Class getParent(ArrayList<Class> classes, String id){
        for(Class cl:classes){
            if(cl.getId().equals(id)){
                return cl;
            }
        }
        return null;
    }

    /**
     * 判断一个方法名是否是 getter 或 setter，用于 WMC 排除
     */
    private boolean isGetterOrSetter(String methodName) {
        if (methodName == null) return false;
        return methodName.startsWith("get") || methodName.startsWith("set");
    }

    /**
     * 计算 WMC：该类的方法数，排除 get 和 set 方法
     */
    private int calculateWMC(Class cl) {
        int wmc = 0;
        for (Operation op : cl.getOperations()) {
            if (!isGetterOrSetter(op.getName())) {
                wmc++;
            }
        }
        return wmc;
    }

    /**
     * 计算 RFC：方法集合 M 的方法数 + 每个方法调用的方法数（即每个方法的参数列表中类型对应的方法数）
     *
     * 由于 UML XMI 中没有方法体，无法直接获得调用关系。
     * 这里使用 ownedParameter 中出现的类型（非 return、非基本类型）推断被调用的方法：
     * 对于每个方法的每个非返回参数，如果其 type 对应某个类，则将该类的所有方法计入响应集合。
     * RFC = |RS| = 本类方法数 + 所有被引用类的方法总数（去重）
     */
    private int calculateRFC(Class cl, ArrayList<Class> allClasses) {
        // RS 用方法名字符串去重（本类方法 + 被调用类的方法）
        Set<String> responseSet = new HashSet<>();

        // 加入本类所有方法
        for (Operation op : cl.getOperations()) {
            responseSet.add(cl.getName() + "#" + op.getName());
        }

        // 遍历本类每个方法的参数，找到参数类型对应的类，将其方法加入响应集合
        for (Operation op : cl.getOperations()) {
            if (op.getParameters() == null) continue;
            for (Parameter param : op.getParameters()) {
                // 跳过 return 方向的参数（direction="return"，在 XML 中参数名与方法名相同且无 direction 属性时也记录）
                // 这里用参数名与方法名相同作为 return 参数的判断依据（与现有解析逻辑一致）
                if (param.getName() != null && param.getName().equals(op.getName())) {
                    continue; // 这是 return 参数，跳过
                }
                // 查找参数类型对应的类
                String paramTypeId = param.getType();
                if (paramTypeId == null) continue;
                Class referencedClass = getClassById(allClasses, paramTypeId);
                if (referencedClass != null) {
                    for (Operation refOp : referencedClass.getOperations()) {
                        responseSet.add(referencedClass.getName() + "#" + refOp.getName());
                    }
                }
            }
        }

        return responseSet.size();
    }

    /**
     * 根据类 ID 查找类（用于 RFC 中查找参数类型对应的类）
     */
    private Class getClassById(ArrayList<Class> allClasses, String id) {
        for (Class cl : allClasses) {
            if (cl.getId().equals(id)) {
                return cl;
            }
        }
        return null;
    }

    /**
     * 计算 LCOM：
     * 对所有方法两两组合（i < j），
     *   若方法 i 和方法 j 使用的参数类型集合（Ii ∩ Ij = ∅），计为 P；
     *   若有交集（Ii ∩ Ij ≠ ∅），计为 Q。
     * LCOM = max(|P| - |Q|, 0)
     *
     * "使用的参数"：取该方法的 ownedParameter 中非 return 参数的 type 集合作为 Ii。
     */
    private int calculateLCOM(Class cl) {
        ArrayList<Operation> ops = cl.getOperations();
        int n = ops.size();
        if (n < 2) return 0;

        // 为每个方法建立其参数类型集合 Ii
        List<Set<String>> paramTypeSets = new ArrayList<>();
        for (Operation op : ops) {
            Set<String> typeSet = new HashSet<>();
            if (op.getParameters() != null) {
                for (Parameter param : op.getParameters()) {
                    // 跳过 return 参数（参数名与方法名相同）
                    if (param.getName() != null && param.getName().equals(op.getName())) {
                        continue;
                    }
                    if (param.getType() != null && !param.getType().isEmpty()) {
                        typeSet.add(param.getType());
                    }
                }
            }
            paramTypeSets.add(typeSet);
        }

        int p = 0, q = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                Set<String> intersection = new HashSet<>(paramTypeSets.get(i));
                intersection.retainAll(paramTypeSets.get(j));
                if (intersection.isEmpty()) {
                    p++;
                } else {
                    q++;
                }
            }
        }

        return Math.max(p - q, 0);
    }

    @Override
    public CommonResponse<ArrayList<CK>> getCk(String url) {
        ArrayList<Class> classes = new ArrayList<Class>();
        ArrayList<Association> associations = new ArrayList<Association>();
        classes = getClassList(url);
        associations = getAssociationList(url);
        ArrayList<CK> cks = new ArrayList<CK>();

        for(Class cl : classes){
            CK ck = new CK();
            ck.setName(cl.getName());

            // DIT：继承树深度
            Class parent = getParent(classes, cl.getParent());
            int l = 0;
            while(parent != null){
                l++;
                parent = getParent(classes, parent.getParent());
            }
            ck.setDit(l);

            // NOC：直接子类数
            int noc = 0;
            for(Class c : classes){
                if(c.getParent() != null && c.getParent().equals(cl.getId())){
                    noc++;
                }
            }
            ck.setNoc(noc);

            // CBO：关联耦合数
            int cbo = 0;
            for(Association association : associations){
                if(association.getBeginId().equals(cl.getId())){
                    cbo++;
                }
                if(association.getEndId().equals(cl.getId())){
                    cbo++;
                }
            }
            ck.setCbo(cbo);

            // WMC：方法数（排除 getter/setter）
            int wmc = calculateWMC(cl);
            ck.setWmc(wmc);

            // RFC：响应集合大小
            int rfc = calculateRFC(cl, classes);
            ck.setRfc(rfc);

            // LCOM：内聚缺乏度
            int lcom = calculateLCOM(cl);
            ck.setLcom(lcom);

            cks.add(ck);
        }
        return CommonResponse.createForSuccess("success", cks);
    }
}