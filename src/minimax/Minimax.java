package minimax;

import java.util.ArrayList;
import java.util.List;

public class Minimax 
{	
	public enum Player 
	{MIN, MAX};
	
	Player player = Player.MAX;
	public static int boardSize;

	public Minimax(Player p, int boardSize) 
	{
		this.player = p;
		Minimax.boardSize = boardSize;
	}
	
	public State getMinimaxDecision(State initialState) 
	{
		long t1 = System.currentTimeMillis();
		
		Action bestAction = null;
		double bestUtility = Double.NEGATIVE_INFINITY;
		List<Action> actionList = getActions(initialState);
		
		for (Action action : actionList) 
		{
			double utility = minValue(getResult(initialState, action));
			if (utility > bestUtility) 
			{
				bestUtility = utility;
				bestAction = action;
			}
		}

		State resultState = getResult(initialState, bestAction);
		
		long t2 = System.currentTimeMillis(); 
		System.out.println("TIME: " + (t2-t1) + "ms");
		
		return resultState;		
	}
	
	private double minValue(State state) 
	{
		double utility = Double.POSITIVE_INFINITY;
		
		if (terminalTest(state)) 
			return utility(state);
		
		for (Action action: getActions(state)) 
		{
			double tmp = maxValue(getResult(state, action));
			utility = Math.min(utility,tmp);
		}
		return utility;
	}
	
	private double maxValue(State state) 
	{
		double utility = Double.NEGATIVE_INFINITY;
		
		if (terminalTest(state)) 
			return utility(state);

		for (Action action: getActions(state)) 
		{
			double tmp = minValue(getResult(state,action));
			utility = Math.max(utility, tmp);
		}
		return utility;
	}

	private State getResult(State state, Action action) 
	{
		State resultState = new State();
		
		for (int i=0; i<boardSize; i++) 
			for (int j=0; j<boardSize; j++) 
				resultState.field[i][j] = state.field[i][j];

		if (action.player == Player.MAX) 
			resultState.field[action.col][action.row] = 1;
		else 
			resultState.field[action.col][action.row] = -1;
	
		return resultState;
	}
	
	private List<Action> getActions(State state) 
	{
		List<Action> actions = new ArrayList<Action>();
		int min = 0, max = 0;

		for (int i=0; i<boardSize; i++) 
		{
			for (int j =0; j<boardSize; j++) 
			{
				if (state.field[i][j] == -1) 
					min++;
				else if (state.field[i][j] == 1) 
					max++;
			}
		}
		
		Player player = Player.MIN;
		if (max <= min) 
			player = Player.MAX;

		for (int i=0; i<boardSize; i++) 
		{
			for (int j =0; j<boardSize; j++) 
			{
				if (state.field[i][j] == 0) 
				{
					Action action = new Action();
					action.col = i;
					action.row = j;
					action.player = player;
					actions.add(action);
				}
			}
		}
		return actions;
	}
	
	private int utility(State state) 
	{
		int val = 0;
		for (int i=0; i<boardSize; i++ ) 
			val += state.field[i][i];
	
		if (val == boardSize && player == Player.MAX || val == -boardSize && player == Player.MIN) 
			return 1;
		
		if (val == -boardSize && player == Player.MAX || val == boardSize && player == Player.MIN) 
			return -1;
		
		val=0;
		for (int i=0; i<boardSize; i++) 
		{
			int j = boardSize-i-1;
			val += state.field[i][j];
		}
		if (val == boardSize && player == Player.MAX || val == -boardSize && player == Player.MIN) 
			return 1;
		
		if (val == -boardSize && player == Player.MAX || val == boardSize && player == Player.MIN) 
			return -1;
		
		for (int i=0; i<boardSize; i++) 
		{
			val = 0;
			for (int j=0; j<boardSize; j++) 
				val += state.field[i][j];

			if (val == boardSize && player == Player.MAX || val == -boardSize && player == Player.MIN) 
				return 1;

			if (val == -boardSize && player == Player.MAX || val == boardSize && player == Player.MIN) 
				return -1;
		}

		for (int j=0; j<boardSize; j++) 
		{
			val = 0;
			for (int i=0; i<boardSize; i++) 
				val += state.field[i][j];
			
			if (val == boardSize && player == Player.MAX || val == -boardSize && player == Player.MIN) 
				return 1;
			
			if (val == -boardSize && player == Player.MAX || val == boardSize && player == Player.MIN) 
				return -1;
		}
		return 0;
	}
	
	private boolean terminalTest(State state) 
	{
		int playedFields = 0;
		
		for (int i=0; i<boardSize; i++) 
		{
			int total = 0;
			for (int j=0; j<boardSize; j++) 
			{
				if (state.field[i][j] != 0) 
					playedFields++;
				
				total += state.field[i][j];
			}
			if (Math.abs(total) == boardSize) 
				return true;
		}
		
		if (playedFields == boardSize*boardSize)
			return true;

		for (int j=0; j<boardSize; j++) 
		{
			int total = 0;
			for (int i=0; i<boardSize; i++) 
				total += state.field[i][j];

			if (Math.abs(total) == boardSize) 
				return true;
		}
		
		int total = 0;
		for (int i=0; i<boardSize; i++ ) 
			total += state.field[i][i];
		
		if (Math.abs(total) == boardSize) 
			return true;
		
		total=0;
		for (int i=0; i<boardSize; i++) 
		{
			int j = boardSize-i-1;
			total += state.field[i][j];
		}
		
		if (Math.abs(total) == boardSize) 
			return true;
		
		return false;		
	}
}