package com.reflect;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.io.FileInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

//反射，类加载
public class ReflectDemo {

    public static void main(String[] args)
            throws NoSuchMethodException, SecurityException, Exception, IllegalAccessException {
        System.out.println(Test1.compileContent);
        // compileContent使用了final修饰，当成了“宏变量”。在编译时，此处就是“Test final static
        // memeber”。不会导致初始化Test1类
        System.out.println(Test1.TS);// 会导致Test1类初始化，static block执行

        System.out.println();
        Class<ClassTest> clazz = ClassTest.class;

        Constructor[] ctors = clazz.getDeclaredConstructors();
        System.out.println("ClassTest defined constructors is:");
        for (Constructor c : ctors) {
            System.out.println(c);
        }

        System.out.println();
        Method[] mtds = clazz.getMethods();
        System.out.println("ClassTest defined methods is:");
        for (Method d : mtds) {
            // System.out.println(d);
        }

        System.out.println();
        Method testReplace = clazz.getMethod("testReplace", String.class, List.class);
        Parameter[] params = testReplace.getParameters();
        int index = 1;
        for (Parameter p : params) {
            System.out.println(p.toString());
        }

        System.out.println();
        Object o = clazz.newInstance();
        Method test = clazz.getMethod("test", null);
        test.invoke(o, null);
    }

}

class ExtendedObjectFactory {
    // 定义一个对象池，key是对象名，value是实际对象
    private Map<String, Object> objectPool = new HashMap<>();
    private Properties config = new Properties();

    // 从指定属性文件中初始化Properties对象
    public void init(String fileName) {
        try (FileInputStream fis = new FileInputStream(fileName)) {
            config.load(fis);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Object createObject(String clazzName)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class<?> clazz = Class.forName(clazzName);
        return clazz.newInstance();
    }

    public void initPool() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        for (String name : config.stringPropertyNames()) {
            // 每取出一个key-value对，如果key中不包含百分号，表明需要根据value创建一个对象
            if (!name.contains("%")) {
                objectPool.put(name, createObject(config.getProperty(name)));
            }
        }
    }

    public void initProperty() throws NoSuchMethodException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        for (String name : config.stringPropertyNames()) {
            // 每取出一对key-value，如果key中包含百分号，即可认为该key用于控制调用对象的setter方法设置值。%前面为对象名，后面为setter方法名
            if (name.contains("%")) {
                String[] objAndProp = name.split("%");
                Object target = getObject(objAndProp[0]);
                // 获取setter方法名：set+"首字母大写"+剩下部分
                String mtdName = "set" + objAndProp[1].substring(0, 1).toUpperCase() + objAndProp[1].substring(1);
                Class<?> targetClass = target.getClass();
                Method mtd = targetClass.getMethod(mtdName, String.class);
                mtd.invoke(target, config.getProperty(name));
            }
        }
    }

    public Object getObject(String name) {
        return this.objectPool.get(name);
    }

}

class Test1 {
    static {
        System.out.println("static block executed.....");
    }

    static String TS = "TEST STRING";
    static final String compileContent = "Test final static memeber";
}

class ClassTest {
    private int id;
    private String name;

    public ClassTest() {
        super();
    }

    public ClassTest(int id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void test() {
        System.out.println("Invoke the Test method.");
    }

    public void testReplace(String str, List<String> list) {
    }

}