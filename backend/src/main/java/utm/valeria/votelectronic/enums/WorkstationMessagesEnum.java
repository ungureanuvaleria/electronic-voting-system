package utm.valeria.votelectronic.enums;

public enum WorkstationMessagesEnum {
    INSERT(1, "INSERT"),
    DELETE(2, "DELETE"),
    READ(3, "READ");
    
    private int functionCode;
    private String message;
    
    WorkstationMessagesEnum(int functionCode, String message) {
        this.functionCode = functionCode;
        this.message = message;
    }
    
    public static int getMessageFunctionCode(String message) {
        for (WorkstationMessagesEnum element : values()) {
            if (element.message.equals(message)) {
                return element.functionCode;
            }
        }
        return 0;
    }
}
