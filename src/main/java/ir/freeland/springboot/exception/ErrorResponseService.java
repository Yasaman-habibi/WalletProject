package ir.freeland.springboot.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import java.util.HashMap;
import java.util.Map;

@Service
public class ErrorResponseService {
	

    private static final Logger logger = LoggerFactory.getLogger(ErrorResponseService.class);

    
    public ResponseEntity<Object> returnValidationError(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : result.getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
            logger.error("Validation error in field: {} - {}", error.getField(), error.getDefaultMessage());
        }
        
        ErrorResponse errorResponse = new ErrorResponse("Validation Failed", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    
    
    
    public static class ErrorResponse {
        private String message;
        private Map<String, String> details;

        public ErrorResponse(String message, Map<String, String> details) {
            this.message = message;
            this.details = details;
        }

        
        
        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        
        
        public Map<String, String> getDetails() {
            return details;
        }

        public void setDetails(Map<String, String> details) {
            this.details = details;
        }
    }
}

