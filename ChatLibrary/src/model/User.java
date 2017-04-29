package model;

public class User {
    private int id;
	private String pwd;
	private String name;
	private String nick;
	private String email;
    private boolean isLoggedIn;

    public User(int id) {
        this.id = id;
		this.pwd = "";
        this.name = String.format("User %d", id);
        this.nick = "";
		this.email = "";
        this.isLoggedIn = false;
	}

    public User(int id, String pwd, String name, String nick, String email) {
        this.id = id;
		this.pwd = pwd;
		this.name = name;
		this.nick = nick;
		this.email = email;
        this.isLoggedIn = false;
	}

    public User(String[] info) {
        id = Integer.parseInt(info[0]);
		pwd = info[1];
		name = info[2];
		nick = info[3];
		email = info[4];
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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
    
    public boolean getIsLoggedIn() {
        return isLoggedIn;
    }
    
    public boolean setIsLoggedIn(boolean status) {
        this.isLoggedIn=status;
		return true;
	}

	public void setAll(String[] tmp) {
		id = Integer.parseInt(tmp[0]);
		pwd = tmp[1];
		name = tmp[2];
		nick = tmp[3];
		email = tmp[4];
	}
}

