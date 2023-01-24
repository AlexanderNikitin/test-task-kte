package an.kte.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Builder
@Data
@Entity
@Table(schema = "public")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Product {
    @Id
    @GeneratedValue
    protected Long id;
    @Column(length = 50)
    protected String name;
    @Column(length = 100)
    protected String description;
    protected Long price; //Копейки
}
