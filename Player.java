
//code from this class taken from https://www.youtube.com/watch?v=SKFNouTFgto&ab_channel=RealTutsGML video series

public class Player{

  private final int RIGHT=0,UP=1,LEFT=2,DOWN=3;//initializing movements as int values

  private double x, y, velX=2, velY=2;// initializing our vel and x and y as doubles
  
  public Player(double x,double y) {//method that will take this class x and make it equal to x
    this.x=x;
    this.y=y;
  }

  public void move(int dir) {//basic direction method
    if(dir==RIGHT) x+=velX;
    if(dir==LEFT) x-=velX;
    if(dir==UP) y-=velY;
    if(dir==DOWN) y+=velY;
  } 

//getters setters stuff to update x and y values
  public int getX(){
    return(int)x;
  }

  public int getY(){
    return (int)y;
  }
  
}