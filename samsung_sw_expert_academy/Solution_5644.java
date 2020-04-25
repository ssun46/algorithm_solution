package samsung_sw_expert_academy;

/////////////////////////////////////////////////////////////////////////////////////////////
// 기본 제공코드는 임의 수정해도 관계 없습니다. 단, 입출력 포맷 주의
// 아래 표준 입출력 예제 필요시 참고하세요.
// 표준 입력 예제
// int a;
// double b;
// char g;
// String var;
// long AB;
// a = sc.nextInt();                           // int 변수 1개 입력받는 예제
// b = sc.nextDouble();                        // double 변수 1개 입력받는 예제
// g = sc.nextByte();                          // char 변수 1개 입력받는 예제
// var = sc.next();                            // 문자열 1개 입력받는 예제
// AB = sc.nextLong();                         // long 변수 1개 입력받는 예제
/////////////////////////////////////////////////////////////////////////////////////////////
// 표준 출력 예제
// int a = 0;                            
// double b = 1.0;               
// char g = 'b';
// String var = "ABCDEFG";
// long AB = 12345678901234567L;
//System.out.println(a);                       // int 변수 1개 출력하는 예제
//System.out.println(b); 		       						 // double 변수 1개 출력하는 예제
//System.out.println(g);		       						 // char 변수 1개 출력하는 예제
//System.out.println(var);		       				   // 문자열 1개 출력하는 예제
//System.out.println(AB);		       				     // long 변수 1개 출력하는 예제
/////////////////////////////////////////////////////////////////////////////////////////////
import java.util.Scanner;
import java.io.FileInputStream;

/*
   사용하는 클래스명이 Solution 이어야 하므로, 가급적 Solution.java 를 사용할 것을 권장합니다.
   이러한 상황에서도 동일하게 java Solution 명령으로 프로그램을 수행해볼 수 있습니다.
 */

class Solution_5644
{
	static int[][] H;
	static int p_n, bc_n;
	static int[][] p;
	static int[][] bc;
	static int answer;
	
	public static void main(String args[]) throws Exception
	{
		/*
		   아래의 메소드 호출은 앞으로 표준 입력(키보드) 대신 input.txt 파일로부터 읽어오겠다는 의미의 코드입니다.
		   여러분이 작성한 코드를 테스트 할 때, 편의를 위해서 input.txt에 입력을 저장한 후,
		   이 코드를 프로그램의 처음 부분에 추가하면 이후 입력을 수행할 때 표준 입력 대신 파일로부터 입력을 받아올 수 있습니다.
		   따라서 테스트를 수행할 때에는 아래 주석을 지우고 이 메소드를 사용하셔도 좋습니다.
		   단, 채점을 위해 코드를 제출하실 때에는 반드시 이 메소드를 지우거나 주석 처리 하셔야 합니다.
		 */
		//System.setIn(new FileInputStream("res/input.txt"));

		/*
		   표준입력 System.in 으로부터 스캐너를 만들어 데이터를 읽어옵니다.
		 */
		Scanner sc = new Scanner(System.in);
		int T;
		T=sc.nextInt();
		/*
		   여러 개의 테스트 케이스가 주어지므로, 각각을 처리합니다.
		*/

		for(int test_case = 1; test_case <= T; test_case++)
		{
			H = new int[][]{{1, 1}, {10, 10}};
			
			p_n = sc.nextInt();
			bc_n = sc.nextInt();
			
			p = new int[2][p_n];
			bc = new int[bc_n+1][4];
			
			// path
			for(int i=0; i<2; i++) {
				for(int j=0; j<p_n; j++) {
					p[i][j] = sc.nextInt();
				}
			}
			
			// battery charger
			for(int i=1; i<=bc_n; i++) {
				bc[i][0] = sc.nextInt();
				bc[i][1] = sc.nextInt();
				bc[i][2] = sc.nextInt();
				bc[i][3] = sc.nextInt();
			}
			
			answer = 0;
			solve();
			System.out.println("#" + test_case + " " + answer);
		}
	}
	
	public static void solve() {
		int[] mx = {0, 0, 1, 0, -1};
		int[] my = {0, -1, 0, 1, 0};
		
		for(int mv=0; mv<=p_n; mv++) {
			int [][] bc_list = new int[2][2];
			
			for(int k=0; k<2; k++) {
				for(int i=1; i<=bc_n; i++) {
			
					if(bc[i][2] >= getDist(k, i)) {
					
						if(bc[i][3] >= bc[bc_list[k][0]][3]) {
							bc_list[k][1] = bc_list[k][0];
							bc_list[k][0] = i;
						} else if(bc[i][3] >= bc[bc_list[k][1]][3]) {
							bc_list[k][1] = i;
						}
					}
				}
			}
			
			if(bc_list[0][0] == bc_list[1][0]) {
				answer += Math.max(bc[bc_list[0][0]][3], Math.max(bc[bc_list[0][0]][3] + bc[bc_list[1][1]][3], bc[bc_list[0][1]][3] + bc[bc_list[1][0]][3]));
			} else {
				answer += (bc[bc_list[0][0]][3] + bc[bc_list[1][0]][3]);
			}
			
			for(int i=0; i<2; i++) {
				if(mv < p_n) {
					H[i][0] += mx[p[i][mv]];
					H[i][1] += my[p[i][mv]];
				}
			}
		}
	}
	
	public static int getDist(int h_idx, int bc_idx) {
		return  (int)(Math.abs(bc[bc_idx][0] - H[h_idx][0]) + Math.abs(bc[bc_idx][1] - H[h_idx][1]));
	}
}