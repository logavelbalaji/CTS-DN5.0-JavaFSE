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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@SpringBootConfiguration
@EnableAutoConfiguration
@Import({
    CountryService.class, CountryController.class,
    EmployeeService.class, DepartmentService.class, SkillService.class
})
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
            pui.addManagedClassName("Stock");
            pui.addManagedClassName("Employee");
            pui.addManagedClassName("Department");
            pui.addManagedClassName("Skill");
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
    public StockRepository stockRepository(EntityManagerFactory emf) {
        EntityManager em = SharedEntityManagerCreator.createSharedEntityManager(emf);
        return new JpaRepositoryFactory(em).getRepository(StockRepository.class);
    }

    @Bean
    public EmployeeRepository employeeRepository(EntityManagerFactory emf) {
        EntityManager em = SharedEntityManagerCreator.createSharedEntityManager(emf);
        return new JpaRepositoryFactory(em).getRepository(EmployeeRepository.class);
    }

    @Bean
    public DepartmentRepository departmentRepository(EntityManagerFactory emf) {
        EntityManager em = SharedEntityManagerCreator.createSharedEntityManager(emf);
        return new JpaRepositoryFactory(em).getRepository(DepartmentRepository.class);
    }

    @Bean
    public SkillRepository skillRepository(EntityManagerFactory emf) {
        EntityManager em = SharedEntityManagerCreator.createSharedEntityManager(emf);
        return new JpaRepositoryFactory(em).getRepository(SkillRepository.class);
    }

    @Bean
    public CommandLineRunner initData(
            CountryService countryService,
            StockRepository stockRepository,
            EmployeeService employeeService,
            DepartmentService departmentService,
            SkillService skillService,
            DepartmentRepository departmentRepository,
            EmployeeRepository employeeRepository,
            SkillRepository skillRepository,
            EntityManagerFactory emf,
            PlatformTransactionManager txManager) {
        return args -> {
            TransactionTemplate transactionTemplate = new TransactionTemplate(txManager);
            transactionTemplate.execute(status -> {
                LOGGER.info("Inside initData CommandLineRunner execution");
                EntityManager em = SharedEntityManagerCreator.createSharedEntityManager(emf);

                seedStockData(stockRepository);
                seedPayrollData(em, departmentRepository, employeeRepository, skillRepository);
                em.flush();
                em.clear();

                LOGGER.info("===== HANDS-ON 1 (Country Query Methods) =====");
                testCountryQueries(countryService);

                LOGGER.info("===== HANDS-ON 2 (Stock Query Methods) =====");
                testStockQueries(stockRepository);

                LOGGER.info("===== HANDS-ON 4 & 6 (ManyToOne and ManyToMany) =====");
                testGetEmployee(employeeService);
                testAddEmployee(employeeService, departmentService);
                testUpdateEmployee(employeeService, departmentService);

                LOGGER.info("===== HANDS-ON 5 (OneToMany Eager Fetch) =====");
                testGetDepartment(departmentService);

                LOGGER.info("===== HANDS-ON 6 Add Skill to Employee =====");
                testAddSkillToEmployee(employeeService, skillService);

                return null;
            });
        };
    }

    private static Date parseDate(String dateStr) {
        try {
            if (dateStr.contains("-")) {
                return new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
            } else if (dateStr.contains("/")) {
                return new SimpleDateFormat("MM/dd/yyyy").parse(dateStr);
            }
            return new SimpleDateFormat("dd-MMM-yy").parse(dateStr);
        } catch (Exception e) {
            throw new RuntimeException("Date parse error: " + dateStr, e);
        }
    }

    private static void seedStockData(StockRepository stockRepository) {
        File csvFile = new File("C:\\Users\\logav\\Downloads\\spring-data-jpa-files\\spring-data-jpa-files\\stock-data.csv");
        if (!csvFile.exists()) {
            csvFile = new File("C:\\Users\\logav\\Downloads\\stock-data.csv");
        }
        if (csvFile.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
                String line;
                br.readLine();
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length >= 5) {
                        Stock stock = new Stock();
                        stock.setDate(parseDate(parts[0].trim()));
                        stock.setCode(parts[1].trim());
                        stock.setOpen(new BigDecimal(parts[2].trim()));
                        stock.setClose(new BigDecimal(parts[3].trim()));
                        stock.setVolume(Long.parseLong(parts[4].trim()));
                        stockRepository.save(stock);
                    }
                }
                LOGGER.info("Seeded stock data from CSV file successfully.");
                return;
            } catch (Exception e) {
                LOGGER.warn("Failed to parse stock-data.csv: " + e.getMessage());
            }
        }
        LOGGER.info("CSV file not found or unreadable. Seeding mock stock data...");
        seedMockStocks(stockRepository);
    }

    private static void seedMockStocks(StockRepository stockRepository) {
        stockRepository.save(new Stock(null, "FB", parseDate("2019-09-03"), new BigDecimal("184.00"), new BigDecimal("182.39"), 9779400L));
        stockRepository.save(new Stock(null, "FB", parseDate("2019-09-04"), new BigDecimal("184.65"), new BigDecimal("187.14"), 11308000L));
        stockRepository.save(new Stock(null, "FB", parseDate("2019-09-05"), new BigDecimal("188.53"), new BigDecimal("190.90"), 13876700L));
        stockRepository.save(new Stock(null, "FB", parseDate("2019-09-06"), new BigDecimal("190.21"), new BigDecimal("187.49"), 15226800L));
        stockRepository.save(new Stock(null, "FB", parseDate("2019-09-09"), new BigDecimal("187.73"), new BigDecimal("188.76"), 14722400L));
        stockRepository.save(new Stock(null, "FB", parseDate("2019-09-10"), new BigDecimal("187.44"), new BigDecimal("186.17"), 15455900L));
        stockRepository.save(new Stock(null, "FB", parseDate("2019-09-11"), new BigDecimal("186.46"), new BigDecimal("188.49"), 11761700L));
        stockRepository.save(new Stock(null, "FB", parseDate("2019-09-12"), new BigDecimal("189.86"), new BigDecimal("187.47"), 11419800L));
        stockRepository.save(new Stock(null, "FB", parseDate("2019-09-13"), new BigDecimal("187.33"), new BigDecimal("187.19"), 11441100L));
        stockRepository.save(new Stock(null, "FB", parseDate("2019-09-16"), new BigDecimal("186.93"), new BigDecimal("186.22"), 8444800L));
        stockRepository.save(new Stock(null, "FB", parseDate("2019-09-17"), new BigDecimal("186.66"), new BigDecimal("188.08"), 9671100L));
        stockRepository.save(new Stock(null, "FB", parseDate("2019-09-18"), new BigDecimal("188.09"), new BigDecimal("188.14"), 9681900L));
        stockRepository.save(new Stock(null, "FB", parseDate("2019-09-19"), new BigDecimal("188.66"), new BigDecimal("190.14"), 10392700L));
        stockRepository.save(new Stock(null, "FB", parseDate("2019-09-20"), new BigDecimal("190.66"), new BigDecimal("189.93"), 19934200L));
        stockRepository.save(new Stock(null, "FB", parseDate("2019-09-23"), new BigDecimal("189.34"), new BigDecimal("186.82"), 13327600L));
        stockRepository.save(new Stock(null, "FB", parseDate("2019-09-24"), new BigDecimal("187.98"), new BigDecimal("181.28"), 18546600L));
        stockRepository.save(new Stock(null, "FB", parseDate("2019-09-25"), new BigDecimal("181.45"), new BigDecimal("182.80"), 18068300L));
        stockRepository.save(new Stock(null, "FB", parseDate("2019-09-26"), new BigDecimal("181.33"), new BigDecimal("180.11"), 16083300L));
        stockRepository.save(new Stock(null, "FB", parseDate("2019-09-27"), new BigDecimal("180.49"), new BigDecimal("177.10"), 14656200L));

        stockRepository.save(new Stock(null, "GOOGL", parseDate("2019-04-22"), new BigDecimal("1236.67"), new BigDecimal("1253.76"), 954200L));
        stockRepository.save(new Stock(null, "GOOGL", parseDate("2019-04-23"), new BigDecimal("1256.64"), new BigDecimal("1270.59"), 1593400L));
        stockRepository.save(new Stock(null, "GOOGL", parseDate("2019-04-24"), new BigDecimal("1270.59"), new BigDecimal("1260.05"), 1169800L));
        stockRepository.save(new Stock(null, "GOOGL", parseDate("2019-04-25"), new BigDecimal("1270.30"), new BigDecimal("1267.34"), 1567200L));
        stockRepository.save(new Stock(null, "GOOGL", parseDate("2019-04-26"), new BigDecimal("1273.38"), new BigDecimal("1277.42"), 1361400L));
        stockRepository.save(new Stock(null, "GOOGL", parseDate("2019-04-29"), new BigDecimal("1280.51"), new BigDecimal("1296.20"), 3618400L));
        stockRepository.save(new Stock(null, "GOOGL", parseDate("2019-10-17"), new BigDecimal("1251.40"), new BigDecimal("1252.80"), 1047900L));

        stockRepository.save(new Stock(null, "FB", parseDate("2019-01-31"), new BigDecimal("165.60"), new BigDecimal("166.69"), 77233600L));
        stockRepository.save(new Stock(null, "FB", parseDate("2018-10-31"), new BigDecimal("155.00"), new BigDecimal("151.79"), 60101300L));
        stockRepository.save(new Stock(null, "FB", parseDate("2018-12-19"), new BigDecimal("141.21"), new BigDecimal("133.24"), 57404900L));

        stockRepository.save(new Stock(null, "NFLX", parseDate("2018-12-24"), new BigDecimal("242.00"), new BigDecimal("233.88"), 9547600L));
        stockRepository.save(new Stock(null, "NFLX", parseDate("2018-12-21"), new BigDecimal("263.83"), new BigDecimal("246.39"), 21397600L));
        stockRepository.save(new Stock(null, "NFLX", parseDate("2018-12-26"), new BigDecimal("233.92"), new BigDecimal("253.67"), 14402700L));
    }

    private static void seedPayrollData(
            EntityManager em,
            DepartmentRepository departmentRepository,
            EmployeeRepository employeeRepository,
            SkillRepository skillRepository) {
        File sqlFile = new File("C:\\Users\\logav\\Downloads\\spring-data-jpa-files\\spring-data-jpa-files\\payroll.sql");
        if (!sqlFile.exists()) {
            sqlFile = new File("C:\\Users\\logav\\Downloads\\payroll.sql");
        }
        if (sqlFile.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(sqlFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    line = line.trim();
                    if (line.toLowerCase().startsWith("insert into") || line.toLowerCase().startsWith("insert  into")) {
                        if (line.endsWith(";")) {
                            line = line.substring(0, line.length() - 1);
                        }
                        em.createNativeQuery(line).executeUpdate();
                    }
                }
                LOGGER.info("Seeded payroll data from SQL script successfully.");
                return;
            } catch (Exception e) {
                LOGGER.warn("Failed to parse and execute payroll.sql: " + e.getMessage());
            }
        }
        LOGGER.info("SQL file not found or unreadable. Seeding mock payroll data...");
        seedMockPayroll(departmentRepository, employeeRepository, skillRepository);
    }

    private static void seedMockPayroll(
            DepartmentRepository departmentRepository,
            EmployeeRepository employeeRepository,
            SkillRepository skillRepository) {
        Department dept1 = new Department(1, "IT", null);
        Department dept2 = new Department(2, "HR", null);
        departmentRepository.save(dept1);
        departmentRepository.save(dept2);

        Skill sk1 = new Skill(1, "Java", null);
        Skill sk2 = new Skill(2, "Spring Boot", null);
        Skill sk3 = new Skill(3, "SQL", null);
        skillRepository.save(sk1);
        skillRepository.save(sk2);
        skillRepository.save(sk3);

        Employee emp1 = new Employee(1, "John", 50000.0, true, parseDate("1995-10-15"), dept1, Set.of(sk1, sk2, sk3));
        Employee emp2 = new Employee(2, "Alice", 65000.0, true, parseDate("1992-04-20"), dept1, Set.of(sk1, sk3));
        Employee emp3 = new Employee(3, "Bob", 45000.0, false, parseDate("1998-08-05"), dept2, Set.of(sk3));
        employeeRepository.save(emp1);
        employeeRepository.save(emp2);
        employeeRepository.save(emp3);
    }

    private static void testCountryQueries(CountryService countryService) {
        List<Country> ouCountries = countryService.findCountryByPartialName("ou");
        LOGGER.info("Countries containing 'ou': {}", ouCountries);

        List<Country> ouCountriesSorted = countryService.findCountryByPartialNameSorted("ou");
        LOGGER.info("Countries containing 'ou' sorted: {}", ouCountriesSorted);

        List<Country> zCountries = countryService.findCountryByStartingAlphabet("Z");
        LOGGER.info("Countries starting with 'Z': {}", zCountries);
    }

    private static void testStockQueries(StockRepository stockRepository) {
        List<Stock> fbSepStock = stockRepository.findByCodeAndDateBetween("FB", parseDate("2019-09-01"), parseDate("2019-09-30"));
        LOGGER.info("Facebook stocks in Sept 2019: {}", fbSepStock);

        List<Stock> googleGtPrice = stockRepository.findByCodeAndCloseGreaterThan("GOOGL", new BigDecimal("1250"));
        LOGGER.info("Google stocks with close price > 1250: {}", googleGtPrice);

        List<Stock> top3Volume = stockRepository.findTop3ByOrderByVolumeDesc();
        LOGGER.info("Top 3 volume stock transactions: {}", top3Volume);

        List<Stock> lowestNflx = stockRepository.findTop3ByCodeOrderByCloseAsc("NFLX");
        LOGGER.info("Top 3 lowest Netflix stock transactions: {}", lowestNflx);
    }

    private static void testGetEmployee(EmployeeService employeeService) {
        LOGGER.info("Start testGetEmployee");
        Employee employee = employeeService.get(1);
        LOGGER.info("Employee: {}", employee);
        LOGGER.info("Department: {}", employee.getDepartment());
        LOGGER.info("Skills: {}", employee.getSkillList());
        LOGGER.info("End testGetEmployee");
    }

    private static void testAddEmployee(EmployeeService employeeService, DepartmentService departmentService) {
        LOGGER.info("Start testAddEmployee");
        Department dept = departmentService.get(1);
        Employee emp = new Employee();
        emp.setName("Charlie");
        emp.setSalary(70000.0);
        emp.setPermanent(true);
        emp.setDateOfBirth(parseDate("1996-03-12"));
        emp.setDepartment(dept);
        employeeService.save(emp);
        LOGGER.info("Added employee successfully, ID: {}", emp.getId());
        LOGGER.info("End testAddEmployee");
    }

    private static void testUpdateEmployee(EmployeeService employeeService, DepartmentService departmentService) {
        LOGGER.info("Start testUpdateEmployee");
        Employee employee = employeeService.get(1);
        Department dept2 = departmentService.get(2);
        employee.setDepartment(dept2);
        employeeService.save(employee);
        LOGGER.info("Updated employee department successfully.");
        LOGGER.info("End testUpdateEmployee");
    }

    private static void testGetDepartment(DepartmentService departmentService) {
        LOGGER.info("Start testGetDepartment");
        Department department = departmentService.get(1);
        LOGGER.info("Department: {}", department);
        LOGGER.info("Employees in Department: {}", department.getEmployeeList());
        LOGGER.info("End testGetDepartment");
    }

    private static void testAddSkillToEmployee(EmployeeService employeeService, SkillService skillService) {
        LOGGER.info("Start testAddSkillToEmployee");
        Employee employee = employeeService.get(3);
        Skill skill = skillService.get(1);
        Set<Skill> skillList = employee.getSkillList();
        skillList.add(skill);
        employee.setSkillList(skillList);
        employeeService.save(employee);
        LOGGER.info("Added skill to employee successfully.");
        LOGGER.info("End testAddSkillToEmployee");
    }
}
