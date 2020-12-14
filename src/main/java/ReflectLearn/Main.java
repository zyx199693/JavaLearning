package ReflectLearn;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodError, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchFieldError, NoSuchMethodException, NoSuchFieldException {
        /**
         * 获取TargetObject类的Class对象并且创建TargetObject类实例
         */
        Class<?> targetClass = Class.forName("ReflectLearn.TargetObject");
        TargetObject targetObject = (TargetObject) targetClass.newInstance();

        /**
         * 获取所有类中定义的所有方法
         */
        Method[] methods = targetClass.getDeclaredMethods();
        for(Method method : methods) {
            System.out.println(method);
        }

        /**
         * 获取指定方法并调用
         */
        Method publicMethod = targetClass.getDeclaredMethod("publicMethod", String.class);
        publicMethod.invoke(targetObject, "JavaGuide");

        /**
         * 获取指定参数并对参数进行修改
         */
        Field field = targetClass.getDeclaredField("value");
        field.setAccessible(true);
        field.set(targetObject, "ABC");

        /**
         * 调用private方法
         */
        Method privateMethod = targetClass.getDeclaredMethod("privateMethod");
        privateMethod.setAccessible(true);
        privateMethod.invoke(targetObject);
    }
}
