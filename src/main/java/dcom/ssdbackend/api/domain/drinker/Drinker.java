package dcom.ssdbackend.api.domain.drinker;

import dcom.ssdbackend.api.domain.dispenser.Dispenser;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Drinker {
    @Id
    private String id;

    @Column
    private String name;

    @Column
    private String detail;

    @Column
    private Integer totalCapacity;

    @Column
    private Integer currentDrink;

    @Column
    private Integer lastDrinkTimeStamp;

    @ManyToOne
    private Dispenser dispenser;
}
