package an.kte.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Builder
@Data
@Entity
@Table(schema = "public")
public class ProductStatistic {
    @Id
    @GeneratedValue
    private Long id;
    private Long productId;
    private Long orderCount;
    private Long fullPrice;
    private Long fillDiscount;
}
