package ge.kerketi.task.exception;

public enum ErrorMessage {

    GENERAL_ERROR("General Error!"),
    COULD_NOT_FOUND_WALLET("Couldn't found wallet!"),
    COULD_NOT_FOUND_CLIENT_BY_PID("Couldn't found client by pid!");

    private String description;

    public String getDescription() {
        return description;
    }

    ErrorMessage(String description) {
        this.description = description;
    }
}
