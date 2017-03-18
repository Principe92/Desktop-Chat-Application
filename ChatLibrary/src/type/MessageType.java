package type;

public enum MessageType {
	TEXT(0),
	IMAGE(1),
	UNKNOWN(2);
	
	private final int value;
	
    private MessageType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    
    public static MessageType getType(int val) {

		for(MessageType type : MessageType.values()){
			if (type.value == val){
				return type;
			}
		}
		
		return UNKNOWN;
	}
}
