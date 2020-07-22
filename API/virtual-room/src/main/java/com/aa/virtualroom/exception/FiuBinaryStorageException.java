package com.aa.virtualroom.exception;

public class FiuBinaryStorageException extends RuntimeException {

	public FiuBinaryStorageException(String mesaage, Throwable ex) {
		super(mesaage,ex);
	}

	public FiuBinaryStorageException(String message) {
		super(message);
	}

}
