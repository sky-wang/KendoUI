package kendoui.exception;

/**
 * The base business exception for the service layer.
 */
public class BusinessException extends MTXException {

    private static final long serialVersionUID = -9127083641751362622L;

    private int httpCode;

    /**
     * Constructor of BusinessException
     * 
     */
    public BusinessException() {
        super();
    }

    /**
     * Constructor of BusinessException
     * 
     * @param httpCode
     */
    public BusinessException(int httpCode) {
        super();
        this.httpCode = httpCode;
    }

    /**
     * Constructor of BusinessException
     * 
     * @param message
     */
    public BusinessException(String message) {
        super(message);
    }

    /**
     * Constructor of BusinessException
     * 
     * @param errorCode
     * @param message
     */
    public BusinessException(String errorCode, String message) {
        super(errorCode, message);
    }

    /**
     * Constructor of BusinessException
     * 
     * @param httpCode
     * @param message
     */
    public BusinessException(int httpCode, String message) {
        super(message);
        this.httpCode = httpCode;
    }

    /**
     * Constructor of BusinessException
     * 
     * @param httpCode
     * @param errorCode
     * @param message
     */
    public BusinessException(int httpCode, String errorCode, String message) {
        super(errorCode, message);
        this.httpCode = httpCode;
    }

    /**
     * Constructor of BusinessException
     * 
     * @param message
     * @param cause
     */
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor of BusinessException
     * 
     * @param errorCode
     * @param message
     * @param cause
     */
    public BusinessException(String errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }

    /**
     * Constructor of BusinessException
     * 
     * @param httpCode
     * @param errorCode
     * @param message
     * @param cause
     */
    public BusinessException(int httpCode, String errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
        this.httpCode = httpCode;
    }

    /**
     * Get the httpCode of BusinessException
     * 
     * @return the httpCode {@link int}
     * 
     */
    public int getHttpCode() {
        return httpCode;
    }

    /**
     * Set the value httpCode for BusinessException
     * 
     * @param httpCode the httpCode to set
     */
    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }
}
