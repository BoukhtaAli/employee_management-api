package ma.java.tutorials.employees.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor @Getter @Setter
public class BusinessException extends Exception {

    private String message;
}
