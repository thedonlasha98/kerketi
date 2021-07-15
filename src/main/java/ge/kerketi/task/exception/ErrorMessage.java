package ge.kerketi.task.exception;

public enum ErrorMessage {

    GENERAL_ERROR("General Error!"),
    COULD_NOT_FOUND_WALLET("Couldn't found wallet!"),
    NOT_ENOUGH_BALANCE("Not enough balance!"),
    SAME_ACCOUNTS_ERROR("Same accounts error!"),
    INCRRECT_CCY_ERROR("Incorrect ccy error!"),
    AMOUNT_MUST_BE_MORE_THAN_ZERO("Amount must be more than zero!"),
    ACCOUNT_NUMBER_MUST_NOT_BE_EMPTY("AccountNumber must not be empty!"),
    PID_MUST_NOT_BE_EMPTY("Pid must not be empty!"),
    PERSONAL_NUMBER_ALREADY_EXISTS("Personal number already exists!"),
    COULD_NOT_FOUND_CLIENT_BY_PID("Couldn't found client by pid!");

    private String description;

    public String getDescription() {
        return description;
    }

    ErrorMessage(String description) {
        this.description = description;
    }
}
