package NemoNemoLogic;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import NemoNemoLogic.DAO;
import NemoNemoLogic.DTO;

public class Controller {
	
//	MusicPlayer player = new MusicPla0yer();
	
	static Scanner sc = new Scanner(System.in);
	static MusicPlayer player = new MusicPlayer();
	MusicVO m = player.play(0); // 메인음악
	DAO dao = new DAO();
	int row = 0;
	static String userNick = "";
	static int userCoin = 0;
	static int level = 0;
	static int userSeq = 0;
	static int gameSeq = 0;
	static long start;
	static long end;
	static String time;

	public void join(DTO dto) { // 회원가입
		row = dao.join(dto);
		if (row > 0) {
			System.out.println("회원가입 성공!");
		} else {
			System.out.println("아이디 또는 닉네임이 중복되었습니다.");
		}

	}

	// 로그인
	public boolean login(DTO dto) {
		boolean isCheck = false;
		DTO res = dao.login(dto);
//		boolean res = dao.login(dto);
		if (res.getNick() != null) {
			userSeq = res.getUserSeq();
			userCoin = res.getUserCoin();
			userNick = res.getNick();
			System.out.println("로그인 완료");
			System.out.println(res.getNick() + "님 반갑습니다.\n");
			return isCheck = true;
		} else {
			System.out.println("아이디나 비밀번호를 확인하세요.");
			System.out.println();
			return isCheck;
		}
	}

	// 게임 난이도 선택
	public static void levelChoice(int level_choice) {
		boolean isCleared = false;
		level = level_choice;
		DAO dao1 = new DAO();
		String[] nums = { "①", "②", "③", "④", "⑤" };
		ArrayList<Integer> game_seq = dao1.levelChoice(level_choice);
		while (true) {
			if (level_choice == 1) {
				
				sleep();
				player.stop();
				player.play(1);
				System.out.println();
				System.out.println("---------------5X5---------------");
				for (int i = 0; i < game_seq.size(); i++) {
					System.out.printf("%5s",nums[i]);
//					System.out.print("[" + game_seq.get(i) + "] ");
				}
				System.out.println();
				System.out.println("---------------------------------\n");
				break;
			} else if (level_choice == 2) {
				sleep();
				System.out.println();
				System.out.println("--------------10X10--------------");
				for (int i = 0; i < game_seq.size(); i++) {
					System.out.printf("%5s",nums[i]);
//					System.out.print("[" + game_seq.get(i) + "] ");
				}
				System.out.println();
				System.out.println("---------------------------------\n");
				break;
			} else {
				System.out.println("올바른 숫자를 입력하세요");
			}
		}
	}

	// 난이도에 따른 게임 선택
	public static int gameChoice(int game_select) {
		DAO dao1 = new DAO();
		int row = 0;
		int num = 0;
		int a = 0;
		// level 1은 5*5, 2는 10*10
		if (level == 1) {
			a = 5;
		} else if (level == 2) {
			a = 10;
		}
		int[][] res = new int[a][a];
		// ans는 답 데이터
//		String ans = dao1.gameChoice(level, game_select);
		GameDTO ans = dao1.gameChoice(level, game_select);
		gameSeq = ans.getGameSeq();

		if (ans.getGameSeq() == 0) {
			System.out.println("숫자를 확인해주세요.");
			return 1;
		}
		row = dao1.clear(userSeq,gameSeq);
		if(row > 0) {

			System.out.println("--------이미 플레이한 그림입니다--------");
			System.out.println("ː    ① 랭킹보기       ② 다시하기      ː ");
			System.out.println("----------------------------------\n");

			int select = sc.nextInt();
			if(select == 1) {
				ArrayList<GameDTO> list = new ArrayList<>();
				list = dao1.rank(gameSeq);
				int a1 = isCleared(list);
//				System.out.println("============ Rank ============");
//				System.out.println("  이름\t\t 시간");
//				for(int i = 0;i<list.size();i++) {
//					System.out.println((i+1)+" "+list.get(i).getUserNick()+"\t\t "+list.get(i).getGameTime());
//				}
				if (a1==1) {
					return a1;
				}
			}else if(select == 2) {
				
			}else {
				System.out.println("올바른 숫자를 입력하세요");
				return 1;
			}
		}
		
		if (ans.getGameAns() == null) {
			System.out.println("게임 정보 오류");

		} else if (level == 1) { // 5*5
			num = 5;
			System.out.println(num + " x " + num);
			// 답데이터 res[][]배열에 저장
			res = arrMake(ans.getGameAns(), num);

			// 문제
			int a1 = playGame(num, res, game_select);
			return a1;
			

		} else if (level == 2) { // 10*10
			num = 10;
			System.out.println(num + " x " + num);
			// 답데이터 res[][]배열에 저장
			res = arrMake(ans.getGameAns(), num);

			// 문제
			int a1 = playGame(num, res, game_select);
			return a1;
			
		} else {
			System.out.println("올바른 숫자를 입력하세요");
		}
		return 0;
	}

	// 답데이터 이중배열로 변경
	public static int[][] arrMake(String ans, int num) {
		int[][] res = new int[num][num];
		String[] arr = ans.split(",");

		for (int i = 0; i < res.length; i++) {
			String[] arr3 = arr[i].split("");
			for (int j = 0; j < arr.length; j++) {
				res[i][j] = Integer.parseInt(arr3[j]);
//				System.out.print(res[i][j] + " ");
			}
//			System.out.println();
		}
		return res;
	}

	// Game Start!
	public static int playGame(int num, int[][] res, int game_select) {
		Scanner sc = new Scanner(System.in);
		DAO dao1 = new DAO();
		// 정답 체크할 변수
		int resCheck = 0;
		int userCheck = 0;
		// 힌트 사용 번호
		int hint = 700;
		System.out.println();
		sleep();

		for (int i = 0; i < res.length; i++) {
			for (int j = 0; j < res.length; j++) {
				// 답데이터출력
//				System.out.print(res[i][j] + " ");
				if (res[i][j] == 1)
					resCheck += 1;
			}
//			System.out.println();
		}
		
		userCoin = dao1.userCoinCheck(userSeq);
		System.out.println("---------------GAME--------------");
		System.out.println("ː " + userNick + "님              코인 : " + userCoin + "개  ː ");
		System.out.println("ː      ~힌트사용을 원하면 700 입력~   ː ");
		System.out.println("---------------------------------\n");

		// 숫자 매칭 배열
		int[][] arr = new int[num][num];
		int cnt = 1;

		System.out.println("번호를 선택하세요.");
		System.out.println();
		System.out.println();
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr.length; j++) {
				arr[i][j] = cnt;
				cnt++;
				System.out.printf("%3s", arr[i][j]);
			}
			System.out.println();
		}
		System.out.println();
		System.out.println();

		// 목숨
		int count = 3;
		int[][] user = new int[num][num];
		int choice = 0;

		start = System.currentTimeMillis();

		while (true) {
			if (level == 1) {
				printQuestion(res, user, 5);
			} else if (level == 2) {
				printQuestion(res, user, 10);
			}
			System.out.println();
			String[] stars = { "♥♥♥", "♥♥♡", "♥♡♡", "♡♡♡" };
			System.out.println("---------------------------------");
			if (count == 3) {
				System.out.println("ː                          " + stars[0] + "  ː");
			} else if (count == 2) {
				System.out.println("ː                          " + stars[1] + "  ː");
			} else if (count == 1) {
				System.out.println("ː                          " + stars[2] + "  ː");
				
			} else if (count == 0) {
				player.stop();
				player.play(7);
				System.out.println("ː                          " + stars[3] + "  ː");
			}
			System.out.println("---------------------------------\n");
			System.out.println();
			
			System.out.print("칠하려면 1번, X는 2번, 힌트는 700번 선택 >> ");
			int oxSelect = sc.nextInt();
			
			if (oxSelect == 1) {
				System.out.print("-------------------------- 숫자 선택 >> ");
				choice = sc.nextInt();
				if(choice < 0 && choice <= num*num){
					System.out.println("숫자를 확인해 주세요.");
					continue;
				}

				if (res[(choice - 1) / num][(choice - 1) % num] == 1 && choice <= num*num) {
					if (user[(choice - 1) / num][(choice - 1) % num] != 1) {
						user[(choice - 1) / num][(choice - 1) % num] = 1;
						userCheck++;
					}
				} else {
					count--;
					System.out.println();
					System.out.println(" 틀렸습니다!");
					System.out.println();
				}

			} else if (oxSelect == 2) {
				System.out.print("숫자 선택 >>");
				choice = sc.nextInt();
				
				if(choice < 0 && choice <= num*num){
					System.out.println("숫자를 확인해 주세요.");
					continue;
				}

				if (res[(choice - 1) / num][(choice - 1) % num] == 0) {
					user[(choice - 1) / num][(choice - 1) % num] = 3;
				} else {
					count--;
					System.out.println("다시 확인하세요.");
				}
			} else if(oxSelect == hint && userCoin != 0) {
				// 힌트 사용
				int hintNum = 0;
				if (level == 1) {
					hintNum = 5;
				} else if (level == 2) {
					hintNum = 10;
				}
				Random rd = new Random();
				dao1.gaCha(userCoin-1);
				choice = rd.nextInt(hintNum*hintNum) + 1;
				System.out.println(choice + "번! 힌트 사용 완료! 코인 한개 차감!");
				System.out.println(userCoin + "개 남았습니다!");
				System.out.println();
				
				if (res[(choice - 1) / num][(choice - 1) % num] == 1) {
					if (user[(choice - 1) / num][(choice - 1) % num] != 1) {
						user[(choice - 1) / num][(choice - 1) % num] = 1;
						userCheck++;
					}
				} else {
					count--;
					System.out.println("다시 확인하세요.");
				}

			}
			
			if (userCheck == resCheck) {
				int row = 0;
				int coin = 0;
				sleep();
				System.out.println();
				System.out.println("정답!! 축하합니다!!");
				System.out.println();
				for (int i = 0; i < user.length; i++) {
					for (int j = 0; j < user.length; j++) {
						if (user[i][j] == 1) {
							System.out.printf("%2s","■");
						} else if (user[i][j] == 3) {
							System.out.printf("%2s","X");
						} else {
							System.out.printf("%2s","□");
						}
					}
					System.out.println();
				}
				System.out.println();
				System.out.print("걸린 시간 : ");
				end = System.currentTimeMillis();
				time = Long.toString((end - start) / 1000 / 60) + "," + Long.toString((end - start) / 1000 % 60);
				System.out.print(Long.toString((end - start) / 1000 / 60) + "분"
						+ Long.toString((end - start) / 1000 % 60) + "초");
				System.out.println();
				if (level == 1 && count == 3) {
					coin = 1;
				} else if (level == 2) {
					coin = count;
				}
				row = dao1.updateCoin(coin, userSeq);
				if (row > 0) {
					
					System.out.println(coin + "코인 흭득!\t");
					System.out.println();
					userCoin += coin;
				}
				dao1.userGame(userSeq, gameSeq, time);
				player.stop();
				player.play(6);

				return 1;
			}

			if (count <= 0) {
				if (userCoin > 2) {
					System.out.println("=============================================");
					System.out.println("ː         ♡ 목숨이 0이 되었어요!                ː");
					System.out.println("ː    목숨을 구입해서 계속 플레이 하시겠습니까?       ː");
					System.out.println("ː ① 네! 계속할래요  ② 아니요 ㅠㅠ 포기하겠습니다     ː");
					System.out.println("=============================================\n");
					int yesOrNo = sc.nextInt();

					if (yesOrNo == 1) {
						System.out.println("--------------돌려돌려--------------");
						System.out.println("ː 코인 3개를 사용해 목숨뽑기를 진행합니다 ː ");
						System.out.println("----------------------------------\n");
						System.out.println();
						sleep();
						
						// userCoin 3개 차감
						dao1.gaCha(userCoin-3);
						int a1 = gaCha();
						player.stop();
						player.play(1);
						
						if (a1 == 10) {
							return 1;
						}
						// 가챠로 뽑아온 목숨 a1더하기
						count = count + a1;
						// 한판 다시니까 1 차감 -> 할랬는데 목숨 1개 뽑으면 꺼져서 일단 안차감..ㅎ
//						count--;
					} else if (yesOrNo == 2) {
						player.stop();
						player.play(7);
						System.out.println("\t포기하셨습니다\n");
						System.out.println("\t　 /) /) \n" + "\t  (ಥ_ಥ)\n");
//					levelChoice();
						return 1;
					} else {
						System.out.println("올바른 숫자를 입력하세요");
					}
				} else {
					System.out.println("=============================================");
					System.out.println("ː         ♡ 목숨이 0이 되었어요!                ː");
					System.out.println("ː    아쉽게도 코인이 없어 게임이 종료됩니다! ^0^     ː");
					System.out.println("=============================================\n");
					return 1;
				}
			}
			
		} // while문 종료
		
	}

	// 이미 클리어한 게임의 경우 랭크를 먼저 보여주기 위함
	public static int isCleared(ArrayList<GameDTO> list) {
		Scanner sc = new Scanner(System.in);
		String[] nums = { "①", "②", "③", "④", "⑤" };
		System.out.println("----------------랭크---------------");
		// 랭크는 1-5위까지만 보여줄까요
//		for (int i = 0; i < nums.length; i++) {
//			System.out.println("\t" + nums[i]);
//		}
//		System.out.println("============ Rank ============");
		System.out.println("  이름\t\t 시간");

		for(int i = 0;i<nums.length;i++) {
			String[] time = list.get(i).getGameTime().split(",");
			System.out.println(nums[i]+" "+list.get(i).getUserNick()+"\t "+time[0] + "분 " + time[1] + "초");
		}
		while (true) {
			System.out.println();
			System.out.println("  ① 다시 도전하기  ② 돌아가기");
			System.out.println("----------------------------------\n");
			int re_select = sc.nextInt();
			if (re_select == 1) {
//				gamePlay();
				return 0;
			} else if (re_select == 2) {
//			levelChoice();
				return 1;
			} else {
				System.out.println("올바른 숫자를 입력하세요");
			}
		}
	}

	public static void gamePlay() {
		int life = 0;
		Scanner sc = new Scanner(System.in);
		String[] stars = { "♥♥♥", "♥♥♡", "♥♡♡", "♡♡♡" };

		System.out.println("---------------GAME--------------");
		System.out.println("ː xxx 님              코인 : 5개  ː ");
		System.out.println("ː                    힌트 : 0개   ː ");
		System.out.println("---------------------------------\n");

		System.out.println("GAME");
		System.out.println("---------------------------------");
		if (life == 3) {
			System.out.println("ː                          " + stars[0] + "  ː");
		} else if (life == 2) {
			System.out.println("ː                          " + stars[1] + "  ː");
		} else if (life == 1) {
			System.out.println("ː                          " + stars[2] + "  ː");
		} else if (life == 0) {
			System.out.println("ː                          " + stars[3] + "  ː");
		}
		System.out.println("---------------------------------\n");

		if (life == 0) {
			System.out.println("=============================================");
			System.out.println("ː         ♡ 목숨이 0이 되었어요!                ː");
			System.out.println("ː    목숨을 구입해서 계속 플레이 하시겠습니까?       ː");
			System.out.println("ː ① 네! 계속할래요  ② 아니요 ㅠㅠ 포기하겠습니다     ː");
			System.out.println("=============================================\n");
			int yesOrNo = sc.nextInt();

			if (yesOrNo == 1) {
				System.out.println("--------------돌려돌려--------------");
				System.out.println("ː 코인 3개를 사용해 목숨뽑기를 진행합니다 ː ");
				System.out.println("----------------------------------\n");
				System.out.println();
				sleep();

//				gaCha();

			} else if (yesOrNo == 2) {
				System.out.println("\t포기하셨습니다\n");
				System.out.println("\t　 /) /) \n" + "\t  (ಥ_ಥ)\n");
//				levelChoice();
			} else {
				System.out.println("올바른 숫자를 입력하세요");
			}

		}
	}

	// sleep 1초
	public static void sleep() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 목숨 뽑기 // 3코인 차감
	public static int gaCha() {
		player.stop();
		player.play(2);
		Random rd = new Random();
		Scanner sc = new Scanner(System.in);
		System.out.println("\r\n"
				+ "█▄▄░░░░░░░░░░▄▄▄▄▄▄░░░░░░░░░░▄▄█\r\n"
				+ "█████▄░░░░░▄███▀▀███▄░░░░░▄█████\r\n"
				+ "███████▄░░▄██▀░░░░▀████▄▄██████▀\r\n"
				+ "░██████████▀░░▀░░▀░░▀█████████▀░\r\n"
				+ "░░▀████████░░░░▀▀░░░░███████▀░░░\r\n"
				+ "░░░░▀▀██████▄░▀▀▀▀░▄██████░░░░░░\r\n"
				+ "░░░░░░░▀▀██▀██████████▀▀░░░░░░░░\r\n"
				+ "░░░░░░░░░▄▄██████████▄▄░░░░░░░░░\r\n"
				+ "░░░░░░░▄██▀▀░██████░▀▀██▄░░░░░░░\r\n"
				+ "░░░░░▄██▀░░░░██████░░░░▀██▄░░░░░\r\n"
				+ "░░▄░██▀░░░░▄████████▄░░░░▀██▄▄░░\r\n"
				+ "░░▀▀▀░░░░░████████████░░░░░▀▀▀░░\r\n"
				+ "░░░░░░░░░██████████████░░░░░░░░░\r\n"
				+ "░░░░░░░░████████████████░░░░░░░░\r\n"
				+ "░░░░░░░██████████████████░░░░░░░\r\n"
				+ "░░░░░░░░░░░░▄█░░░░█▄░░░░░░░░░░░░\r\n"
				+ "\r\n"
				+ "\r\n"
				+ "");
		sleep();
		sleep();
		int num = rd.nextInt(4);
		if (num == 0) {
			player.stop();
			player.play(3);
			System.out.println("╭ ⁀ ⁀ ╮\n" + "( '👅'　　)\n" + "╰ ‿ ‿ ╯\n" + "　　　　　　　　╭ ⁀ ⁀ ╮\n" + "　　　　　　　　( '👅'　　)\n"
					+ "　　　　　　　　╰ ‿ ‿ ╯\n" + "╭ ⁀ ⁀ ╮\n" + "( '👅'　　)\n" + "╰ ‿ ‿ ╯");
		} else if (num == 1) {
			player.stop();
			player.play(4);
			System.out.print("\t (\\_/)\n" + "\t( •.• )\n" + "\t/ > •‧:❤️:‧•");
		} else if (num == 2) {
			player.stop();
			player.play(4);
			System.out.println("\t (\\_/)\n" + "\t( •.• )\n" + "\t/ > •‧:❤️❤️:‧•");
		} else if (num == 3) {
			player.stop();
			player.play(5);
			System.out.println("\t (\\_/)\n" + "\t( •.• )\n" + "\t/ > •‧:❤️❤️❤️:‧•");
		}
		sleep();
		// 목숨 + num 해야함
		System.out.println();

		if (num == 0) {
			System.out.println("꽝!!! 안타깝네요!!");
			System.out.println();
			return 10;
		}
		
		System.out.println("----------------------------------");
		System.out.println("ː         " + num + "개의 목숨 UP            ː ");
		System.out.println("ː     ① 계속하기     ② 돌아가기      ː ");
		System.out.println("----------------------------------\n");

		int life_select = sc.nextInt();

		if (life_select == 1) {
			// 목숨 -1 해야함
			return num;
		} else if (life_select == 2) {
			return 10;
		} else {
			System.out.println("올바른 숫자를 입력하세요");
		}
		return 10;
	}

	public static void printQuestion(int[][] ans, int[][] user, int numX) {
		// x hint
		String[] hintArrX = getHintArrX(ans, numX);
		// y hint
		String[] hintArrY = getHintArrY(ans, numX);

		int hintZone = (ans.length + 1) / 2;
		int entireZone = (ans.length + 1) / 2 + ans.length;
		int len = (ans.length + 1) / 2;

		for (int i = 0; i < entireZone; i++) {
			for (int j = 0; j < entireZone; j++) {
				if (i < hintZone && j < hintZone) {
					System.out.print("  ");
				} else if (i >= hintZone && j >= hintZone) {
					if (user[i - len][j - len] == 1) {
						System.out.print("■" + " ");
					} else if (user[i - len][j - len] == 3) {
						System.out.print("X" + " ");
					} else {
						System.out.print("□" + " ");
					}

				} else {
					if (i < len) {
						String[] a = hintArrY[j - len].split("");
						if (a.length > i) {
							System.out.print(a[i] + " ");
						} else {
							System.out.print("  ");
						}
					} else {
						String[] a = hintArrX[i - len].split("");
						if (a.length > j) {
							System.out.print(a[j] + " ");
						} else {
							System.out.print("  ");
						}
					}

				}
			}
			System.out.println();
		}

	}

	public static String[] getHintArrX(int[][] ans, int numX) {

		int cntNumX = 0;
//		int numX = 5;
		String[] hintArrX = new String[numX];
		for (int i = 0; i < hintArrX.length; i++) {
			hintArrX[i] = "";
		}
//		String[] hintArrX = { "", "", "", "", "" };
		for (int i = 0; i < ans.length; i++) {
			for (int j = 0; j < hintArrX.length; j++) {
				if (ans[i][j] == 1) {
					cntNumX++;
					if (j == numX - 1 && ans[i][numX - 1] == 1) {
						hintArrX[i] += cntNumX;
					}
				} else if (ans[i][j] == 0) {
					if (cntNumX != 0) {
						hintArrX[i] += cntNumX;
					}
					cntNumX = 0;
				}

			}
			cntNumX = 0;
		}

		return hintArrX;
	}

	public static String[] getHintArrY(int[][] ans, int numY) {

		int cntNumY = 0;
//		int numY = 5;
		String[] hintArrY = new String[numY];
		for (int i = 0; i < hintArrY.length; i++) {
			hintArrY[i] = "";
		}
//		String[] hintArrY = { "", "", "", "", "" };
		for (int i = 0; i < ans.length; i++) {
			for (int j = 0; j < hintArrY.length; j++) {
				if (ans[j][i] == 1) {
					cntNumY++;
					if (j == numY - 1 && ans[j][i] == 1) {
						hintArrY[i] += cntNumY;
					}
				} else if (ans[j][i] == 0) {
					if (cntNumY != 0) {
						hintArrY[i] += cntNumY;
					}
					cntNumY = 0;
				}

			}
			cntNumY = 0;
		}

		return hintArrY;

	}
	

}
