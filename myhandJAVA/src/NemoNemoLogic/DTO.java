package NemoNemoLogic;

public class DTO {
	private String id;
	private String pw;
	private String nick;

	public DTO(String id, String pw, String nick) {
		super();
		this.id = id;
		this.pw = pw;
		this.nick = nick;
	}
	public DTO(String id, String pw) {
		this.id = id;
		this.pw = pw;
	}
	public DTO() {
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getId() {
		return id;
	}
	public String getPw() {
		return pw;
	}
	public String getNick() {
		return nick;
	}
	
}
