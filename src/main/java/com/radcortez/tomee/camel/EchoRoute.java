package com.radcortez.tomee.camel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.bean.validator.BeanValidationException;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import javax.ws.rs.core.Response;

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
        onException(BeanValidationException.class)
                .handled(true)
                .convertBodyTo(Response.class);

        from("direct:rest-post")
                .to("bean-validator:echo-validation")
                .convertBodyTo(Echo.class)
                .to("jpa:?transactionManager=#jtaTransactionManager")
                .log("Saved ${body}")
                .convertBodyTo(EchoDto.class)
                .convertBodyTo(Response.class);
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
