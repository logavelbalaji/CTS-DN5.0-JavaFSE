import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.SharedEntityManagerCreator;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;

@SpringBootConfiguration
@EnableAutoConfiguration
@Import({CountryService.class, CountryController.class})
public class Application {
    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        LOGGER.info("Inside main");
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("temp");
        em.setPersistenceUnitPostProcessors(pui -> {
            pui.addManagedClassName("Country");
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
    public CountryRepository countryRepository(EntityManagerFactory emf) {
        EntityManager em = SharedEntityManagerCreator.createSharedEntityManager(emf);
        return new JpaRepositoryFactory(em).getRepository(CountryRepository.class);
    }

    @Bean
    public CommandLineRunner initData(CountryService countryService, PlatformTransactionManager txManager) {
        return args -> {
            TransactionTemplate transactionTemplate = new TransactionTemplate(txManager);
            transactionTemplate.execute(status -> {
                LOGGER.info("Inside initData CommandLineRunner execution");
                testGetAllCountries(countryService);
                testFindCountryByCode(countryService);
                testAddCountry(countryService);
                testUpdateCountry(countryService);
                testDeleteCountry(countryService);
                testFindCountryByPartialName(countryService);
                return null;
            });
        };
    }

    private static void testGetAllCountries(CountryService countryService) {
        LOGGER.info("Start testGetAllCountries");
        List<Country> countries = countryService.getAllCountries();
        LOGGER.info("countries={}", countries);
        LOGGER.info("End testGetAllCountries");
    }

    private static void testFindCountryByCode(CountryService countryService) {
        LOGGER.info("Start testFindCountryByCode");
        try {
            Country country = countryService.findCountryByCode("IN");
            LOGGER.info("Country: {}", country);
        } catch (CountryNotFoundException e) {
            LOGGER.error("Country not found: IN", e);
        }
        try {
            countryService.findCountryByCode("XX");
        } catch (CountryNotFoundException e) {
            LOGGER.info("Expected exception caught for invalid code XX: {}", e.getMessage());
        }
        LOGGER.info("End testFindCountryByCode");
    }

    private static void testAddCountry(CountryService countryService) {
        LOGGER.info("Start testAddCountry");
        Country country = new Country("TS", "Test Country");
        countryService.addCountry(country);
        try {
            Country added = countryService.findCountryByCode("TS");
            LOGGER.info("Added Country: {}", added);
        } catch (CountryNotFoundException e) {
            LOGGER.error("Failed to retrieve added country", e);
        }
        LOGGER.info("End testAddCountry");
    }

    private static void testUpdateCountry(CountryService countryService) {
        LOGGER.info("Start testUpdateCountry");
        try {
            countryService.updateCountry("TS", "Updated Test Country");
            Country updated = countryService.findCountryByCode("TS");
            LOGGER.info("Updated Country: {}", updated);
        } catch (CountryNotFoundException e) {
            LOGGER.error("Failed to update country", e);
        }
        LOGGER.info("End testUpdateCountry");
    }

    private static void testDeleteCountry(CountryService countryService) {
        LOGGER.info("Start testDeleteCountry");
        countryService.deleteCountry("TS");
        try {
            countryService.findCountryByCode("TS");
            LOGGER.error("Delete failed, country still exists");
        } catch (CountryNotFoundException e) {
            LOGGER.info("Deleted successfully, expected exception caught: {}", e.getMessage());
        }
        LOGGER.info("End testDeleteCountry");
    }

    private static void testFindCountryByPartialName(CountryService countryService) {
        LOGGER.info("Start testFindCountryByPartialName");
        List<Country> countries = countryService.findCountryByPartialName("In");
        LOGGER.info("Countries matching 'In': {}", countries);
        LOGGER.info("End testFindCountryByPartialName");
    }
}
