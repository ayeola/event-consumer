package com.saltech.event_consumer.response;

import com.saltech.event_consumer.service.HttpHeader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends HttpHeader {

    @Autowired
    private ResponseBuilder responseBuilder;

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<?> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) throws IOException {

        try {
            BaseResponse response = new BaseResponse();
            response.setStatusMassage(ex.getMessage());
            response.setStatus("400");
            return responseBuilder.buildResponse(getHeader(),
                    HttpStatus.OK.value(), "Successful", response);
        } catch (Exception ex2) {
            BaseResponse response = new BaseResponse();
            response.setStatusMassage(ex.getMessage());
            response.setStatus("06");
            return responseBuilder.buildResponse(getHeader(),
                    HttpStatus.OK.value(), "Successful", response);
        }
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<?> missingServletRequestParameterException(MissingServletRequestParameterException ex) throws IOException {

        try {
            BaseResponse response = new BaseResponse();
            response.setStatusMassage(ex.getMessage());
            response.setStatus("400");
            return responseBuilder.buildResponse(getHeader(),
                    HttpStatus.OK.value(), "Successful", response);
        } catch (Exception ex2) {
            BaseResponse response = new BaseResponse();
            response.setStatusMassage(ex.getMessage());
            response.setStatus("06");
            return responseBuilder.buildResponse(getHeader(),
                    HttpStatus.OK.value(), "Successful", response);
        }
    }
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<?> newNullPointerException(NullPointerException ex) throws IOException {

        try {
            BaseResponse response = new BaseResponse();
            response.setStatusMassage("Null value Not Allowed");
            response.setStatus("06");
            return responseBuilder.buildResponse(getHeader(),
                    HttpStatus.OK.value(), "Successful", response);
        } catch (Exception ex2) {
            BaseResponse response = new BaseResponse();
            response.setStatusMassage(ex.getMessage());
            response.setStatus("06");
            return responseBuilder.buildResponse(getHeader(),
                    HttpStatus.OK.value(), "Successful", response);
        }
    }

    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<ApiResponse> duplicateKeyException(DuplicateKeyException ex)
            throws IOException {

        try {
            BaseResponse response = new BaseResponse();
            response.setStatusMassage(ex.getMessage());
            response.setStatus("06");
            return responseBuilder.buildResponse(getHeader(),
                    HttpStatus.OK.value(), "Successful", response);
        } catch (Exception ex2) {
            BaseResponse response = new BaseResponse();
            response.setStatus("06");
            response.setStatusMassage(ex.getMessage());
            return responseBuilder.buildResponse(getHeader(),
                    HttpStatus.OK.value(), "Successful", response);
        }
    }

    @ExceptionHandler(ArrayIndexOutOfBoundsException.class)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<ApiResponse> arrayIndexOutOfBoundsException(
            ArrayIndexOutOfBoundsException ex) throws IOException {

        try {
            BaseResponse response = new BaseResponse();
            response.setStatusMassage(ex.getMessage());
            response.setStatus("06");
            ResponseEntity<ApiResponse> responseEntity = responseBuilder.buildResponse(getHeader(),
                    HttpStatus.OK.value(), "Successful", response);
            return responseEntity;
        } catch (Exception ex2) {
            BaseResponse response = new BaseResponse();
            response.setStatus("06");
            response.setStatusMassage(ex.getMessage());
            ResponseEntity<ApiResponse> responseEntity = responseBuilder.buildResponse(getHeader(),
                    HttpStatus.OK.value(), "Successful", response);
            return responseEntity;
        }
    }

}
