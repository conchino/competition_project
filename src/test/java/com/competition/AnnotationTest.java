package com.competition;

import com.competition.project.annotation.Log;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@SpringBootTest
public class AnnotationTest {

    @Log(operationName = "Hello")
    public void showAnoInfo(String operationName){
        System.out.println(operationName);
    }


    /* 获取注解值，并将注解值赋给对象的方法中参数使用 */
    @Test
    public void annotationUse() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        Class<AnnotationTest> aClass = AnnotationTest.class;
//        AnnotationTest annotationTest = aClass.getDeclaredConstructor().newInstance();

        Method[] methods = aClass.getMethods();

        for (Method method : methods) {
            if (method.isAnnotationPresent(Log.class)){
//                method.invoke(annotationTest,method.getAnnotation(Log.class).operationName());
                method.invoke(aClass.getDeclaredConstructor().newInstance(),method.getAnnotation(Log.class).operationName());
            }
        }

    }


    @Test
    public void getAnnotationValue(){       // 注解使用测试
        AnnotationTest annotationTest = new AnnotationTest();

        Method[] methods = annotationTest.getClass().getMethods();

        for (Method method : methods) {

            if (method.isAnnotationPresent(Log.class)){
                System.out.println("方法: "+method.getName()+" 上存在注解: "+Log.class.getSimpleName()+" || 注解值为: "+method.getAnnotation(Log.class).operationName());
                Log annotation = method.getAnnotation(Log.class);

                Class<? extends Annotation> annotationType = annotation.annotationType();

                System.out.println(annotation);
                System.out.println(annotationType);
            }


        }
    }

}
