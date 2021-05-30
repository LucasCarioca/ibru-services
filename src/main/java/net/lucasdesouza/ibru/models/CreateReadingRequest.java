package net.lucasdesouza.ibru.models;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class CreateReadingRequest {
    private String brewId;
    private Double gravity;
}
