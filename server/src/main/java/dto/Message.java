package dto;

import base_repo.models.Entity;
import lombok.Value;

@Value
public class Message extends Entity {
    String message;
}
