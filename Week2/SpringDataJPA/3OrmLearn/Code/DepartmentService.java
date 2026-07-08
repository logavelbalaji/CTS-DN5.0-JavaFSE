import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DepartmentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentService.class);

    @Autowired
    private DepartmentRepository departmentRepository;

    public Department get(int id) {
        LOGGER.info("Start");
        return departmentRepository.findById(id).orElse(null);
    }

    public void save(Department department) {
        LOGGER.info("Start");
        departmentRepository.save(department);
        LOGGER.info("End");
    }
}
