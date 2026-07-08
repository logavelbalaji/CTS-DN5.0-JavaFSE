import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class EmployeeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @jakarta.persistence.PersistenceContext
    private jakarta.persistence.EntityManager em;

    public Employee get(int id) {
        LOGGER.info("Start");
        return employeeRepository.findById(id).orElse(null);
    }

    public void save(Employee employee) {
        LOGGER.info("Start");
        employeeRepository.save(employee);
        LOGGER.info("End");
    }

    public List<Employee> getAllPermanentEmployees() {
        LOGGER.info("Start");
        return employeeRepository.getAllPermanentEmployees();
    }

    public double getAverageSalary(int id) {
        LOGGER.info("Start");
        return employeeRepository.getAverageSalary(id);
    }

    public List<Employee> getAllEmployeesNative() {
        LOGGER.info("Start");
        return employeeRepository.getAllEmployeesNative();
    }

    public List<Employee> findEmployeesByCriteria(Boolean permanent, String nameKeyword) {
        LOGGER.info("Start Criteria Query");
        jakarta.persistence.criteria.CriteriaBuilder cb = em.getCriteriaBuilder();
        jakarta.persistence.criteria.CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
        jakarta.persistence.criteria.Root<Employee> root = cq.from(Employee.class);
        root.fetch("department", jakarta.persistence.criteria.JoinType.LEFT);
        root.fetch("skillList", jakarta.persistence.criteria.JoinType.LEFT);
        java.util.List<jakarta.persistence.criteria.Predicate> predicates = new java.util.ArrayList<>();
        if (permanent != null) {
            predicates.add(cb.equal(root.get("permanent"), permanent));
        }
        if (nameKeyword != null && !nameKeyword.isEmpty()) {
            predicates.add(cb.like(root.get("name"), "%" + nameKeyword + "%"));
        }
        cq.where(predicates.toArray(new jakarta.persistence.criteria.Predicate[0]));
        cq.distinct(true);
        return em.createQuery(cq).getResultList();
    }
}
