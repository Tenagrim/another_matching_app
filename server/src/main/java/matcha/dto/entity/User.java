package matcha.dto.entity;

import matcha.base_repo.annotations.Column;
import matcha.base_repo.annotations.Id;
import matcha.base_repo.annotations.Table;
import matcha.base_repo.annotations.Transient;
import matcha.base_repo.models.Entity;
import lombok.Data;

@Data
@Table(name = "users")
public class User extends Entity {
    @Id
    @Column(name = "user_id")
    Integer id;
    @Column(name = "user_auth_id")
    Integer userAuthId;
    @Column(name = "first_name")
    String firstName;
    @Column(name = "last_name")
    String lastName;
    String password;
    String email;

    @Transient
    UserAuth userAuth;

}
