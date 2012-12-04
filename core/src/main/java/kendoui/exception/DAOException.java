package kendoui.exception;

/**
 * Exception for DB related service.
 */
public class DAOException extends MTXException {

    private static final long serialVersionUID = 6428430494012675859L;

    /**
     * Constructor of DAOException
     * 
     */
    public DAOException() {
        super();
    }

    /**
     * Constructor of DAOException
     *
     * @param message
     * @param cause
     */
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor of DAOException
     *
     * @param message
     */
    public DAOException(String message) {
        super(message);
    }

    /**
     * Constructor of DAOException
     *
     * @param cause
     */
    public DAOException(Throwable cause) {
        super(cause);
    }

}
