package br.edu.ifpe.afogados.desplugai.exception;

import br.edu.ifpe.afogados.desplugai.dto.ApiErrorDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorDTO> handleException(Exception e, HttpServletRequest request) {
        ApiErrorDTO errorDTO = new ApiErrorDTO();
        errorDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorDTO.setPath(request.getRequestURI());
        errorDTO.setTimestamp(new Date());
        errorDTO.setMessage(e.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(errorDTO);
    }
}
