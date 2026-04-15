package com.csu.ecbackend.service.impl;

import com.csu.ecbackend.bean.ASTClass;
import com.csu.ecbackend.bean.ASTMethod;
import com.csu.ecbackend.commom.CommonResponse;
import com.csu.ecbackend.service.ASTService;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.MethodCallExpr;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class ASTServiceImpl implements ASTService {


    @Override
    public CommonResponse<ArrayList<ASTClass>> getASTResult(String fileName) {
        ArrayList<ASTClass> ASTResultList = new ArrayList<>();
        try {
            CompilationUnit cu = StaticJavaParser.parse(new File(fileName));
            List<ClassOrInterfaceDeclaration> classes = cu.findAll(ClassOrInterfaceDeclaration.class);
//            List<ClassOrInterfaceDeclaration> classes = cu.findAll(ClassOrInterfaceDeclaration.class);
            for (ClassOrInterfaceDeclaration type : classes) {
                ASTClass astClass = new ASTClass();
                astClass.setClassName(String.valueOf(type.getName()));
                ArrayList<ASTMethod> methodList = new ArrayList<>();
                List<MethodDeclaration> methods = type.getMethods();
                int rfc = 0;
                for (MethodDeclaration method : methods) {
                    ASTMethod astMethod = new ASTMethod();
                    astMethod.setMethodName(String.valueOf(method.getName()));

                    astMethod.setAccessAuthority(String.valueOf(method.getAccessSpecifier()));
                    astMethod.setReturnType(String.valueOf(method.getType()));

                    NodeList<Parameter> parameterList = method.getParameters();
                    ArrayList<String> parameters = new ArrayList<>();
                    if (parameterList.size()>0){
                        for (Parameter parameter : parameterList) {
                            parameters.add(String.valueOf(parameter.getName()));
                        }
                    }else{
                        parameters=null;
                    }
                    astMethod.setParameters(parameters);
                    // 函数中调用的外部函数
                    List<MethodCallExpr> innerMethods = new ArrayList<>();
                    ArrayList<String> outMethodList = new ArrayList<>();
                    for (Node node : method.getChildNodes()) {
                        node.findAll(MethodCallExpr.class).forEach(innerMethods::add);
                    }
                    rfc += innerMethods.size();
                    // 输出所有调用外部函数的函数名
                    for (MethodCallExpr methodCall : innerMethods) {
                        outMethodList.add(methodCall.getName().asString());
                    }
                    if(outMethodList.size()==0){
                        outMethodList=null;
                    }
                    astMethod.setOutMethodNameList(outMethodList);
                    methodList.add(astMethod);
                }
                astClass.setMethodList(methodList);
                rfc+=methods.size();
                astClass.setRFC(rfc);
                int lcom = getLCOM(type);
                astClass.setLCOM(lcom);
                ASTResultList.add(astClass);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return CommonResponse.createForSuccess("success",ASTResultList);
    }
    public int getLCOM(ClassOrInterfaceDeclaration classes){
        int LCOM = 0;
        int P=0;        // 参照ppt上的P、Q
        int Q=0;
        Set<String> sharedVariables = new HashSet<>();
        List<MethodDeclaration> methods = classes.getMethods();
        // 遍历每个方法，获取使用的成员变量
        for (int i =0;i<methods.size();i++) {
            MethodDeclaration method = methods.get(i);
            boolean isJoint = false;
            List<VariableDeclarator> variables = method.findAll(VariableDeclarator.class);
            // 遍历其它方法，如果它们有使用的共同变量，则Q集合+1，否则P集合+1
            for (int j=i+1;j<methods.size();j++) {
                MethodDeclaration otherMethod = methods.get(j);
                List<VariableDeclarator> otherVariables = otherMethod.findAll(VariableDeclarator.class);
                if(jointMethod(variables,otherVariables)){
                    Q+=1;
                    isJoint = true;
                    break;
                }
            }
            if(!isJoint){       // 如果该方法中的变量和其它方法均不相较
                P+=1;
            }

        }
        // 计算LCOM的数值
        if(P>Q){
            LCOM= P-Q;
        }
        return LCOM;
    }
    public Boolean jointMethod(List<VariableDeclarator> A, List<VariableDeclarator> B) {
        boolean disjoint = Collections.disjoint(A, B);
        // 判断两个集合是否相交，如果相交返回true
        return !disjoint;
    }
}
