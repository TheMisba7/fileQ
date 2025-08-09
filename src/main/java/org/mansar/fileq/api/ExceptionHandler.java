package org.mansar.fileq.api;

import lombok.extern.slf4j.Slf4j;
import org.mansar.fileq.exceptions.UnSupportedFileType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
@Slf4j
public class ExceptionHandler {


    @org.springframework.web.bind.annotation.ExceptionHandler(UnSupportedFileType.class)
    public ProblemDetail handlerUnSupportedFileType(UnSupportedFileType unSupportedFileType) {
        log.error(unSupportedFileType.getMessage(), unSupportedFileType);
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, unSupportedFileType.getMessage());
    }
}
