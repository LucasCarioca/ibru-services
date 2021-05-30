package net.lucasdesouza.ibru.models;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class RegisterUserRequest {
    private String email;
    private String password;
}
