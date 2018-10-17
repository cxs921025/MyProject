package com.cxs.core.exception;

/**
 * @author ChenXS
 * Service class custom exception information
 */
public class ServiceException extends RuntimeException {
    /**
     * 序列化ID
     */
    private static final long serialVersionUID = -9138621492432781587L;

    public ServiceException(String message) {
        super(message);
    }
}
