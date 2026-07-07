import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private PrimaryEmployeeRepository primaryRepository;

    @Autowired
    private SecondaryEmployeeRepository secondaryRepository;

    @GetMapping("/primary")
    public List<PrimaryEmployee> getPrimaryEmployees() {
        return primaryRepository.findAll();
    }

    @GetMapping("/secondary")
    public List<SecondaryEmployee> getSecondaryEmployees() {
        return secondaryRepository.findAll();
    }
}
