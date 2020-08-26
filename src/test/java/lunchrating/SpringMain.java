package lunchrating;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("spring/spring-app.xml");

        System.out.println("Bean definition names: " + Arrays.toString(context.getBeanDefinitionNames()));

        context.close();
    }


}
