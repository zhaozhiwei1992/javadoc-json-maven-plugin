package com.example;

import com.google.gson.Gson;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.Doclet;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.RootDoc;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: ExampleDoclet
 * @Description: 将指定目录下的类及注释信息，转换到json文件中
 * 参考 demo/springboot/springboot-swagger-javadoc/xxx/CommentToJsonMain
 *
 * @date 2022/10/25 下午8:25
 */
public class CommentToJson extends Doclet {

    private static String outputPackage;

    /**
     * @data: 2022/10/25-下午11:14
     * @User: zhaozhiwei
     * @method: start
      * @param root :
     * @return: boolean
     * @Description: 解析java文件注释信息, 并序列化到文件中
     */
    public static boolean start(RootDoc root) {
        final Map<String, Object> classMap = new HashMap<>();
        ClassDoc[] classes = root.classes();
        for (ClassDoc cls : classes) {
//            System.out.println(cls);
            // 输出类注释信息
//            System.out.println(cls.getRawCommentText());
            final Map<String, String> classRawCommentMap = rawCommentToMap(cls);
            classMap.putAll(classRawCommentMap);
            MethodDoc[] methods = cls.methods();
            for (MethodDoc meth : methods) {
//                System.out.println(meth);
                // 输出方法上注释信息
//                System.out.println(meth.getRawCommentText());
                final Map<String, String> methodRawCommentMap = rawCommentToMap(meth);
                classMap.putAll(methodRawCommentMap);
            }
        }

        // 序列化
        final Gson gson = new Gson();

        // 写出到一个文件如: class目录下
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(outputPackage + File.separator + "javadoc.json");
            fileWriter.write(gson.toJson(classMap));
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            if(!Objects.isNull(fileWriter)){
                try {
                    fileWriter.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            throw new RuntimeException(e);
        }
        return true;
    }

    /**
     * @data: 2022/10/25-下午11:30
     * @User: zhaozhiwei
     * @method: rawCommentToMap
      * @param meth :
     * @return: java.util.Map<java.lang.String,java.lang.String>
     * @Description: 将methodDoc信息转map保存
     * map.put(com.example.resource.PersonResource.findByID(java.lang.Long)#data, "xx");
     * map.put(com.example.resource.PersonResource.findByID(java.lang.Long)#User, "xx");
     * ...
     *
     */
    private static Map<String, String> rawCommentToMap(MethodDoc meth) {

        final Map<String, String> map = new HashMap<>();
//        com.example.resource.PersonResource.findByID(java.lang.Long)
//        @date: 2022/10/25-上午10:19
//        @author: zhaozhiwei
//        @method: findByID
//        @param id :
// @return: com.lx.demo.springbootcache.domain.Person
//        @Description: 根据id获取用户信息
//        获取十次， 只有第一次是读库，后续都是取缓存
//        直接删掉redis缓存里的内容，仍然可以获取数据，并且走缓存，此时获取的是服务缓存ehcache中的信息
//        seq 10 |xargs -i curl -XGET 'http://localhost:8080/persons/2'
        final String rawCommentText = meth.getRawCommentText();
        final String methodName = meth.toString();
        final String[] split = rawCommentText.split("\n");
        for (String s : split) {
            if(s.contains("method")){
                map.put(methodName + "#" + "method", s.replace("@method: ", "").trim());
            }else if(s.contains("Description")){
                map.put(methodName + "#" + "Description", s.replace("@Description: ", "").trim());
            }else if(s.contains("author")){
                map.put(methodName + "#" + "author", s.replace("@author: ", "").trim());
            }else if(s.contains("date")){
                map.put(methodName + "#" + "date", s.replace("@date: ", "").trim());
            }
        }
        return map;
    }

    /**
     * @data: 2022/10/25-下午11:23
     * @User: zhaozhiwei
     * @method: rawCommentToMap
      * @param cls :
     * @return: java.util.Map<java.lang.String,java.lang.String>
     * @Description: 将ClassDoc信息转换为map保存
     *
     * map.put("com.example.resource.PersonResource#Title", "xx")
     * map.put("com.example.resource.PersonResource#version", "xx")
     * ...
     */
    private static Map<String, String> rawCommentToMap(ClassDoc cls) {
        final Map<String, String> map = new HashMap<>();
//        com.example.resource.PersonResource
//        @Title: PersonController
//        @Package com/example/springbootcache/controller/PersonController.java
//        @Description: 用户信息接口
//        @author zhaozhiwei
//        @date 2022/10/25 下午8:23
//        @version V1.0
        final String rawCommentText = cls.getRawCommentText();
        final String packageName = cls.toString();
        final String[] split = rawCommentText.split("\n");
        for (String s : split) {
            if(s.contains("Title")){
                map.put(packageName + "#" + "Title", s.replace("@Title: ", "").trim());
            }else if(s.contains("Description")){
                map.put(packageName + "#" + "Description", s.replace("@Description: ", "").trim());
            }else if(s.contains("author")){
                map.put(packageName + "#" + "author", s.replace("@author: ", "").trim());
            }else if(s.contains("date")){
                map.put(packageName + "#" + "date", s.replace("@date: ", "").trim());
            }else if(s.contains("version")){
                map.put(packageName + "#" + "version", s.replace("@version: ", "").trim());
            }else if(s.contains("Package")){
                map.put(packageName + "#" + "Package", s.replace("@Package: ", "").trim());
            }
        }
        return map;
    }

    // 这里手动执行， 后续可以将这个代码做成maven插件
    public void exec(String basePackage, String outputPkg) {
        outputPackage = outputPkg;
        // 获取指定package下所有文件全路径
        final List<String> docArgsList = new ArrayList<>();
        docArgsList.add("-doclet");
        docArgsList.add(CommentToJson.class.getName());
        // 填充文件列表
        generateFileList(basePackage, docArgsList);
        System.out.println(docArgsList);
        com.sun.tools.javadoc.Main.execute(docArgsList.toArray(new String[]{}));
    }

    /**
     * @data: 2022/10/25-下午11:11
     * @User: zhaozhiwei
     * @method: generateFileList
      * @param packageName :
     * @param docArgsList :
     * @return: void
     * @Description: 生成要解析的文件列表
     */
    private void generateFileList(String packageName, List<String> docArgsList) {
        // 定义一个枚举的集合 并进行循环来处理这个目录下的内容
        Enumeration<URL> dirs;
        try {
            final URL url1 = new File(packageName).toURI().toURL();
            final Vector<URL> urls = new Vector<>();
            urls.add(url1);
            dirs = urls.elements();
            // 循环迭代下去
            while (dirs.hasMoreElements()) {
                URL url = dirs.nextElement();
                // 得到协议的名称
                String protocol = url.getProtocol();
                if ("file".equals(protocol)) {
                    // 获取包的物理路径
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    // 以文件的方式扫描整个包下的文件 并添加到集合中
                    docArgsList.addAll(findFilesByDirectory(filePath));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public List<String> findFilesByDirectory(String packagePath) {
        // 获取此包的目录 建立一个File
        File dir = new File(packagePath);
        if (!dir.exists() || !dir.isDirectory()) {
            return new ArrayList<>(0);
        }

        File[] dirs = dir.listFiles();
        List<String> classes = new ArrayList<>();
        // 循环所有文件
        for (File file : dirs) {
            // 如果是目录 则继续扫描
            if (file.isDirectory()) {
                classes.addAll(findFilesByDirectory(file.getAbsolutePath()));
            } else if (file.getName().endsWith(".java")) {
                classes.add(file.getAbsoluteFile().toString());
            }
        }

        return classes;
    }
}