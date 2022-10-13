package dcom.ssdbackend.api.domain.dispenser;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Dispenser {
    @Id
    private String id;

    @Column
    private boolean started;
}
