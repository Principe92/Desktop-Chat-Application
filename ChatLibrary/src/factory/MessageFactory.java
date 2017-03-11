package factory;

import java.util.ArrayList;
import java.util.List;

import model.IMessage;
import model.ImageMessage;
import model.TextMessage;

public class MessageFactory {

	public static List<IMessage> getMessages(){
		List<IMessage> list = new ArrayList<>();
		
		list.add(new ImageMessage(new ImageHandler()));
		list.add(new TextMessage());
		
		return list;
	}

	public static IMessage getMessage(String text) {
		// TODO Auto-generated method stub
		
		List<IMessage> list = getMessages();
		
		for (IMessage msg : list){
			if (msg.IsType(text)){
				msg.setData(text);
				return msg;
			}
		}
		
		return null;
	}
}
