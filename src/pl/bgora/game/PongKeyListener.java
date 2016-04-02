package pl.bgora.game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import org.apache.log4j.Logger;

public class PongKeyListener implements KeyListener {

	private static Logger LOGGER = Logger.getLogger(PongKeyListener.class);

	private MainFrame reference;

	public PongKeyListener(MainFrame mainFrame) {
		this.reference = mainFrame;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		Player leftPlayer = reference.getLeft();
		Player rightPlayer = reference.getRight();

		if (arg0.getKeyCode() == KeyEvent.VK_Q) {
			leftPlayer.setSpeed(-Math.abs(Player.DEFAULT_SPEED));

		}
		if (arg0.getKeyCode() == KeyEvent.VK_Z) {
			leftPlayer.setSpeed(Math.abs(Player.DEFAULT_SPEED));

		}
		if (arg0.getKeyCode() == KeyEvent.VK_UP) {
			rightPlayer.setSpeed(-Math.abs(Player.DEFAULT_SPEED));
		}
		if (arg0.getKeyCode() == KeyEvent.VK_DOWN) {
			rightPlayer.setSpeed(Math.abs(Player.DEFAULT_SPEED));
		}

		arg0.consume();
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		Player leftPlayer = reference.getLeft();
		Player rightPlayer = reference.getRight();

		if (arg0.getKeyCode() == KeyEvent.VK_Q) {
			leftPlayer.setSpeed(0);
		} else if (arg0.getKeyCode() == KeyEvent.VK_Z) {
			leftPlayer.setSpeed(0);
		}
		if (arg0.getKeyCode() == KeyEvent.VK_UP) {
			rightPlayer.setSpeed(0);
		} else if (arg0.getKeyCode() == KeyEvent.VK_DOWN) {
			rightPlayer.setSpeed(0);
		}
		if(arg0.getKeyCode() == KeyEvent.VK_PRINTSCREEN){
			
			try {
				Image screenshot = reference.getScreen();
				BufferedImage im = new BufferedImage(screenshot.getWidth(null), screenshot.getHeight(null), BufferedImage.TYPE_INT_RGB);
				im.getGraphics().drawImage(screenshot, 0, 0, null);
				ImageIO.write(im, "png", new File("screens\\screen.png"));
			} catch (FileNotFoundException e) {
				LOGGER.error(e,e);
			} catch (IOException e) {
				LOGGER.error(e,e);
			}
		}
		arg0.consume();
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

}
