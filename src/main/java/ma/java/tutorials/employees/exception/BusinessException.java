package ma.java.tutorials.employees.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor @Getter @Setter
public class BusinessException extends Exception {

    private String message;

    private HttpStatus httpStatus;
}
