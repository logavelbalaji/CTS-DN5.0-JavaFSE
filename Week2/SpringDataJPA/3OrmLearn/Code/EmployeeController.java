import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employees/permanent")
    public List<Employee> getPermanentEmployees() {
        return employeeService.getAllPermanentEmployees();
    }

    @GetMapping("/employees/average-salary/{deptId}")
    public double getAverageSalary(@PathVariable int deptId) {
        return employeeService.getAverageSalary(deptId);
    }

    @GetMapping("/employees/native")
    public List<Employee> getEmployeesNative() {
        return employeeService.getAllEmployeesNative();
    }

    @GetMapping("/employees/criteria")
    public List<Employee> getEmployeesByCriteria(
            @RequestParam(required = false) Boolean permanent,
            @RequestParam(required = false) String name) {
        return employeeService.findEmployeesByCriteria(permanent, name);
    }
}
