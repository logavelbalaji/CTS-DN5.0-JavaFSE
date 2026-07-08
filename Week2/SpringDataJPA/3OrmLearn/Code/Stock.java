import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "stock")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "st_id")
    private Integer id;

    @Column(name = "st_code", length = 10)
    private String code;

    @Column(name = "st_date")
    private Date date;

    @Column(name = "st_open", precision = 10, scale = 2)
    private BigDecimal open;

    @Column(name = "st_close", precision = 10, scale = 2)
    private BigDecimal close;

    @Column(name = "st_volume")
    private Long volume;
}
