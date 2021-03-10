package Test;

import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;

public class GameController {

		@FXML
	    private Rectangle rectangle;
		private Player player = new Player(name);

	    @FXML
	    public void KeyPressed(KeyEvent event) {
	    	if (event.getCode() == KeyCode.LEFT) {
	    		player.moveLeft();
	  
	    	} else if (event.getCode() == KeyCode.RIGHT) {
	    		player.moveRight();
	    	} else if (event.getCode() == KeyCode.SPACE) {
	    		player.shoot();

	    }

	}

}
