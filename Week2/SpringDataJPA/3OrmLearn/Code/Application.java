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
    EmployeeService.class, EmployeeController.class,
    DepartmentService.class, SkillService.class,
    AttemptService.class, QuizController.class
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
            pui.addManagedClassName("User");
            pui.addManagedClassName("Attempt");
            pui.addManagedClassName("Question");
            pui.addManagedClassName("Option");
            pui.addManagedClassName("AttemptQuestion");
            pui.addManagedClassName("AttemptOption");
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
    public AttemptRepository attemptRepository(EntityManagerFactory emf) {
        EntityManager em = SharedEntityManagerCreator.createSharedEntityManager(emf);
        return new JpaRepositoryFactory(em).getRepository(AttemptRepository.class);
    }

    @Bean
    public UserRepository userRepository(EntityManagerFactory emf) {
        EntityManager em = SharedEntityManagerCreator.createSharedEntityManager(emf);
        return new JpaRepositoryFactory(em).getRepository(UserRepository.class);
    }

    @Bean
    public QuestionRepository questionRepository(EntityManagerFactory emf) {
        EntityManager em = SharedEntityManagerCreator.createSharedEntityManager(emf);
        return new JpaRepositoryFactory(em).getRepository(QuestionRepository.class);
    }

    @Bean
    public OptionRepository optionRepository(EntityManagerFactory emf) {
        EntityManager em = SharedEntityManagerCreator.createSharedEntityManager(emf);
        return new JpaRepositoryFactory(em).getRepository(OptionRepository.class);
    }

    @Bean
    public AttemptQuestionRepository attemptQuestionRepository(EntityManagerFactory emf) {
        EntityManager em = SharedEntityManagerCreator.createSharedEntityManager(emf);
        return new JpaRepositoryFactory(em).getRepository(AttemptQuestionRepository.class);
    }

    @Bean
    public AttemptOptionRepository attemptOptionRepository(EntityManagerFactory emf) {
        EntityManager em = SharedEntityManagerCreator.createSharedEntityManager(emf);
        return new JpaRepositoryFactory(em).getRepository(AttemptOptionRepository.class);
    }

    @Bean
    public CommandLineRunner initData(
            CountryService countryService,
            StockRepository stockRepository,
            EmployeeService employeeService,
            DepartmentService departmentService,
            SkillService skillService,
            AttemptService attemptService,
            DepartmentRepository departmentRepository,
            EmployeeRepository employeeRepository,
            SkillRepository skillRepository,
            UserRepository userRepository,
            QuestionRepository questionRepository,
            OptionRepository optionRepository,
            AttemptRepository attemptRepository,
            AttemptQuestionRepository attemptQuestionRepository,
            AttemptOptionRepository attemptOptionRepository,
            EntityManagerFactory emf,
            PlatformTransactionManager txManager) {
        return args -> {
            TransactionTemplate transactionTemplate = new TransactionTemplate(txManager);
            transactionTemplate.execute(status -> {
                LOGGER.info("Inside initData CommandLineRunner execution");
                EntityManager em = SharedEntityManagerCreator.createSharedEntityManager(emf);

                seedStockData(stockRepository);
                seedPayrollData(em, departmentRepository, employeeRepository, skillRepository);
                seedQuizData(userRepository, questionRepository, optionRepository, attemptRepository, attemptQuestionRepository, attemptOptionRepository);
                em.flush();
                em.clear();

                LOGGER.info("===== HANDS-ON 2 (Get all permanent employees using HQL fetch query) =====");
                testGetAllPermanentEmployees(employeeService);

                LOGGER.info("===== HANDS-ON 3 (Fetch Quiz Attempt Details using HQL fetch) =====");
                testGetAttemptDetails(attemptService);

                LOGGER.info("===== HANDS-ON 4 (Get Average Salary using HQL aggregate query) =====");
                testGetAverageSalary(employeeService);

                LOGGER.info("===== HANDS-ON 5 (Get All Employees using Native Query) =====");
                testGetAllEmployeesNative(employeeService);

                LOGGER.info("===== HANDS-ON 6 (Criteria Query Dynamic Search) =====");
                testCriteriaQuery(employeeService);

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

    private static void seedQuizData(
            UserRepository userRepository,
            QuestionRepository questionRepository,
            OptionRepository optionRepository,
            AttemptRepository attemptRepository,
            AttemptQuestionRepository attemptQuestionRepository,
            AttemptOptionRepository attemptOptionRepository) {
        User user = new User(1, "John Doe", null);
        userRepository.save(user);

        Question q1 = new Question(1, "What is the extension of the hyper text markup language file?", null);
        questionRepository.save(q1);
        Option o1_1 = new Option(1, ".xhtm", 0.0, q1);
        Option o1_2 = new Option(2, ".ht", 0.0, q1);
        Option o1_3 = new Option(3, ".html", 1.0, q1);
        Option o1_4 = new Option(4, ".htmx", 0.0, q1);
        optionRepository.save(o1_1);
        optionRepository.save(o1_2);
        optionRepository.save(o1_3);
        optionRepository.save(o1_4);

        Question q2 = new Question(2, "What is the maximum level of heading tag can be used in a HTML page?", null);
        questionRepository.save(q2);
        Option o2_1 = new Option(5, "5", 0.0, q2);
        Option o2_2 = new Option(6, "3", 0.0, q2);
        Option o2_3 = new Option(7, "4", 0.0, q2);
        Option o2_4 = new Option(8, "6", 1.0, q2);
        optionRepository.save(o2_1);
        optionRepository.save(o2_2);
        optionRepository.save(o2_3);
        optionRepository.save(o2_4);

        Question q3 = new Question(3, "The HTML document itself begins with <html> and ends </html>. State True of False", null);
        questionRepository.save(q3);
        Option o3_1 = new Option(9, "false", 0.0, q3);
        Option o3_2 = new Option(10, "true", 1.0, q3);
        optionRepository.save(o3_1);
        optionRepository.save(o3_2);

        Question q4 = new Question(4, "Choose the right option to store text value value in a variable", null);
        questionRepository.save(q4);
        Option o4_1 = new Option(11, "'John'", 0.5, q4);
        Option o4_2 = new Option(12, "John", 0.0, q4);
        Option o4_3 = new Option(13, "\"John\"", 0.5, q4);
        Option o4_4 = new Option(14, "/John/", 0.0, q4);
        optionRepository.save(o4_1);
        optionRepository.save(o4_2);
        optionRepository.save(o4_3);
        optionRepository.save(o4_4);

        Attempt attempt = new Attempt(1, new Date(), user, null);
        attemptRepository.save(attempt);

        AttemptQuestion aq1 = new AttemptQuestion(1, attempt, q1, null);
        attemptQuestionRepository.save(aq1);
        attemptOptionRepository.save(new AttemptOption(1, false, aq1, o1_1));
        attemptOptionRepository.save(new AttemptOption(2, false, aq1, o1_2));
        attemptOptionRepository.save(new AttemptOption(3, true, aq1, o1_3));
        attemptOptionRepository.save(new AttemptOption(4, false, aq1, o1_4));

        AttemptQuestion aq2 = new AttemptQuestion(2, attempt, q2, null);
        attemptQuestionRepository.save(aq2);
        attemptOptionRepository.save(new AttemptOption(5, false, aq2, o2_1));
        attemptOptionRepository.save(new AttemptOption(6, true, aq2, o2_2));
        attemptOptionRepository.save(new AttemptOption(7, false, aq2, o2_3));
        attemptOptionRepository.save(new AttemptOption(8, false, aq2, o2_4));

        AttemptQuestion aq3 = new AttemptQuestion(3, attempt, q3, null);
        attemptQuestionRepository.save(aq3);
        attemptOptionRepository.save(new AttemptOption(9, false, aq3, o3_1));
        attemptOptionRepository.save(new AttemptOption(10, true, aq3, o3_2));

        AttemptQuestion aq4 = new AttemptQuestion(4, attempt, q4, null);
        attemptQuestionRepository.save(aq4);
        attemptOptionRepository.save(new AttemptOption(11, true, aq4, o4_1));
        attemptOptionRepository.save(new AttemptOption(12, false, aq4, o4_2));
        attemptOptionRepository.save(new AttemptOption(13, false, aq4, o4_3));
        attemptOptionRepository.save(new AttemptOption(14, false, aq4, o4_4));
    }

    private static void testGetAllPermanentEmployees(EmployeeService employeeService) {
        LOGGER.info("Start");
        List<Employee> employees = employeeService.getAllPermanentEmployees();
        LOGGER.info("Permanent Employees: {}", employees);
        employees.forEach(e -> LOGGER.info("Skills: {}", e.getSkillList()));
        LOGGER.info("End");
    }

    private static void testGetAttemptDetails(AttemptService attemptService) {
        LOGGER.info("Start testGetAttemptDetails");
        Attempt attempt = attemptService.getAttempt(1, 1);
        if (attempt != null) {
            for (AttemptQuestion aq : attempt.getAttemptQuestions()) {
                LOGGER.info("{}", aq.getQuestion().getText());
                int index = 1;
                for (AttemptOption ao : aq.getAttemptOptions()) {
                    LOGGER.info(" {}) {}\t{}\t{}", index++, ao.getOption().getText(), ao.getOption().getScore(), ao.isSelected());
                }
                LOGGER.info("");
            }
        }
        LOGGER.info("End testGetAttemptDetails");
    }

    private static void testGetAverageSalary(EmployeeService employeeService) {
        LOGGER.info("Start");
        double averageSalary = employeeService.getAverageSalary(1);
        LOGGER.info("Average Salary for Dept 1: {}", averageSalary);
        LOGGER.info("End");
    }

    private static void testGetAllEmployeesNative(EmployeeService employeeService) {
        LOGGER.info("Start");
        List<Employee> list = employeeService.getAllEmployeesNative();
        LOGGER.info("Native Query All Employees: {}", list);
        LOGGER.info("End");
    }

    private static void testCriteriaQuery(EmployeeService employeeService) {
        LOGGER.info("Start");
        List<Employee> criteriaResult = employeeService.findEmployeesByCriteria(true, "John");
        LOGGER.info("Criteria query search results: {}", criteriaResult);
        LOGGER.info("End");
    }
}
