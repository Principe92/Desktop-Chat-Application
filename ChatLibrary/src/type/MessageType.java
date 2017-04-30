package type;

public enum MessageType {
	TEXT(0),
	IMAGE(1),
    QUIT(2),
    UNKNOWN(3);

    private final int value;

    MessageType(int value) {
        this.value = value;
    }

    public static MessageType getType(int val) {

		for(MessageType type : MessageType.values()){
			if (type.value == val){
				return type;
			}
		}

		return UNKNOWN;
	}

    public int getValue() {
        return value;
    }
}
