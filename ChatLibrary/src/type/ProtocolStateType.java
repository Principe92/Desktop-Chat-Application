package type;

public enum ProtocolStateType {

	HANDSHAKE(0),
	DATE(1),
	END(2), 
	UNKNOWN(3);
	
	private int value;
	
	private  ProtocolStateType(int value){
		this.value = value;
	}
	
	public int getValue(){
		return value;
	}

	public static ProtocolStateType getType(int val) {

		for(ProtocolStateType type : ProtocolStateType.values()){
			if (type.value == val){
				return type;
			}
		}
		
		return UNKNOWN;
	}
}
