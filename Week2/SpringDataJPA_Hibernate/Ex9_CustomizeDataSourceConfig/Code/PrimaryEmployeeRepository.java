import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PrimaryEmployeeRepository extends JpaRepository<PrimaryEmployee, Long> {
    List<PrimaryEmployee> findByName(String name);
}
