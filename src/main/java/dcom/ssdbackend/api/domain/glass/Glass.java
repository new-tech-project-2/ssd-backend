package dcom.ssdbackend.api.domain.glass;

import dcom.ssdbackend.api.domain.dispenser.Dispenser;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Glass {
    @Id
    private String id;

    @Column
    private String drinkerName;

    @Column
    private String detail;

    @Column
    private Integer totalCapacity;

    @Column
    private Integer currentDrink;

    @Column
    private Long lastDrinkTimeStamp;

    @ManyToOne
    private Dispenser dispenser;
}
