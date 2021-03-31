package spaceInvaders;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;


public class GameController{
		
	private int boardWidth = 600;
	private int boardHeight = 400;
	private int playerWidth = 50;
	private double alienAnimDuration = 0.1;
	private int direction = 0;
	private int targetFPS = 30;
	private int cycleDuration = 1000 / targetFPS;
	private int speed = 3;
	private int shotSpeed = 3;
	int frameCounter = 0;
	private int secondsPerAlienRow = 5;
	private double newPosX;

	@FXML
    private Pane pane;
	
	@FXML
	private Rectangle rectangle;
	private Player player = new Player("Ola");
	private Board board = new Board(player);
	private List<Circle> alienCircles = new ArrayList<Circle>();
	private List<Circle> shotCircles = new ArrayList<Circle>();
	
	@FXML
	public void startGame() {
		System.out.println("start");
		System.out.println(cycleDuration);
		board.startGame();
		pane.requestFocus();
		
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(cycleDuration), event -> {
			frameCounter += 1;
			newPosX = player.getPosx() + speed * direction;
			if(newPosX < -(boardWidth - playerWidth) / 2) {
				newPosX = -(boardWidth - playerWidth) / 2;
			}
			else if(newPosX > (boardWidth - playerWidth) / 2) {
				newPosX = (boardWidth - playerWidth) / 2;
			}
			player.setPosx(newPosX);
			moveRectangle();
			updatePosOfShots();
			moveShots();
			if (frameCounter % (targetFPS * secondsPerAlienRow) == 0) {
				if(!board.getEndGame() == true) {
					moveAlienRow(); 
				} else {
					Platform.exit();
				}
			}
		}));
		
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
	}
	
	public int getDirection() {
		return direction;
	}
	
	public void setDirection(int direction) {
		this.direction = direction;
	}
	
	
	
	// Hvorfor funker ikke denne? Jeg vil at den skal se n�r piltastene blir sluppet opp :(
	// DEN FUNKER N� WOHOOO
	
	@FXML
	public void KeyReleased(KeyEvent event) {
		if(event.getCode() == KeyCode.LEFT) {
			if(getDirection() == -1) {
				setDirection(0);
			}
		} else if(event.getCode() == KeyCode.RIGHT) {
			if(getDirection() == 1) {
				setDirection(0);
			}
		}
	}
	
   @FXML
    public void KeyPressed(KeyEvent event) {
    	if (event.getCode() == KeyCode.LEFT) {
    		setDirection(-1);
    		// player.moveLeft();
    		moveRectangle();
  
    	} else if (event.getCode() == KeyCode.RIGHT) {
    		setDirection(1);
    		// player.moveRight();
    		moveRectangle();
    	
    	} else if (event.getCode() == KeyCode.SPACE) {
    		shoot();
		}
	}
    
    @FXML
    public void moveRectangle(){
    	TranslateTransition transition = new TranslateTransition();
		transition.setDuration(Duration.millis(cycleDuration));
		transition.setToX(player.getPosx());
		transition.setNode(rectangle);
		transition.play(); 
		
		/*TranslateTransition transition = new TranslateTransition();
		transition.setDuration(Duration.seconds(3));
		transition.setToX(50);
		transition.setToY(-400);
		transition.setCycleCount(Animation.INDEFINITE);
		transition.setAutoReverse(true);
		transition.setNode(rectangle);
		transition.play(); 
		*/
    	
    }
    
    @FXML
    public void moveAlienRow() {
    	if(board.getAlienGroup().size() != 0 ) {
    		board.pushAliensDown();
    		for (int i = 0; i<board.getAlienGroup().size();i++) {
    			Circle c = alienCircles.get(i);
    			TranslateTransition transition = new TranslateTransition();
    			transition.setDuration(Duration.seconds(alienAnimDuration));
    			transition.setToY(board.getAlienGroup().get(i).getPosy());
    			transition.setNode(c);
    			transition.play(); 
    		}
    	}
    	board.drawAlienRow();
    	for (int i = 0; i<board.getAliensPerRow();i++) {
    		Alien alien = board.getAlienGroup().get(board.getAlienGroup().size()-i-1);
    		Circle c = new Circle();
        	c.setRadius(alien.getRadius());
        	c.setFill(alien.getAlienColor());
        	c.setCenterX(alien.getPosx());
        	c.setCenterY(alien.getPosy());
        	alienCircles.add(c);
        	pane.getChildren().add(c);
    	}
    }
    
    @FXML
    public void shoot() {
    	Circle c = new Circle();
    	Shot shot = new Shot(player.getPosx(), c);
    	board.getShotGroup().add(shot);
    	c.setCenterX(shot.getPosx());
    	c.setCenterY(shot.getPosy());
    	c.setRadius(shot.getShotRadius());
    	c.setFill(shot.getShotColor());
    	pane.getChildren().add(c);
    }
    
    public void updatePosOfShots() {
    	for(int i=0; i < board.getShotGroup().size(); i++) {
	    	Shot shot = board.getShotGroup().get(i);
			shot.setPosy(shot.getPosy() - shotSpeed);
	    }
    }
    
    
    public void moveShots() {
    	for(int i=0; i < board.getShotGroup().size(); i++) {
    		Shot shot = board.getShotGroup().get(i);
    		Circle c = shot.getC();
    		TranslateTransition transition = new TranslateTransition();
			transition.setDuration(Duration.millis(cycleDuration));
			transition.setByY(-shotSpeed);
			transition.setNode(c);
			transition.play(); 
    	}
    }

}