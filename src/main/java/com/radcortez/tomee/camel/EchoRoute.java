package com.radcortez.tomee.camel;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

/**
 * Description.
 *
 * @author Roberto Cortez
 */
@Named
public class EchoRoute extends RouteBuilder {
    @PersistenceContext(unitName = "camelPu")
    private EntityManager entityManager;
    @Inject
    private UserTransaction userTransaction;

    @Override
    public void configure() throws Exception {
        from("direct:rest-post")
                .convertBodyTo(Echo.class)
                .to("jpa:?transactionManager=#jtaTransactionManager")
                .convertBodyTo(String.class)
                .log("Saved ${body}");
    }

    @Produces
    @Named("jtaTransactionManager")
    private JtaTransactionManager createTransactionManager() {
        JtaTransactionManager jtaTransactionManager = new JtaTransactionManager();
        jtaTransactionManager.setUserTransaction(userTransaction);
        jtaTransactionManager.afterPropertiesSet();
        return jtaTransactionManager;
    }

    @Produces
    @Named("entityManagerFactory")
    private EntityManagerFactory createEntityManagerFactory() {
        return entityManager.getEntityManagerFactory();
    }
}
