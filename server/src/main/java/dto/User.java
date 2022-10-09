package dto;

import base_repo.models.Entity;
import lombok.Data;

@Data
public class User extends Entity {
    String id;
    String name;
    String surname;
}
