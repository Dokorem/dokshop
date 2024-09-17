package dok.dokshop.catalogueservice.controller.exceptions;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.validation.BindException;
import java.util.Locale;

@ControllerAdvice
@RequiredArgsConstructor
public class BadRequestBindControllerAdvice {

    private final MessageSource messageSource;

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ProblemDetail> bindException(BindException exception, Locale locale) {
        ProblemDetail problemDetail = ProblemDetail
                .forStatusAndDetail(HttpStatus.BAD_REQUEST,
                messageSource.getMessage("error.productNotFound", new Object[0],
                        "error.productNotFound", locale));
        problemDetail.setProperty("errors", exception.getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .toList());

        return ResponseEntity.badRequest()
                .body(problemDetail);
    }

}
