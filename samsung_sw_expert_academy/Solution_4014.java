package samsung_sw_expert_academy;

import java.util.Scanner;
import java.io.FileInputStream;

class Solution_4014
{
	static int n;
	static int x;
	static int[][] map;
	static int answer;
	
	public static void main(String args[]) throws Exception
	{
		Scanner sc = new Scanner(System.in);
		int T;
		T=sc.nextInt();
		
		for(int test_case = 1; test_case <= T; test_case++)
		{
			n = sc.nextInt();
			x = sc.nextInt();
			
			map = new int[n][n];
			
			for(int i=0; i<n; i++) {
				for(int j=0; j<n; j++) {
					map[i][j] = sc.nextInt();
				}
			}
			
			answer = 0;
			solve();
			System.out.println("#" + test_case + " " + answer);
			
		}
	}
	
	public static void solve() {
		for(int i=0; i<n; i++) {
			makeRunwayByHorizen(i);
			makeRunwayByVertical(i);
		}
	}
	
	public static void makeRunwayByHorizen(int i) {
		int h = map[i][0];
		int pre_way_len = 1;
		
		for(int j=1; j<n; j++) {
			if(map[i][j] > h+1 || map[i][j] < h-1) {
				return;
			} else if(map[i][j] == h) {
				pre_way_len++;
			} else if(map[i][j] == h+1){
				if(pre_way_len < x)
					return;
				
				pre_way_len = 1;
			} else if(map[i][j] == h-1) {
				int post_way = j;
				
				for(;post_way < n && post_way < j+x; post_way++) {
					if(map[i][post_way] != h-1)
						return;
				}
				
				if(post_way != j+x)
					return;
				
				j = j+x-1;
				pre_way_len = 0;
			}
			
			h = map[i][j];
		}
		
		answer++;
	}
	
	public static void makeRunwayByVertical(int j) {
		int h = map[0][j];
		int pre_way_len = 1;
		
		for(int i=1; i<n; i++) {
			if(map[i][j] > h+1 || map[i][j] < h-1) {
				return;
			} else if(map[i][j] == h) {
				pre_way_len++;
			} else if(map[i][j] == h+1){
				if(pre_way_len < x)
					return;
				
				pre_way_len = 1;
			} else if(map[i][j] == h-1) {
				int post_way = i;
				
				for(;post_way < n && post_way < i+x; post_way++) {
					if(map[post_way][j] != h-1)
						return;
				}
				
				if(post_way != i+x)
					return;
				
				i = i+x-1;
				pre_way_len = 0;
			}
			
			h = map[i][j];
		}
		
		answer++;
	}
}