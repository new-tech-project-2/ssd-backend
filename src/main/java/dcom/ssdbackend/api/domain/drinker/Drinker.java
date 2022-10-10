package dcom.ssdbackend.api.domain.drinker;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Drinker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Column
    private String detail;

    @Column
    private Integer totalCapacity;

    @Column
    private Integer currentDrinks;
}
