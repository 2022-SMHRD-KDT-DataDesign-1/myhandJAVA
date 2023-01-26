package NemoNemoLogic;

public class GameDTO {
	private Integer userSeq;
	private Integer gameSeq;
	private String gameTime;
	private Integer gameClear;
	private String gameAns;
	
	public GameDTO(Integer userSeq, Integer gameSeq, String gameTime, Integer gameClear, String gameAns) {
		this.userSeq = userSeq;
		this.gameSeq = gameSeq;
		this.gameTime = gameTime;
		this.gameClear = gameClear;
		this.gameAns = gameAns;
	}
	public GameDTO(Integer gameSeq, String gameAns) {
		this.gameSeq = gameSeq;
		this.gameAns = gameAns;
	}
	
	public Integer getUserSeq() {
		return userSeq;
	}
	public String getGameAns() {
		return gameAns;
	}
	public void setGameAns(String gameAns) {
		this.gameAns = gameAns;
	}
	public void setUserSeq(Integer userSeq) {
		this.userSeq = userSeq;
	}
	public Integer getGameSeq() {
		return gameSeq;
	}
	public void setGameSeq(Integer gameSeq) {
		this.gameSeq = gameSeq;
	}
	public String getGameTime() {
		return gameTime;
	}
	public void setGameTime(String gameTime) {
		this.gameTime = gameTime;
	}
	public Integer getGameClear() {
		return gameClear;
	}
	public void setGameClear(Integer gameClear) {
		this.gameClear = gameClear;
	}

}
