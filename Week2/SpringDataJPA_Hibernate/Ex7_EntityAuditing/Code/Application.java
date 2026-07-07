import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.domain.AuditorAware;

@SpringBootConfiguration
@EnableAutoConfiguration
@EnableJpaAuditing
@Import({DepartmentController.class, EmployeeController.class})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("temp");
        em.setPersistenceUnitPostProcessors(pui -> {
            pui.addManagedClassName("Employee");
            pui.addManagedClassName("Department");
        });
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        em.setJpaPropertyMap(properties);
        return em;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    public AuditorAware<String> auditorProvider() {
        return new AuditorAwareImpl();
    }

    @Bean
    public DepartmentRepository departmentRepository(EntityManager entityManager) {
        return new JpaRepositoryFactory(entityManager).getRepository(DepartmentRepository.class);
    }

    @Bean
    public EmployeeRepository employeeRepository(EntityManager entityManager) {
        return new JpaRepositoryFactory(entityManager).getRepository(EmployeeRepository.class);
    }

    @Bean
    public CommandLineRunner initData(
            DepartmentRepository deptRepo,
            EmployeeRepository empRepo,
            PlatformTransactionManager transactionManager) {
        return args -> {
            new TransactionTemplate(transactionManager).execute(status -> {
                Department hr = new Department();
                hr.setName("HR");
                deptRepo.save(hr);
                Department finance = new Department();
                finance.setName("Finance");
                deptRepo.save(finance);
                Employee emp1 = new Employee();
                emp1.setName("Aarav Sharma");
                emp1.setEmail("aarav.sharma@bank.com");
                emp1.setDepartment(hr);
                empRepo.save(emp1);
                Employee emp2 = new Employee();
                emp2.setName("Bhavna Patel");
                emp2.setEmail("bhavna.patel@bank.com");
                emp2.setDepartment(finance);
                empRepo.save(emp2);
                return null;
            });
        };
    }
}
