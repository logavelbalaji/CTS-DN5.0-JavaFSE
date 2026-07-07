import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByDepartmentId(Long departmentId);
    List<Employee> findByName(String name);
    Employee findByEmail(String email);

    @Query("SELECT e FROM Employee e WHERE e.name LIKE %:keyword%")
    List<Employee> searchByNameKeyword(@Param("keyword") String keyword);

    @Query(value = "SELECT * FROM employees WHERE email = :email", nativeQuery = true)
    Employee findByEmailNative(@Param("email") String email);

    Employee findByEmailNamed(@Param("email") String email);
}
