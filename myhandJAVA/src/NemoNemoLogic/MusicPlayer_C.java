package NemoNemoLogic;

import java.util.ArrayList;

import javazoom.jl.player.MP3Player;


public class MusicPlayer_C {
	
	ArrayList<MusicVO> musiclist = new ArrayList<>();
	
	int curIndex = 0;
	
	MP3Player mp3 = new MP3Player();

	public MusicPlayer_C() {
		musiclist.add(new MusicVO("chilpop","ss",120, ".//music/chillhop.mp3"));
	
	}
	
	

	// 재생기능
	public MusicVO play() {
		MusicVO vo = musiclist.get(curIndex);
		
		if (mp3.isPlaying()) {
			mp3.stop();
		}
		mp3.play(vo.getMusicPath());

		return vo;
	}
	
	// 멈추기
	public void stop() {
		
		if (mp3.isPlaying()) {
			mp3.stop();
		}
//	return "";
	
	}
}
