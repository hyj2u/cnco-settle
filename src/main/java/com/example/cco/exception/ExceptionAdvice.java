package com.example.cco.exception;

import com.example.cco.base.ErrorRespDto;
import com.example.cco.base.ErrorWriter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class ExceptionAdvice {
    private final ErrorWriter errorWriter;
    private final ObjectMapper objectMapper;

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception e) {
        log.error(errorWriter.getPrintStackTrace(e));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity(e.getMessage(), headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({DuplicatedException.class})
    public ResponseEntity handleDuplicatedException(DuplicatedException e) throws JsonProcessingException {
        log.error(errorWriter.getPrintStackTrace(e));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity(messageToErrResp(e.getMessage(), "CLIENT_0001"), headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({WrongPasswordException.class})
    public ResponseEntity handleWrongPasswordException(DuplicatedException e) throws JsonProcessingException {
        log.error(errorWriter.getPrintStackTrace(e));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity(messageToErrResp(e.getMessage(), "CLIENT_0002"), headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ExcelFormatException.class})
    public ResponseEntity handleExcelFormatException (ExcelFormatException e) throws JsonProcessingException {
        log.error(errorWriter.getPrintStackTrace(e));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity(messageToErrResp(e.getMessage(), "CLIENT_0003"), headers, HttpStatus.BAD_REQUEST);
    }


    private String messageToErrResp(String msg, String code) throws JsonProcessingException {
        ErrorRespDto errorRespDto = ErrorRespDto.builder()
                .errorCode(code)
                .msg(msg)
                .build();
        return objectMapper.writeValueAsString(errorRespDto);
    }

}
