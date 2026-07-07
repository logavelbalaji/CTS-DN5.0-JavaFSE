import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.SharedEntityManagerCreator;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.sql.DataSource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.HashMap;

@Configuration
@EnableTransactionManagement
public class PrimaryDbConfig {
    @Primary
    @Bean(name = {"primaryDataSource", "dataSource"})
    @ConfigurationProperties(prefix = "spring.primary-datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = {"primaryEntityManagerFactory", "entityManagerFactory"})
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            @Qualifier("primaryDataSource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("temp");
        em.setPersistenceUnitPostProcessors(pui -> {
            pui.addManagedClassName("PrimaryEmployee");
            pui.addManagedClassName("PrimaryDepartment");
        });
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        em.setJpaPropertyMap(properties);
        return em;
    }

    @Primary
    @Bean(name = {"primaryTransactionManager", "transactionManager"})
    public PlatformTransactionManager transactionManager(
            @Qualifier("primaryEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Primary
    @Bean(name = "primaryDepartmentRepository")
    public PrimaryDepartmentRepository primaryDepartmentRepository(
            @Qualifier("primaryEntityManagerFactory") EntityManagerFactory emf) {
        EntityManager em = SharedEntityManagerCreator.createSharedEntityManager(emf);
        return new JpaRepositoryFactory(em).getRepository(PrimaryDepartmentRepository.class);
    }

    @Primary
    @Bean(name = "primaryEmployeeRepository")
    public PrimaryEmployeeRepository primaryEmployeeRepository(
            @Qualifier("primaryEntityManagerFactory") EntityManagerFactory emf) {
        EntityManager em = SharedEntityManagerCreator.createSharedEntityManager(emf);
        return new JpaRepositoryFactory(em).getRepository(PrimaryEmployeeRepository.class);
    }
}
