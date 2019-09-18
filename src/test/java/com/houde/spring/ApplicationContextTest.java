package com.houde.spring;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationContextTest {

    @Test
    public void testFactoryBean() {
        String configLocation = "spring/application-factory-bean.xml";
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(configLocation);
        System.out.println("helloFactory -> " + applicationContext.getBean("helloFactory"));
        System.out.println("&helloFactory -> " + applicationContext.getBean("&helloFactory"));
    }

    @Test
    public void testFactoryMethod() {
        String configLocation = "spring/application-factory-method.xml";
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(configLocation);
        System.out.println("staticHelloFactory -> " + applicationContext.getBean("staticHelloFactory"));
    }

    @Test
    public void testFactoryMethod2() {
        String configLocation = "spring/application-factory-method.xml";
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(configLocation);
        Object obj1 = applicationContext.getBean("staticHelloFactory2");
        System.out.println("obj1 -> " + obj1);
        System.out.println("staticHelloFactory -> " + applicationContext.getBean("staticHelloFactory2"));
    }

    @Test
    public void testLookupMethod() {
        String configLocation = "spring/application-lookup-method.xml";
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(configLocation);
        PlayerProvider obj1 = (PlayerProvider) applicationContext.getBean("playerProvider");
        System.out.println("player -> " + obj1.getPlayer()+", "+obj1.hashCode());
        System.out.println("player -> " + obj1.getPlayer()+", "+obj1.hashCode());
     }
}