package net.lucasdesouza.ibru.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Data
@Entity(name="readings")
@Table(name="readings")
public class Reading {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="brew_id", referencedColumnName = "id")
    @JsonIgnore
    private Brew brew;

    private Double gravity;
    private Date date;
    @Column(name="starting_gravity")
    private Boolean start = false;
    @Column(name="ending_gravity")
    private Boolean end = false;
}