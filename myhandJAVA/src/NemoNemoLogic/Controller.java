package NemoNemoLogic;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import NemoNemoLogic.DAO;
import NemoNemoLogic.DTO;

public class Controller {
	Scanner sc = new Scanner(System.in);
	DAO dao = new DAO();
	int row = 0;
	static String userNick = "";
	static int level = 0;

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
			userNick = res.getNick();
			System.out.println("로그인 완료");
			System.out.println(res.getNick() + "님 반갑습니다.\n");
			return isCheck = true;
		} else {
			System.out.println("아이디나 비밀번호를 확인하세요.");
			return isCheck;
		}
	}

	// 게임 난이도 선택
	public static void levelChoice(int level_choice) {
		boolean isCleared = false;
		level = level_choice;
		DAO dao1 = new DAO();
		String[] nums = {"①","②","③","④","⑤"};
		ArrayList<Integer> game_seq = dao1.levelChoice(level_choice);
		while (true) {
			if (level_choice == 1) {
				System.out.println("---------------5X5---------------");
				for (int i = 0; i < game_seq.size(); i++) {
					System.out.print(nums[i]+"\t");
//					System.out.print("[" + game_seq.get(i) + "] ");
				}
				System.out.println("---------------------------------");
				break;
			} else if (level_choice == 2) {
				System.out.println("--------------10X10--------------");
				for (int i = 0; i < game_seq.size(); i++) {
					System.out.print(nums[i]+"\t");
//					System.out.print("[" + game_seq.get(i) + "] ");
				}
				System.out.println("---------------------------------");
				break;
			} else {
				System.out.println("올바른 숫자를 입력하세요");
			}
		}
	}

	// 난이도에 따른 게임 선택
	public static void gameChoice(int game_select) {
		DAO dao1 = new DAO();
		int num = 0;
		int[][] res = new int[5][5];
		// ans는 답 데이터
		String ans = dao1.gameChoice(game_select);
		if (ans == null) {
			System.out.println("게임 정보 오류");

		} else if (level == 1) { // 5*5
			num = 5;
			System.out.println(num + "x" + num);
			// 답데이터 res[][]배열에 저장
			res = arrMake(ans, num);

			// 문제 
			playGame(num, res);

		} else if (level == 2) { // 10*10
			System.out.println(num + "x" + num);
			num = 10;
		} else {
			System.out.println("올바른 숫자를 입력하세요");
		}

	}

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
	public static void playGame(int num, int[][] res) {
		Scanner sc = new Scanner(System.in);
		// 정답 체크할 변수
		int resCheck = 0;
		int userCheck = 0;
		
		for (int i = 0; i < res.length; i++) {
			for (int j = 0; j < res.length; j++) {
				System.out.print(res[i][j] + " ");
				if (res[i][j] == 1)	resCheck+=1;
			}
			System.out.println();
		}
		System.out.println(resCheck);

		// 숫자 매칭 배열
		int[][] arr = new int[num][num];
		int cnt = 1;

		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr.length; j++) {
				arr[i][j] = cnt;
				cnt++;
				System.out.printf("%3s",arr[i][j]);
			}
			System.out.println();
		}

		// 목숨
		int count = 3;
		int[][] user = new int[num][num];

		while (count > 0) {

			System.out.println("현재 목숨 : " + count);
			for (int i = 0; i < user.length; i++) {
				for (int j = 0; j < user.length; j++) {
					if (user[i][j] == 1) {
						System.out.printf("%3s","■");
					} else if (user[i][j] == 3) {
						System.out.printf("%3s","X");
					} else {
						System.out.printf("%3s","□");
					}
				}
				System.out.println();
			}

			System.out.print("선택을 원하면 1번, X를 원하면 2번 선택 >>");
			int oxSelect = sc.nextInt();
			if (oxSelect == 1) {
				System.out.print("숫자 선택 >>");
				int choice = sc.nextInt();

				if (res[(choice - 1) / num][(choice - 1) % num] == 1) {
					user[(choice-1) / num][(choice - 1) % num] = 1;
					userCheck++;
				} else {
					count--;
				}
			} else if (oxSelect == 2) {
				System.out.print("숫자 선택 >>");
				int choice = sc.nextInt();

				if (res[(choice - 1) / num][(choice - 1) % num] == 0) {
					user[(choice - 1) / num][(choice - 1) % num] = 3;
				} else {
					System.out.println("숫자를 확인하세요.");
					continue;
				}
			}

			System.out.println(userCheck);
			if (userCheck == resCheck) {
				System.out.println("정답");
				break;
			}

		} // while문 종료
		if (cnt == 0) {
			System.out.println("목숨이 없습니다.");
		}
	}
	

	// 이미 클리어한 게임의 경우 랭크를 먼저 보여주기 위함
	public static void isCleared() {
		Scanner sc = new Scanner(System.in);
		String[] nums = {"①","②","③","④","⑤"};
		System.out.println("----------------랭크---------------");
		// 랭크는 1-5위까지만 보여줄까요
				for (int i = 0; i < nums.length; i++) {
					System.out.println("\t"+nums[i]);
				}

		System.out.println();
		System.out.println("  ① 다시 도전하기  ② 돌아가기");
		System.out.println("----------------------------------");
		int re_select = sc.nextInt();
		if (re_select == 1) {
			gamePlay();
		} else if (re_select == 2) {
//			levelChoice();
		}else {
			System.out.println("올바른 숫자를 입력하세요");
		}
	}

	public static void gamePlay() {
		int life = 0;
		Scanner sc = new Scanner(System.in);
		String[] stars = {"♥♥♥","♥♥♡","♥♡♡","♡♡♡"};

		System.out.println("---------------GAME--------------");
		System.out.println("ː xxx 님              코인 : 5개  ː ");
		System.out.println("ː                    힌트 : 0개   ː ");
		System.out.println("---------------------------------");
		

		System.out.println("GAME");

		System.out.println("---------------------------------");
		if (life == 3) {
			System.out.println("ː                          "+stars[0]+"  ː");
		}else if (life == 2) {
			System.out.println("ː                          "+stars[1]+"  ː");			
		}else if (life == 1) {
			System.out.println("ː                          "+stars[2]+"  ː");					
		}else if (life == 0) {
			System.out.println("ː                          "+stars[3]+"  ː");								
		}
		System.out.println("---------------------------------");
		
		
		if (life == 0) {
			System.out.println("=============================================");
			System.out.println("ː         ♡ 목숨이 0이 되었어요!                ː");
			System.out.println("ː    목숨을 구입해서 계속 플레이 하시겠습니까?       ː");
			System.out.println("ː ① 네! 계속할래요  ② 아니요 ㅠㅠ 포기하겠습니다     ː");
			System.out.println("=============================================");
			int yesOrNo = sc.nextInt();

			if (yesOrNo == 1) {
				System.out.println("--------------돌려돌려--------------");
				System.out.println("ː 코인 3개를 사용해 목숨뽑기를 진행합니다 ː ");
				System.out.println("----------------------------------");
				System.out.println();
				sleep();
//				gaCha();

			} else if (yesOrNo == 2) {
				System.out.println("\t포기하셨습니다\n");
				System.out.println("\t　 /) /) \n"
						+ "\t  (ಥ_ಥ)\n");
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
	public static void gaCha() {
		Random rd = new Random();
		Scanner sc = new Scanner(System.in);
		System.out.println("  Λ＿Λ\n" + "（ㆍωㆍ)つ━☆*。\n" + "⊂　　 ノ 　　　.뾰\n" + "　し-Ｊ　　　°。로 *´¨)\n"
				+ "　　　　　　..　.· ´¸.·롱*´¨) ¸.·*¨)\n" + "　　　　　　　　　　(¸.·´ ( ¸.'*\n" + "");
		int num = rd.nextInt(4);
		if (num == 0) {
			System.out.println("╭ ⁀ ⁀ ╮\n"
					+ "( '👅'　　)\n"
					+ "╰ ‿ ‿ ╯\n"
					+ "　　　　　　　　╭ ⁀ ⁀ ╮\n"
					+ "　　　　　　　　( '👅'　　)\n"
					+ "　　　　　　　　╰ ‿ ‿ ╯\n"
					+ "╭ ⁀ ⁀ ╮\n"
					+ "( '👅'　　)\n"
					+ "╰ ‿ ‿ ╯");
		}else if (num == 1) {
			System.out.print("\t (\\_/)\n"
					+ "\t( •.• )\n"
					+ "\t/ > •‧:❤️:‧•");
		}else if (num == 2) {
			System.out.println("\t (\\_/)\n"
					+ "\t( •.• )\n"
					+ "\t/ > •‧:❤️❤️:‧•");
		}else if (num == 3) {
			System.out.println("\t (\\_/)\n"
					+ "\t( •.• )\n"
					+ "\t/ > •‧:❤️❤️❤️:‧•");
		}
		sleep();
		// 목숨 + num 해야함
		System.out.println();
		
		System.out.println("----------------------------------");
		System.out.println("ː         "+num+"개의 목숨 UP            ː ");
		System.out.println("ː     ① 계속하기     ② 돌아가기      ː ");
		System.out.println("----------------------------------");
		
		int life_select = sc.nextInt();

		if (life_select == 1) {
			// 목숨 -1 해야함
			gamePlay();
		} else if (life_select == 2) {
//			levelChoice();
		} else { 
			System.out.println("올바른 숫자를 입력하세요");
		}
	}

}
