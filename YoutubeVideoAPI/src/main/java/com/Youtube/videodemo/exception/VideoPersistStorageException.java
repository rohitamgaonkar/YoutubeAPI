package com.Youtube.videodemo.exception;

public class VideoPersistStorageException extends RuntimeException {
    public VideoPersistStorageException(String message) {
        super(message);
    }

    public VideoPersistStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
