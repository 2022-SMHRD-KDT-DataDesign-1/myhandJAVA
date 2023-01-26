package NemoNemoLogic;

public class MusicVO {

	private String musicName;	// 노래제목
	private String singer;		// 가수이름
	private int playTime;		// 재생시간
	// 추가
	private String musicPath;	// 노래경로
	
	
	
	public MusicVO(String musicName, String singer, int playTime, String musicPath) {
		this.musicName = musicName;
		this.singer = singer;
		this.playTime = playTime;
		this.musicPath = musicPath;
	}
	
	
	
	public String getMusicName() {
		return musicName;
	}
	public String getSinger() {
		return singer;
	}
	public int getPlayTime() {
		return playTime;
	}
	public String getMusicPath() {
		return musicPath;
	}
	

}
