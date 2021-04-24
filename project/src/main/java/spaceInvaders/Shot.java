package spaceInvaders;


import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.lang.Math;

public class Shot {
	
	private double posx;
	private float posy;
	private Color shotColor = Color.BLACK;
	private double shotRadius = 5;
	private Circle c;
	private int shotSpeed = 5;
	private Board board;
	
	public Shot(double posx, Circle c, Board board) {
		this.posx = posx+300;
		this.posy = board.getBoardHeight() - board.getPlayer().getPlayerWidth();
		this.c = c;
		this.board = board;
	}

	
	public Circle getC() {
		return c;
	}
	
	public double getPosx() {
		return posx;
	}

	public void setPosx(double posx) {
		this.posx = posx;
	}

	public float getPosy() {
		return posy;
	}
		
	public Color getShotColor() {
		return shotColor;
	}
	
	public double getShotRadius() {
		return shotRadius;
	}
	
	public Alien hitsAlien() {
		if(board.getAlienGroup().size() != 0) {
			for (Alien alien : board.getAlienGroup()) {
				double dist = (alien.getPosx() - this.posx)*(alien.getPosx() - this.posx) + (alien.getPosy() - this.posy)*(alien.getPosy() - this.posy);
				if(dist <= (this.shotRadius + alien.getRadius())*(this.shotRadius + alien.getRadius())) {
					board.removeShot(this);
					board.setScore(10);
					return alien;
				}
			}
		}
		return null;
	}
	
	public void moveShot() {
		this.posy -= shotSpeed;
		this.c.setCenterY(this.posy);
		if (this.posy < -10) {
			board.removeShot(this);
		}
	}
	
	public int getShotSpeed() {
		return shotSpeed;
	}
}

