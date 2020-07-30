package com.aa.virtualroom.exception;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.aa.virtualroom.response.ErrorResponse;

@SuppressWarnings({"unchecked","rawtypes"})
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
	
	private static Logger LOGGER = LogManager.getLogger(CustomExceptionHandler.class);
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllException(Exception e,WebRequest request){
		List<String> details = new ArrayList<String>();
		details.add(e.getLocalizedMessage());
		ErrorResponse response = new ErrorResponse("Server Error", details);
		LOGGER.info(e.getMessage()+" The details "+e.getClass().toString());
		LOGGER.error( e.fillInStackTrace());
		return new ResponseEntity(response,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(RecordNotFoundException.class)
	public final ResponseEntity<Object> handleResourceNotFoundException(RecordNotFoundException e,WebRequest wr){
		List<String> details = new ArrayList<>();
		details.add(e.getLocalizedMessage());
		ErrorResponse response = new ErrorResponse("Resource Not found", details);
		LOGGER.info(e.getMessage()+" The details "+e.getClass().toString());
		LOGGER.error( e.fillInStackTrace());		
		return new ResponseEntity(response,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(FiuBinaryStorageException.class)
	public final ResponseEntity<Object> handleRFiuBinaryStorageException(FiuBinaryStorageException e,WebRequest wr){
		List<String> details = new ArrayList<>();
		details.add(e.getLocalizedMessage());
		ErrorResponse response = new ErrorResponse("Not able to store the file", details);
		LOGGER.info(e.getMessage()+" The details "+e.getClass().toString());
		LOGGER.error( e.fillInStackTrace());
		return new ResponseEntity(response,HttpStatus.PAYLOAD_TOO_LARGE);
	}
	
	@ExceptionHandler(UnsupportedOperationException.class)
	public final ResponseEntity<Object> handleJsonSchemaNotSupportedException(UnsupportedOperationException e,WebRequest wr){
		List<String> details = new ArrayList<>();
		details.add(e.getLocalizedMessage());
		ErrorResponse response = new ErrorResponse("Json Schema is Not valid", details);
		LOGGER.info(e.getMessage()+" The details "+e.getClass().toString());
		LOGGER.error( e.fillInStackTrace());
		return new ResponseEntity(response,HttpStatus.BAD_REQUEST);
	}
}
