package org.mansar.fileq.api;

import lombok.extern.slf4j.Slf4j;
import org.mansar.fileq.exceptions.FileQException;
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


    @org.springframework.web.bind.annotation.ExceptionHandler(FileQException.class)
    public ProblemDetail handleException(FileQException exception) {
        log.error(exception.getMessage(), exception);
        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
    }
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ProblemDetail handleException(Exception exception) {
        log.error(exception.getMessage(), exception);
        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
    }
}
