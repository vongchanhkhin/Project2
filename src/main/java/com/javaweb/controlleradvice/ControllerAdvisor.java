package com.javaweb.controlleradvice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.javaweb.customexception.FieldRequiredException;
import com.javaweb.model.ErrorResponseDTO;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
	@ExceptionHandler(ArithmeticException.class)
	public ResponseEntity<Object> handleArithmeticException(ArithmeticException ex, WebRequest req) {

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();

		errorResponseDTO.setError(ex.getMessage());
		List<String> errorDetails = new ArrayList<>();
		errorDetails.add("Số nguyên không thể chia cho 0!");
		errorResponseDTO.setDetails(errorDetails);

		return new ResponseEntity<>(errorResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(FieldRequiredException.class)
	public ResponseEntity<Object> handleFieldRequiredException(FieldRequiredException ex, WebRequest req) {

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();

		errorResponseDTO.setError(ex.getMessage());
		List<String> errorDetails = new ArrayList<>();
		errorDetails.add("Kiểm tra lại trường name hoặc numberofbasement bởi vì một trong hai đang bị null!");
		errorResponseDTO.setDetails(errorDetails);

		return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_GATEWAY);
	}
}
