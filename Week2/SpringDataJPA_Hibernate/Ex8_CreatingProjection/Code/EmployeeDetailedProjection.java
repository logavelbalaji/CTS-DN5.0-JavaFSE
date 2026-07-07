import org.springframework.beans.factory.annotation.Value;

public interface EmployeeDetailedProjection {
    Long getId();
    String getName();
    String getEmail();
    
    @Value("#{target.department != null ? target.department.name : 'No Department'}")
    String getDepartmentName();
}
