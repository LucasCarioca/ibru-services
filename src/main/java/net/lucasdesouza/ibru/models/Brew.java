package net.lucasdesouza.ibru.models;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Data
@Entity(name = "brews")
public class Brew {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @OneToMany(mappedBy = "brew", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reading> readings;

    private String name;
    private String details;
    private Date startDate;
    private Date bottleDate;
}
