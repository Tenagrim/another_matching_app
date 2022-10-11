package matcha.dto.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import matcha.base_repo.annotations.Column;
import matcha.base_repo.annotations.Id;
import matcha.base_repo.annotations.Table;
import matcha.base_repo.models.Entity;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_auth")
public class UserAuth extends Entity {
    @Id
    @Column(name = "user_auth_id")
    Long id;
    String token;
    Date date;

    public UserAuth(String token) {
        this.token = token;
    }

}
