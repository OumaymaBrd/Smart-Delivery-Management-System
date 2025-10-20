package org.example.smart_delivry.listener;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class JPALoaderListener implements ServletContextListener {

    private EntityManagerFactory emf;

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        emf = Persistence.createEntityManagerFactory("my-persistence-unit");
        sce.getServletContext().setAttribute("emf", emf);
        System.out.println("JPA EntityManagerFactory créé. Les tables doivent être créées si hbm2ddl.auto=update.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (emf != null && emf.isOpen()) {
            emf.close();
            System.out.println("EntityManagerFactory fermé.");
        }
    }
}
