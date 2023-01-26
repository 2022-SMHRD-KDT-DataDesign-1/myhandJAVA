package NemoNemoLogic;

import java.util.ArrayList;

import javazoom.jl.player.MP3Player;


public class MusicPlayer_C {
	
	ArrayList<MusicVO> musiclist = new ArrayList<>();
	
	int curIndex = 0;
	
	MP3Player mp3 = new MP3Player();

	public MusicPlayer_C() {
		musiclist.add(new MusicVO("chilpop","ss",120, "C://song/chillhop.mp3"));
		musiclist.add(new MusicVO("chilpop","ss",120, "C://song/retro.mp3"));
		musiclist.add(new MusicVO("chilpop","ss",120, "C://song/starlight.mp3"));
		musiclist.add(new MusicVO("chilpop","ss",120, "C://song/Cooking_Time.mp3"));
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
	public String stop() {
		
		if (mp3.isPlaying()) {
			mp3.stop();
		}
		
		return "노래가 정지되었습니다";
	}
}
