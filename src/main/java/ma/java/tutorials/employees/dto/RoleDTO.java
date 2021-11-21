package ma.java.tutorials.employees.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter @NoArgsConstructor
public class RoleDTO implements Serializable {

    private Long id;

    private String role = "user";
}
