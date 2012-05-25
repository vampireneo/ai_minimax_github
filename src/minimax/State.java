package minimax;

public class State 
{
	public int[][] field;
	
	public State()
	{
		field = new int[Minimax.boardSize][Minimax.boardSize];
	}

    public String toString() 
    {
    	String s = "";
        for (int i=0; i<Minimax.boardSize; i++) 
        {
        	s += "|";
        	for (int j=0; j<Minimax.boardSize; j++) 
        	{
                if (field[i][j] == 1) 
                	s += "X";
                else if (field[i][j] == -1) 
                    s += "O";
                else 
                    s += " ";
        	}
                s += "|\n";
        }
        return s;
    }
}
