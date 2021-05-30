package net.lucasdesouza.ibru.models;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class CreateBrewRequest {
    private String name;
    private String details;
    private Double startingGravity;
}
