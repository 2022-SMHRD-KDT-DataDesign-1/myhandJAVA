package NemoNemoLogic;

import java.util.ArrayList;

import javazoom.jl.player.MP3Player;

public class MusicPlayer {

ArrayList<MusicVO> musiclist = new ArrayList<>();
	

	MP3Player mp3 = new MP3Player();
	
	
	public MusicPlayer() {
		musiclist.add(new MusicVO(".//music/where.mp3")); // 메인 노래
		musiclist.add(new MusicVO(".//music/retro.mp3")); // 게임플레이 노래 
		
		musiclist.add(new MusicVO(".//music/Coin.mp3"));
		
	}
	
	// 재생기능
	public MusicVO play(int idx) {
		MusicVO vo = musiclist.get(idx);
		
		if (mp3.isPlaying()) {
			mp3.stop();
		}
		mp3.play(vo.getMusicPath());

		return vo;
	}
	

	
	public void stop() {
		
		if (mp3.isPlaying()) {
			mp3.stop();
		}
	
		
	}
	
}
