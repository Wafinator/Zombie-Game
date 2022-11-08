import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ZombieGame extends JFrame implements ActionListener {
    private final Timer myTimer; //timer to call gameplay functions per frame
    private final Game game;

    public ZombieGame(){
        super("Zombie Survival Games"); //title
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setPreferredSize(new Dimension(1100,700)); //setting frame size
        pack();
        myTimer = new Timer(10, this);//new timer that will trigger every 10ms
        //creating new jPanel object
        game = new Game(this);//initialzing the game which will work on this class
        add(game);//adding the game instance
        setResizable(false);//window cannot be resizable
        setVisible(true);//window and contents are visible
    }

    public void start(){//start method which starts a timer
        myTimer.start(); 
    }
    public void actionPerformed(ActionEvent evt){ //calls gameplay functions
      game.run(); 
    } 

    public static void main(String[] arguments){
        new ZombieGame();
    }
}