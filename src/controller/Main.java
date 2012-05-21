package controller;


import config.GuiConfig;
import gui.*;
import processing.core.PApplet;
import java.util.LinkedList;

import minimax.Minimax;
import minimax.State;
import minimax.Minimax.Player;


public class Main extends PApplet
{
    private Environment env;
    private final static int BOARD_SIZE = 3;

    public static void main(String[] args)
    {
        PApplet.main(new String[]{ "controller.Main"});	
    }

    public void setup()
    {
        size(GuiConfig.SCREEN_WIDTH, GuiConfig.SCREEN_HEIGHT);
        this.screenWidth  = GuiConfig.SCREEN_WIDTH;
        this.screenHeight = GuiConfig.SCREEN_HEIGHT;
        background(100, 100, 100);
        env = new Environment(this);
    }

    public void draw()
    {
        env.draw();
    }

    public void mouseClicked()
    {
        // testliste mit vordefinierten z√ºgen!!
        LinkedList<Symbol> results = new LinkedList<Symbol>();
        Minimax mmPlayerMax = new Minimax(Player.MAX, BOARD_SIZE);
		Minimax mmPlayerMin = new Minimax(Player.MIN, BOARD_SIZE);
    	State prevState = new State();
    	State currState = prevState;
    	
        Coordinate2D co = env.get_board().get_clicked_coordinates();
        System.out.println("x "+co.section_x);
        System.out.println("y "+co.section_y);
        //TODO: click übernehmen
		prevState.field[0][0] = 1;	
		results.add(new Cross(0));

		System.out.println(prevState.toString());
		for (int i=0; i<BOARD_SIZE*BOARD_SIZE-1; i++) 
		{
			if (i%2 == 0) 
				currState = mmPlayerMax.getMinimaxDecision(prevState);
			else 
				currState = mmPlayerMin.getMinimaxDecision(prevState);
		
			results.add(this.stateToSymbol(prevState, currState));
			
			prevState = currState;
			System.out.println(prevState.toString());
		}
        
//        results.add( new Cross(10) );
//        results.add( new Circle(6) );
//        results.add( new Cross(11) );
//        results.add( new Circle(9) );
//        results.add( new Cross(15) );
//        results.add( new Circle(5) );
//        results.add( new Cross(7) );
//        results.add( new Circle(3) );
//        results.add( new Cross(12) );
//        results.add( new Circle(13) );
//        results.add( new Cross(1) );
//        results.add( new Circle(14) );
//        results.add( new Cross(8) );
//        results.add( new Circle(0) );
//        results.add( new Cross(2) );
//
//        results.add( new Circle(4) );



        env.get_board().set_results( results );
    }

    
    private Symbol stateToSymbol(State prevState, State currState)
    {
    	for(int i=0; i<BOARD_SIZE; i++)
    	{
    		for(int j=0; j<BOARD_SIZE; j++)
    		{
    			if(prevState.field[i][j] != currState.field[i][j])
    			{
    				if(currState.field[i][j] == 1)
    					return new Cross(i*BOARD_SIZE+j);
    				else
    					return new Circle(i*BOARD_SIZE+j);
    			}
    		}
    	}
    	return null;
    }  
}
