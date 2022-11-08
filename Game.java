import java.awt.*;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.*; 
import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;



public class Game extends JPanel implements MouseListener, MouseMotionListener, KeyListener {//using jpanel and implementing methods that will find keys/mouse button and motions activity
  private final int RIGHT = 0, UP = 1, LEFT = 2, DOWN = 3;//setting player motion values from 0-3 

  private String screen="GAME";//making a new string variable called screen, which will be useful to check the current game status 
  private final boolean[] keys = new boolean[KeyEvent.KEY_LAST + 1];//initializing a keys variable that will be assigned under the last key, 
                                                                    //+1 is there because(e.g.100 keys=[100] which only goes 0-99)
  
  private boolean frozen=false;//initializing a boolean variable to check if the screen is frozen which will come into use when a zombie kills us

  private final ZombieGame mainFrame;//initializing a game window called mainframe

  public Image player1 = Toolkit.getDefaultToolkit().getImage("images/player1.gif");//loading my player image
  public Image enemy = Toolkit.getDefaultToolkit().getImage("images/enemyBasic.gif");//loading enemy image
  public Image star = Toolkit.getDefaultToolkit().getImage("images/Health.gif");//loading heart image for powerup

  public ArrayList<Zombie> zombies = new ArrayList<>();//creating an arraylist for zombies, this will be used to check collisions later on
  public ArrayList<Bullet> bullets = new ArrayList<>();//creating an arraylist for bullets, this will be used to check collisions later on

  private Player player;//initializing our player
  
  boolean onscreen= true;//boolean variable onscreen is true, which will come into use later on

  int lvl = 0, lives=3,score=0,invincible=300;//intializing our level,lives, score and the time we are invincible
  
  

  public Game(ZombieGame m) {//Game method which is basically a "window" method and initializes our game 
    mainFrame = m;//setting mainframe to m
    setSize(1100, 700);//setting our window size to 1100x700
    addKeyListener(this);//method that will listen to keyactions
    addMouseListener(this);//method that will listen to mouse actions
    addMouseMotionListener(this);//method that will listen to the movements which the mouse relative to it's surface
    newLevel();//calling the newlevel() method to "start" the game
    player = new Player(550, 350);//spawning in our player at 550,350 and calling player method
  }

  //Got help from my friend for this method
  public void run() {//run method 
    if(!frozen&&screen.equals("GAME")) {//if screen isnt frozen and the game instance is true...
      invincible--;//your invincibility is going to start to run out as the frames load
      System.out.println(invincible);//prints the invincibility time left
      movePlayer();//calling the movePlayer() method so the player can move around
      for (Bullet b : bullets) {//a for loop that will loop through all bullets and will move the bullets accordingly
        b.move();
      }
      bullets.removeIf(b -> !b.isActive());//this my friend told me to use instead of a for loop since for loop cant look 
                                           //through each and this will look at each individual bullet and will delete it if its inactive
      
      for (Zombie z : zombies) {//looks at the zombie list will check each and individual zombie if they got shot,then it will move it towards player
        checkHit(z);
        z.move(player);
        for (Bullet b : bullets) {//basically a 2d list that will go through each bullet and zombie and check for interactions
          if (z.checkHit(b)){
            score+=1;
          }
          

        }
      }
      zombies.removeIf(z -> !z.isActive());//same thing as i said above but this time it will remove the zombie if its inactive(got shot or killed us)

    /*
    for(Zombie z:zombies){
      if(!z.isActive()){
        zombies.remove(z);
      }
    }
     */

      if (zombies.size() == 0) {//if all the zombies are dead, nextlevel() method will be called
        newLevel();
      }
    }
    repaint();//updates our screen/game
  }

  public void checkHit(Zombie z){//method that will check if our zombie got hit with a bullet
    if(invincible<=0) {//if the player's invincibility time runs out
      if (Math.abs(z.getX() - player.getX()) < 40 && Math.abs(z.getY() - player.getY()) < 40) {//got help from friend for this aswell,
      //takes the absolute value of zombie's x value and subtracts it from the player's x value,(same for y),and if it is less than 
        //40(players hitbox), then our player(dies) and loses a life. if he has no lives left its gameover for him.
        lives--;
        //score-=20;
        if(lives<=0){
          screen="LOSS";
          return;
        }
        invincible = 300;//sets invincibility period back to 3s or 300ms/100fps.
        frozen = true;//player becomes frozen as he is hit
      }
    }
  }

  public void newLevel() {//new method that will add more zombies at random locations based on level
    lvl ++;
    if (lvl == 1) {
      for (int i = 0; i < 5; i++) {
        zombies.add(new Zombie(randint(0,1100),randint(0,700)));
      }
    }
    if (lvl == 2) {
      for (int i = 0; i < 7; i++) {
        zombies.add(new Zombie(randint(0,1100),randint(0,700)));
      }
    }
    if (lvl == 3) {
      for (int i = 0; i < 9; i++) {
        zombies.add(new Zombie(randint(0,1100),randint(0,700)));
      }
    }
    if(lvl==4){
      for (int i = 0; i < 11; i++) {
        zombies.add(new Zombie(randint(0,1100),randint(0,700)));
      }
    }
    if(lvl==5){//you won if you get level 5!
      screen="WIN";
    }
  }

  public void movePlayer() {//basic method that will manage our movements
    if (keys[KeyEvent.VK_W]){
      player.move(UP);
    }
    if (keys[KeyEvent.VK_A]){
      player.move(LEFT);
    }
    if (keys[KeyEvent.VK_S]){
      player.move(DOWN);
    }
    if (keys[KeyEvent.VK_D]){
      player.move(RIGHT);
    }
  }

  public void mousePressed(MouseEvent e) {//method that will check what we pressed on the mouse
    frozen=false;//we are no longer frozen
    int mx = e.getX(), my = e.getY();//getting the x and y values and setting them to new variables
    double angle;//making a double variable for our angle.
    if (mx > player.getX()) {//if the mouse clicked x position is greater than the player x position...
      angle = Math.atan((double) (my - player.getY()) / (double) (mx - player.getX()));//using unit circle, tan=y/x so tan inverse of dy/dx
    } else if (mx < player.getX()) {//if the mouse clicked x distance is less than the player's x position... 
      angle = Math.atan((double) (my - player.getY()) / (double) (mx - player.getX())) + Math.PI;//same thing but adding pi since your angle appears on the right side
    }
    
    else {//else if dx=0, so if its straight up 
      if (my < player.getY()){//if mouse y is less than player y
        angle = Math.PI / 2;//the angle is pi/2, which is straight up
      }
      else {//else if its straight down
        angle = 3 * Math.PI / 2;//3pi/2 is 270 degress which is straight down
      }
    }
    bullets.add(new Bullet(player.getX(), player.getY(), angle));//adding a new bullet at the player's x and y coordinate at the angle
  }

  public void mouseReleased(MouseEvent e) { }//need these in order for mouselistener to work

  public void mouseEntered(MouseEvent e) { }

  public void mouseExited(MouseEvent e) { }

  public void mouseMoved(MouseEvent e) { }

  public void mouseDragged(MouseEvent e) { }

  public void mouseClicked(MouseEvent e) {

  }

  public void keyPressed(KeyEvent e) { //update keys pushed down
    frozen=false;
    keys[e.getKeyCode()] = true;
  }

  public void keyReleased(KeyEvent e) { //keys released
    keys[e.getKeyCode()] = false;
  }

  public void keyTyped(KeyEvent e) {//need this for keylistener else it will error
  }

  public void addNotify() {
    super.addNotify();
    requestFocus();//window focuses
    mainFrame.start();//starts the mainframe
  }

  public void paintComponent(Graphics g) {//graphics method 
    
    g.setColor(Color.black);
    g.fillRect(0, 0, 1100, 700);
    Random rand = new Random();
    
    
    
    
    
    
    if(screen.equals("GAME")) {
      g.setColor(Color.YELLOW);
      for (Bullet b : bullets) {
        g.fillOval(b.getX() - 2, b.getY() - 2, 4, 4);
      }
      g.drawImage(player1, player.getX() - 25, player.getY() - 25, null);
      for (Zombie z : zombies) {
        g.drawImage(enemy, z.getX() - 25, z.getY() - 25, null);
      }
      
      
      //g.drawImage(star,rand.nextInt(1100-0+1),rand.nextInt(700-0+1),null);
        
      g.setColor(Color.red);
      g.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
      g.drawString("Level :" + lvl, 10, 25);
      g.drawString("Enemies left:" + zombies.size(), 10, 55);
      g.setColor(Color.blue);
      g.drawString("Lives " + lives, 10, 85);
      g.drawString("Score: "+score,950,25);
      //g.drawImage(star,rand.nextInt(1100-50+1),rand.nextInt(700-50+1),null);//draws a star at random points.
    }
    else if(screen.equals("WIN")){
      g.setFont(new Font("Comic Sans MS", Font.BOLD, 50));
      g.setColor(Color.white);
      g.drawString("Game over. You won!", 300, 350);
    }
    else if(screen.equals("LOSS")){
      
      g.setFont(new Font("Comic Sans MS", Font.BOLD, 50));
      g.setColor(Color.red);
      g.drawString("Game over. You lost!", 300, 350);
    }
  }

  public static int randint(int low, int high) { //returns random int between 2 ints inclusive
    return (int) (Math.random() * (high - low + 1) + low);
  }
}