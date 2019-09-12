package com.rdlogic.dbconnect;

import com.rdlogic.dbconnect.dao.AccessorJpaDao;
import com.rdlogic.dbconnect.dao.JDBCTestDAO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.sql.Connection;

@SpringBootApplication(scanBasePackages = {"com.rdlogic.dbconnect"})
public class DbconnectApplication {

    public static void main(String[] args) {
//        SpringApplication.run(DbconnectApplication.class, args);
        SpringApplication app = new SpringApplication(DbconnectApplication.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        app.run(args);
    }

    @Bean
    public CommandLineRunner propTest(@Qualifier("rdlDatasource") DataSource rdlClientDatasource, ApplicationContext ctx, AccessorJpaDao dao, JDBCTestDAO jdbcTest) {
        return (args) -> {
            Connection c = null;
            try {
                c = rdlClientDatasource.getConnection();
                System.out.println("successful connection");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                c.close();
            }
            System.out.println(dao.getById("SUP").toString());
            System.out.println("jdbc test");
            System.out.println(jdbcTest.getSUPemail());
        };
    }
}
