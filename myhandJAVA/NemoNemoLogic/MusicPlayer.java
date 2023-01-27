package NemoNemoLogic;

import java.util.ArrayList;

import javazoom.jl.player.MP3Player;

public class MusicPlayer {

ArrayList<MusicVO> musiclist = new ArrayList<>();
	

	MP3Player mp3 = new MP3Player();
	
	
	public MusicPlayer() {
		musiclist.add(new MusicVO("C//song/where.mp3")); // 메인 노래 - 0
		musiclist.add(new MusicVO("C//song/retro.mp3")); // 게임플레이 노래 
		
		musiclist.add(new MusicVO("C//song/Music_Box.mp3")); // 뾰로롱 - 2
		musiclist.add(new MusicVO("C//song/절망2.mp3")); // 목숨 0
		musiclist.add(new MusicVO("C//song/휘파람.mp3")); // 목숨 1,2 
		musiclist.add(new MusicVO("C//song/박수.mp3"));  // 목숨 3 
		
		musiclist.add(new MusicVO("C//song/트럼펫.mp3")); // 클리어 - 6
		
		musiclist.add(new MusicVO("C//song/우는사람.mp3")); // 목숨 1개남았을때 - 7
		
//		musiclist.add(new MusicVO(".//music/Error.mp3")); 
//		musiclist.add(new MusicVO(".//music/Coin.mp3"));
		
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
