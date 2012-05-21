package controller;


import config.GuiConfig;
import gui.*;
import processing.core.PApplet;
import java.util.LinkedList;


public class Main extends PApplet
{
    private Environment env;

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
        Coordinate2D co = env.get_board().get_clicked_coordinates();
        System.out.println("x "+co.section_x);
        System.out.println("y "+co.section_y);


        // testliste mit vordefinierten zügen!!
        LinkedList<Symbol> results = new LinkedList<Symbol>();

        results.add( new Cross(10) );
        results.add( new Circle(6) );
        results.add( new Cross(11) );
        results.add( new Circle(9) );
        results.add( new Cross(15) );
        results.add( new Circle(5) );
        results.add( new Cross(7) );
        results.add( new Circle(3) );
        results.add( new Cross(12) );
        results.add( new Circle(13) );
        results.add( new Cross(1) );
        results.add( new Circle(14) );
        results.add( new Cross(8) );
        results.add( new Circle(0) );
        results.add( new Cross(2) );

        results.add( new Circle(4) );



        env.get_board().set_results( results );
    }

}
