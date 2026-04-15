package com.csu.ecbackend.service.impl;

import com.csu.ecbackend.commom.CommonResponse;
import com.csu.ecbackend.service.ASTService;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.MethodCallExpr;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ASTServiceImpl implements ASTService {

    // 所有的类
    private Map<String,Map> allClassMap = new HashMap<>();

    @Override
    public CommonResponse<Map> getASTResult(String fileName) {
        try {
            CompilationUnit cu = StaticJavaParser.parse(new File(fileName));
            List<ClassOrInterfaceDeclaration> classes = cu.findAll(ClassOrInterfaceDeclaration.class);
//            List<ClassOrInterfaceDeclaration> classes = cu.findAll(ClassOrInterfaceDeclaration.class);

            for (ClassOrInterfaceDeclaration type : classes) {
                Map<String,Map> classMap = new HashMap<>();
                String className = String.valueOf(type.getName());
                List<MethodDeclaration> methods = type.getMethods();
                int rfc = 0;
                for (MethodDeclaration method : methods) {
                    Map<String,String> methodMap = new HashMap<>();
                    String methodName = String.valueOf(method.getName());

                    String methodAccess = String.valueOf(method.getAccessSpecifier());
                    methodMap.put("accessAuthority",methodAccess);
                    String methodType = String.valueOf(method.getType());
                    methodMap.put("Type",methodType);
                    NodeList<Parameter> parameterList = method.getParameters();
                    String parametersName = "";
                    if (parameterList.size()>0){
                        for (Parameter parameter : parameterList) {
                            parametersName = parametersName + ","+parameter.getName();
                        }
                        methodMap.put("parametersName",parametersName);
                    }else{
                        methodMap.put("Parameters",null);
                    }
                    // 函数中调用的外部函数
                    List<MethodCallExpr> innerMethods = new ArrayList<>();
                    for (Node node : method.getChildNodes()) {
                        node.findAll(MethodCallExpr.class).forEach(innerMethods::add);
                    }
                    rfc += innerMethods.size();
                    // 输出所有调用外部函数的函数名
                    for (MethodCallExpr methodCall : innerMethods) {
                        String outMethodName = methodCall.getName().asString();
                        methodMap.put("outMethodName",outMethodName);
                    }
                    classMap.put(methodName,methodMap);
                }
                rfc+=methods.size();
                Map<String,String> rfcMap = new HashMap<>();
                rfcMap.put("RFC",String.valueOf(rfc));
                classMap.put("RFC",rfcMap);
                Map<String,String> lcomMap = new HashMap<>();
                int lcom = getLCOM(type);
                lcomMap.put("LCOM",String.valueOf(lcom));
                classMap.put("LCOM",lcomMap);
                allClassMap.put(className,classMap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return CommonResponse.createForSuccess("success",allClassMap);
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
