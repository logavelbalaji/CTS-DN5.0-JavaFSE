import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;

@SpringBootConfiguration
@EnableAutoConfiguration
@Import({
    PrimaryDbConfig.class,
    SecondaryDbConfig.class,
    EmployeeController.class
})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner initData(
            PrimaryDepartmentRepository primaryDeptRepo,
            PrimaryEmployeeRepository primaryEmpRepo,
            SecondaryEmployeeRepository secondaryEmpRepo,
            @Qualifier("primaryTransactionManager") PlatformTransactionManager primaryTxManager,
            @Qualifier("secondaryTransactionManager") PlatformTransactionManager secondaryTxManager) {
        return args -> {
            new TransactionTemplate(primaryTxManager).execute(status -> {
                PrimaryDepartment hr = new PrimaryDepartment();
                hr.setName("HR");
                primaryDeptRepo.save(hr);
                PrimaryEmployee emp1 = new PrimaryEmployee();
                emp1.setName("Aarav Sharma");
                emp1.setEmail("aarav.sharma@bank.com");
                emp1.setDepartment(hr);
                primaryEmpRepo.save(emp1);
                return null;
            });
            new TransactionTemplate(secondaryTxManager).execute(status -> {
                SecondaryEmployee emp2 = new SecondaryEmployee();
                emp2.setName("Bhavna Patel");
                emp2.setEmail("bhavna.patel@bank.com");
                secondaryEmpRepo.save(emp2);
                return null;
            });
        };
    }
}
