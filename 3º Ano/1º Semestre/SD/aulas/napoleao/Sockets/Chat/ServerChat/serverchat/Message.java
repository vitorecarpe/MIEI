package serverchat;

import java.io.Serializable;

public class Message implements Serializable {

	public enum MessageCode {
		EXIT
	}

	private int origin;
	private int destination;
	private String text;
	private MessageCode msgCode;


	public Message(int origin, int destination, String text) {
		this.origin = origin;
		this.destination = destination;
		this.text = text;
	}

	public int getOrigin() {
		return origin;
	}

	public int getDestination() {
		return destination;
	}

	public String getText() {
		return text;
	}

	public MessageCode getMessageCode() {
		return msgCode;
	}
}
