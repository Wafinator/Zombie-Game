/*
 * Wafi Hassan
 * Zombie Survival Game
 * 2020-11-04
 * */

public class Zombie{
    private double x, y, vel=1.5;//initializing double variables and zombie speed
    private boolean active=true;//boolean variable
    //private int score=0;
    public Zombie(double x, double y) {
        this.x = x;
        this.y = y;
        //this.score=score;
    }

    public void move(Player p){
        double ang; //angle from zombie to player
        if (p.getX()>x) { //if player is on the right
            ang = Math.atan((p.getY()-y)/(p.getX()-x)); //tan inverse to find angle
        }
        else if (p.getX()<x) { //if player is on the left
            ang = Math.atan((p.getY()-y) / (p.getX()-x)) + Math.PI; //add pi to get actual angle
        }
        else { //if player is straight up or down
            if (p.getY()<y){ //if player is above
                ang= Math.PI / 2; //zombie faces up
            }
            else{ //if player is below
                ang = 3 * Math.PI / 2; //zombie faces down
            }
        }
        x+=vel*Math.cos(ang); //moving zombie the distance vel
        y+=vel*Math.sin(ang);
    }

    public boolean isActive(){
        return active;
    }

    public boolean checkHit(Bullet b){
        if(Math.abs(b.getX()-x)<20&&Math.abs(b.getY()-y)<20){ //if bullet falls in 40x40px box around zombie
          //score+=20;
            active=false; //zombie dies
            b.hit(); //bullet disappears
            return true;
        }
        return true;
    }
//setters and getters to get the x and y positions at all times
    public int getX() {
      return (int) x; 
    }

    public int getY() { 
      return (int) y; 
    }
}