/*
 * Wafi Hassan
 * Zombie Survival Game
 * 2020-11-04
 * */

public class Bullet {
    private double x, y, velX, velY;//initializing variables as doubles for precise measuring
    private boolean active=true;//setting a boolean variable to see if the bullet is visible or not, currently it is visible
    public Bullet(double x, double y, double ang) {//constructor class that takes x,y, and the angle it is facing
        this.x = x;//assigning the x value of the class equal to the variable x in the constructor
        this.y = y;//assigning the y value of the class equal to the variable y in the constructor
        velX=6*Math.cos(ang);//finding the x length of the bullet by using trig. The 5 is the speed(length) of the bullet, can be adjustable.
        velY=6*Math.sin(ang);//finding the y length of the bullet by using trig.
    }

    public void move(){
        x+=velX;//adding the trig value calculated aboce with whatever x value is given in the constructor, higher value=faster bullet
        y+=velY;//adding the trig value calculated aboce with whatever y value is given in the constructor
        if(x<0||x>1100||y<0||y>1100){//calculating if bullet goes off screen using boundaries and checking them.
            active=false;//since this is equal to false, then bullet would disappear
        }
    }

    public boolean isActive(){//creating a boolean setter and getter which returns/updates the status of the bullet
      return active;
    }

    public void hit(){//creating a hit method that will run when a bullet hits a zombie and will remove bullet
        active=false;
    }

    public int getX() { //creating a setter and getter which returns/updates the x value as a integer
      return (int) x; 
    }

    public int getY() { //creating a setter and getter which returns/updates the y value as a integer
      return (int) y; 
    }
}