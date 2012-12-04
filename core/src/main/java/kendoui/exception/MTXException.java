package kendoui.exception;

import kendoui.utils.Constant;

/**
 * The base exception type for MTX system.
 */
public class MTXException extends Exception {

    private static final long serialVersionUID = -9202548787178975162L;

    // current exception error message
    private String message;

    // current exception error code
    private String errorCode = Constant.GLOBAL_ERROR_CODE_EXCEPTION;

    /**
     * Constructor of MTXException
     */
    public MTXException() {
        super();
    }

    /**
     * Constructor of MTXException
     *
     * @param message
     */
    public MTXException(String message) {
        this.init(null, message);
    }

    /**
     * Constructor of MTXException
     *
     * @param errorCode
     * @param message
     */
    public MTXException(String errorCode, String message) {
        this.init(errorCode, message);
    }

    /**
     * Constructor of MTXException
     *
     * @param cause
     */
    public MTXException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructor of MTXException
     *
     * @param message
     * @param cause
     */
    public MTXException(String message, Throwable cause) {
        super(cause);
        this.init(null, message);
    }

    /**
     * Constructor of MTXException
     *
     * @param errorCode
     * @param message
     * @param cause
     */
    public MTXException(String errorCode, String message, Throwable cause) {
        super(cause);
        this.init(errorCode, message);
    }

    /**
     * Init method for setting value of errorCode and message
     *
     * @param errorCode
     * @param message
     */
    private void init(String errorCode, String message) {
        if (message == null) {
            this.message = "";
        } else {
            this.message = message;
        }
        this.errorCode = errorCode;

    }

    /**
     * Get the message of MTXException
     *
     * @return the message {@link String}
     */
    public String getMessage() {
        return message;
    }

    /**
     * Set the value id for MTXException
     *
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Get the errorCode of MTXException
     *
     * @return the errorCode {@link String}
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Set the value errorCode for MTXException
     *
     * @param errorCode the errorCode to set
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * Override toString to provide custom description of MTXException
     *
     * @return
     */
    @Override
    public String toString() {
        if (null != getCause()) {
            return String.format("%s errorCode=%s,  errorMessage=%s, wrapperCause=%s;", this.getClass().getName(),
                    errorCode, message, getCause().getMessage());
        } else {
            return String.format("%s errorCode=%s,  errorMessage=%s", this.getClass().getName(), errorCode, message);
        }
    }
}
