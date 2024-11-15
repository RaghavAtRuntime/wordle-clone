package use_case.logout;

/**
 * The output boundary for the Logout Use Case.
 */
public interface LogoutOutputBoundary {
    /**
     * Prepares the success view for the Login Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(LogoutOutputData outputData);
  
    void switchTogridView();
}
