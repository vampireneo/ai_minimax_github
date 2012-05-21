package minimax;

import java.util.ArrayList;
import java.util.List;


public class Minimax 
{	
	public enum Player 
	{MIN, MAX};
	
	public static int boardSize;
	
	Player p = Player.MAX;
	int termCount =0;
	
	public Minimax(Player p, int boardSize) 
	{
		this.p = p;
		Minimax.boardSize = boardSize;
	}
	
	public State getMinimaxDecision(State initial) 
	{
		this.termCount = 0;
		long now = System.currentTimeMillis();
		Action bestAction = null;
		double bestUt = Double.NEGATIVE_INFINITY;
		List<Action> l = getActions(initial);
		for (Action a : l) {
			State tmp = getResult(initial,a);
			double minVal = minValue(tmp);
			if (minVal > bestUt) {
				bestUt = minVal;
				bestAction = a;
			}
		}

		State res = getResult(initial, bestAction);
		long then = System.currentTimeMillis(); 
		System.out.println("terminal nodes:" + this.termCount + " calculation took: " + (then-now) + "ms");
		return res;		
	}
	
	private double minValue(State state) 
	{
		if (terminalTest(state)) {
			return utility(state);
		}
		
		double ut = Double.POSITIVE_INFINITY;
		for (Action a: getActions(state)) {
			State tmp = getResult(state, a);
			ut = Math.min(ut,maxValue(tmp) );
		}
		
		return ut;
	}
	
	private double maxValue(State state) {
		if (terminalTest(state)) {
			return utility(state);
		}
		double ut = Double.NEGATIVE_INFINITY;
		for (Action a: getActions(state)) {
			State tmp = getResult(state,a);
			ut = Math.max(ut, minValue(tmp));
		}
		
		return ut;
	}

	private State getResult(State s, Action a) 
	{
		State ret = new State();
		ret.field = new int[boardSize][boardSize];
		for (int i=0; i < boardSize; i++) {
			for (int j=0; j < boardSize; j++) {
				ret.field[i][j] = s.field[i][j];
			}
		}
		if (a.p == Player.MAX) {
			ret.field[a.col][a.row] = 1;
		} else {
			ret.field[a.col][a.row] = -1;
		}	
		return ret;
	}
	
	private List<Action> getActions(State state) 
	{
		List<Action> list = new ArrayList<Action>();
		int max = 0;
		int min = 0;
		for (int i=0; i < boardSize; i++) {
			for (int j =0; j < boardSize; j++) {
				if (state.field[i][j] == -1) {
					min++;
				} else if (state.field[i][j] == 1) {
					max++;
				}
			}
		}
		Player p = null;
		if (max <= min) {
			p = Player.MAX;
		} else {
			p = Player.MIN;
		}
		for (int i=0; i < boardSize; i++) {
			for (int j =0; j < boardSize; j++) {
				if (state.field[i][j] == 0) {
					Action a = new Action();
					a.p = p;
					a.col = i;
					a.row = j;
					list.add(a);
				}
			}
		}
		
		return list;
	}
	
	private int utility(State state) {
		
		
		
		if (!terminalTest(state)) {
			throw new RuntimeException("not a terminal state:\n" + state);
		} else {
			this.termCount++;
			if (termCount % 1000000 == 0) {
				System.out.println("terminal nodes visited: " + this.termCount); 
			}
		}
		
		//columns
		for (int i=0; i < boardSize; i++) {
			int c = 0;
			for (int j=0; j < boardSize; j++) {
				c += state.field[i][j];
			}
			if (c == boardSize && p == Player.MAX || c == -boardSize && p == Player.MIN) {
				return 1;
			}
			if (c == -boardSize && p == Player.MAX || c == boardSize && p == Player.MIN) {
				return -1;
			}
		}
		
		//rows
		for (int j=0; j < boardSize; j++) {
			int c = 0;
			for (int i=0; i < boardSize; i++) {
				c += state.field[i][j];
			}
			if (c == boardSize && p == Player.MAX || c == -boardSize && p == Player.MIN) {
				return 1;
			}
			if (c == -boardSize && p == Player.MAX || c == boardSize && p == Player.MIN) {
				return -1;
			}
		}
		
		

		//diagonal
		//diagonal
		int c = 0;
		for (int i=0; i < boardSize; i++ ) {
			c += state.field[i][i];
		}
		if (c == boardSize && p == Player.MAX || c == -boardSize && p == Player.MIN) {
			return 1;
		}
		if (c == -boardSize && p == Player.MAX || c == boardSize && p == Player.MIN) {
			return -1;
		}
		
		c=0;
		for (int i=0; i < boardSize; i++) {
			int j = boardSize -i - 1;
			c += state.field[i][j];
		}
		if (c == boardSize && p == Player.MAX || c == -boardSize && p == Player.MIN) {
			return 1;
		}
		if (c == -boardSize && p == Player.MAX || c == boardSize && p == Player.MIN) {
			return -1;
		}
		return 0;
	}
	
	private boolean terminalTest(State state) {
		//columns
		int ones = 0;
		
		for (int i=0; i < boardSize; i++) {
			int c = 0;
			for (int j=0; j < boardSize; j++) {
				c += state.field[i][j];
				if (state.field[i][j] != 0) {
					ones++;
				}
			}
			if (Math.abs(c) == boardSize) {
				return true;
			}
		}
		
		if (ones == boardSize*boardSize) {
			return true;
		}

		//rows
		for (int j=0; j < boardSize; j++) {
			int c = 0;
			for (int i=0; i < boardSize; i++) {
				c += state.field[i][j];
			}
			if (Math.abs(c) == boardSize) {
				return true;
			}
		}
		

		//diagonal
		int c = 0;
		for (int i=0; i < boardSize; i++ ) {
			c += state.field[i][i];
		}
		if (Math.abs(c) == boardSize) {
			return true;
		}
		
		c=0;
		for (int i=0; i < boardSize; i++) {
			int j=boardSize -i -1;
			c += state.field[i][j];
		}
		if (Math.abs(c) == boardSize) {
			return true;
		}
		
		return false;		
	}

/*	// MAIN//
	public static void main(String  [] args) {
		State s1 = new State();
		s1.field[0][0] = 1;
//		s1.field[2][0] = -1;
////		s1.field[2][2] = 1;
////		s1.field[1][2] = -1;
//////////		
////		s1.field[0][1] = -1;
////		s1.field[0][2] = 1;
//////		s1.field[1][2] = -1;
////		s1.field[1][0] = 1;
////		s1.field[2][0] = -1;
		Minimax mm1 = new Minimax(Player.MAX, 4);
		Minimax mm2 = new Minimax(Player.MIN, 4);
		int iter = 0;
		System.out.println(s1);
		for (int i=0; i < 9; i++) {
			if (iter %2 == 0) {
				s1=mm1.getMinimaxDecision(s1);
			} else {
				s1=mm2.getMinimaxDecision(s1);
			}
			System.out.println(s1);
			iter++;
		}
	}*/
}
