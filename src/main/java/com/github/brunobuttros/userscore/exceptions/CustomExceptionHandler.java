package com.github.brunobuttros.userscore.exception;

import com.github.brunobuttros.userscore.dto.ErrorResponseDTO;
import com.github.brunobuttros.userscore.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class CustomExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleException(Exception ex) {
        logger.error("Ocorreu uma exceção não tratada:", ex);
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(LocalDateTime.now(), "Ocorreu um erro interno no servidor.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(LoginOuCPFJaExistenteException.class)
    public ResponseEntity<ErrorResponseDTO> handleLoginOuCPFJaExistenteException(LoginOuCPFJaExistenteException ex) {
        logger.error("Erro ao cadastrar usuário: {}", ex.getMessage());
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(LocalDateTime.now(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(UserIDNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleUserIDNotFoundException(UserIDNotFoundException ex) {
        logger.error("Id do usuario não foi encontrado: {}", ex.getMessage());
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(LocalDateTime.now(),ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
    @ExceptionHandler(TokenGenerationException.class)
    public ResponseEntity<ErrorResponseDTO> TokenGenerationException(TokenGenerationException ex){
        logger.error("Error while generating token: {}", ex.getMessage());
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(LocalDateTime.now(),ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    @ExceptionHandler(TokenValidationException.class)
    public ResponseEntity<ErrorResponseDTO> handleTokenValidationException(TokenValidationException ex) {
        logger.error("Error while validating token", ex);
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(LocalDateTime.now(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }
    @ExceptionHandler(ScoreApiException.class)
    public ResponseEntity<ErrorResponseDTO> handleScoreApiException(ScoreApiException ex) {
        logger.error("Error while validating token", ex);
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(LocalDateTime.now(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
    @ExceptionHandler(FileCreationException.class)
    public ResponseEntity<ErrorResponseDTO> handleFileCreationException(FileCreationException ex) {
        logger.error("Error occurred during file creation: {}", ex.getMessage(), ex);
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(LocalDateTime.now(), "Error occurred during file creation");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
    @ExceptionHandler(EmailSendingException.class)
    public ResponseEntity<ErrorResponseDTO> handleEmailSendingException(EmailSendingException ex) {
        logger.error("Erro ao enviar e-mail", ex);
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(LocalDateTime.now(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
    @ExceptionHandler(CepInvalidoException.class)
    public ResponseEntity<ErrorResponseDTO> handleCepInvalidoException(CepInvalidoException ex) {
        logger.error("CEP inválido ou não encontrado", ex);
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(LocalDateTime.now(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        logger.error("Usuário não encontrado: {}", ex.getMessage());
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(LocalDateTime.now(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
}
