package model;

public class Client {
	private String id;
	private String pwd;
	private String name;
	private String nick;
	private String email;
	
	public Client(){
		this.id = "";
		this.pwd = "";
		this.name = "";
		this.nick = "";
		this.email = "";
	}
	
	public Client(String id, String pwd, String name, String nick, String email) {
		this.id = id;
		this.pwd = pwd;
		this.name = name;
		this.nick = nick;
		this.email = email;
	}

	public Client(String[] info) {
		id = info[0];
		pwd = info[1];
		name = info[2];
		nick = info[3];
		email = info[4];
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getemail() {
		return email;
	}

	public void setemail(String email) {
		this.email = email;
	}

	public void setAll(String[] tmp) {
		id = tmp[0];
		pwd = tmp[1];
		name = tmp[2];
		nick = tmp[3];
		email = tmp[4];
	}
}

