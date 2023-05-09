package com.epam.esm.handler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
public class ResponseMessage {

        private String errorMessage;
        private HttpStatus statusCode;

//        private String stackTrace;
//        private List<ValidationError> errors;
}
