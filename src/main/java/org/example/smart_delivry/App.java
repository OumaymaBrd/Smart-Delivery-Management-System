package org.example.smart_delivry;

import org.example.smart_delivry.config.AppConfig;
import org.example.smart_delivry.controller.MainController;
import org.example.smart_delivry.service.HelloService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App
{
    public static void main( String[] args )
    {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        HelloService helloService = (HelloService) context.getBean("helloService");
        MainController controller = new MainController(helloService);
        controller.run();
    }
}
