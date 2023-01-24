package an.kte.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Builder
@Data
@Entity
@Table(schema = "public")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Client implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    @Column(length = 50)
    private String name;
    private Double discount1;
    private Double discount2;
}
