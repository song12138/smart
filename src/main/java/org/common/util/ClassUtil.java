package org.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 类操作工具类
 * Created by paul on 2017/8/21.
 */
public class ClassUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassUtil.class);

    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }


    /**
     * 加载类
     * @param className
     * @param isInitiallized
     * @return
     */
    public static Class<?> loadClass(String className,boolean isInitiallized) {
        Class<?> cls = null;
        try {
            cls = Class.forName(className, isInitiallized, getClassLoader());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return cls;
    }

    public static Class<?> loadClass(String className) {
        return loadClass(className, true);
    }


    /**
     * 获取指定包下的所有类
     */

//    public static Set<Class<?>> getClassSet(String packageName) {
//        Set<Class<?>> classSet = new HashSet<>();
//        try {
//            Enumeration<URL> urls = getClassLoader().getResources(packageName.replace(".", "/"));
//            while (urls.hasMoreElements()) {
//                URL url = urls.nextElement();
//                if (null != url) {
//                    //获取Url的协议名称
//                    String protocol = url.getProtocol();
//                    if ("file".equals(protocol)) {
//                        //替换url中的空格,获取真正的Url
//                        String packagePath = url.getPath().replaceAll("%20", "");
//                        addClass(classSet,packagePath,packageName);
//                    } else if ("jar".equals(protocol)) {
//                        JarURLConnection jarURLConnection= (JarURLConnection) url.openConnection();
//                        if(null != jarURLConnection){
//                            JarFile jarFile = jarURLConnection.getJarFile();
//                            if(null != jarFile){
//                                Enumeration<JarEntry> jarEntities = jarFile.entries();
//                                while (jarEntities.hasMoreElements()) {
//                                    JarEntry jarEntry = jarEntities.nextElement();
//                                    String jarEntryName=jarEntry.getName();
//                                    if (jarEntryName.endsWith(".class")) {
//                                        String className = jarEntryName.substring(0, jarEntryName.lastIndexOf("."))
//                                                .replaceAll("/", ".");
//                                        doAddClass(classSet,className);
//                                    }
//                                }
//                            }
//
//                        }
//                    }
//
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return classSet;
//    }
//
//
//
//
//    private static void addClass(Set<Class<?>> classSet,String packagePath,String packageName){
//        File[] files = new File(packagePath).listFiles(new FileFilter() {
//                @Override
//                public boolean accept(File file) {
//                    return (file.isFile() && file.getName().endsWith(".class")) ||
//                            file.isDirectory();
//                }
//            });
//
//        for (File file : files) {
//            String fileName = file.getName();
//            if(file.isFile()){
//                String className = fileName.substring(0, fileName.lastIndexOf("."));
//                if (StringUtil.isNotEmpty(packageName)) {
//                    className = packageName + "." + className;
//                    //todo
//                } else {
//                    String subPackagePaht = fileName;
//                    if (StringUtil.isNotEmpty(packageName)) {
//                        subPackagePaht = packageName + "/" + subPackagePaht;
//                    }
//                    String subPackageName = fileName;
//                    if (StringUtil.isNotEmpty(packageName)) {
//                        subPackageName = packageName + "." + subPackageName;
//                    }
//                    addClass(classSet,subPackagePaht,subPackageName);
//                }
//            }
//        }
//    }
//
//
//    private static void doAddClass(Set<Class<?>> classSet,String className) {
//        Class<?> cls = loadClass(className, false);
//        classSet.add(cls);
//    }




    public static Set<Class<?>> getClassSet(String pack){

        // 第一个class类的集合
        Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
        // 是否循环迭代
        boolean recursive = true;
        // 获取包的名字 并进行替换
        String packageName = pack;
        String packageDirName = packageName.replace('.', '/');
        // 定义一个枚举的集合 并进行循环来处理这个目录下的things
        Enumeration<URL> dirs;
        try{
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            // 循环迭代下去
            while (dirs.hasMoreElements()){
                // 获取下一个元素
                URL url = dirs.nextElement();
                // 得到协议的名称
                String protocol = url.getProtocol();
                // 如果是以文件的形式保存在服务器上
                if ("file".equals(protocol)) {
                    System.err.println("file类型的扫描");
                    // 获取包的物理路径
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    // 以文件的方式扫描整个包下的文件 并添加到集合中
                    findAndAddClassesInPackageByFile(packageName, filePath, recursive, classes);
                }else if ("jar".equals(protocol)) {
                    // 如果是jar包文件
                    // 定义一个JarFile
                    System.err.println("jar类型的扫描");
                    JarFile jar;
                    try{
                        // 获取jar
                        jar = ((JarURLConnection) url.openConnection()).getJarFile();
                        // 从此jar包 得到一个枚举类
                        Enumeration<JarEntry> entries = jar.entries();
                        // 同样的进行循环迭代
                        while (entries.hasMoreElements()){
                            // 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
                            JarEntry entry = entries.nextElement();
                            String name = entry.getName();
                            // 如果是以/开头的
                            if (name.charAt(0) == '/') {
                                // 获取后面的字符串
                                name = name.substring(1);
                            }
                            // 如果前半部分和定义的包名相同
                            if (name.startsWith(packageDirName)) {
                                int idx = name.lastIndexOf('/');
                                // 如果以"/"结尾 是一个包
                                if (idx != -1) {
                                    // 获取包名 把"/"替换成"."
                                    packageName = name.substring(0, idx).replace('/', '.');
                                }
                                // 如果可以迭代下去 并且是一个包
                                if ((idx != -1) || recursive) {
                                    // 如果是一个.class文件 而且不是目录
                                    if (name.endsWith(".class") && !entry.isDirectory()) {
                                        // 去掉后面的".class" 获取真正的类名
                                        String className = name.substring(packageName.length() + 1, name.length() - 6);
                                        try{
                                            // 添加到classes
                                            classes.add(Class.forName(packageName + '.' + className));
                                        }catch (ClassNotFoundException e){
                                            // log
                                            // .error("添加用户自定义视图类错误 找不到此类的.class文件");
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                    }catch (IOException e){
                        // log.error("在扫描用户定义视图时从jar包获取文件出错");
                        e.printStackTrace();
                    }
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        return classes;
    }

    /**
     * 以文件的形式来获取包下的所有Class
     *
     * @param packageName
     * @param packagePath
     * @param recursive
     * @param classes
     */
    public static void findAndAddClassesInPackageByFile(
            String packageName,
            String packagePath,
            final boolean recursive,
            Set<Class<?>> classes){
        // 获取此包的目录 建立一个File
        File dir = new File(packagePath);
        // 如果不存在或者 也不是目录就直接返回
        if (!dir.exists() || !dir.isDirectory()) {
            // log.warn("用户定义包名 " + packageName + " 下没有任何文件");
            return;
        }
        // 如果存在 就获取包下的所有文件 包括目录
        File[] dirfiles = dir.listFiles(new FileFilter(){

            // 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
            public boolean accept(File file){
                return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));
            }
        });
        // 循环所有文件
        for (File file : dirfiles){
            // 如果是目录 则继续扫描
            if (file.isDirectory()) {
                findAndAddClassesInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath(), recursive, classes);
            }else{
                // 如果是java类文件 去掉后面的.class 只留下类名
                String className = file.getName().substring(0, file.getName().length() - 6);
                try{
                    // 添加到集合中去
                    // classes.add(Class.forName(packageName + '.' +
                    // className));
                    // 经过回复同学的提醒，这里用forName有一些不好，会触发static方法，没有使用classLoader的load干净
                    classes.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className));
                }catch (ClassNotFoundException e){
                    // log.error("添加用户自定义视图类错误 找不到此类的.class文件");
                    e.printStackTrace();
                }
            }
        }
    }
}













