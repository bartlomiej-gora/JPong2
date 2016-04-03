package pl.bgora.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;

import org.apache.log4j.Logger;

public class MainFrame extends JFrame {
  
  private static Logger LOGGER = Logger.getLogger(MainFrame.class);
  
  private int height;
  
  private int width;
  
  private int topLine;
  
  private int bottomLine;
  
  private int leftLine;
  
  private int rightLine;
  
  private Player left;
  
  private Player right;
  
  private Ball ball;
  
  private Image image;
  
  private Clip hitSound;
  
  private Clip pointSound;
  
  private Image screen;
  
  public MainFrame() {
    setBackground(Color.white);
    setLayout(null);
    
  }
  
  public void startGame() {
	initSounds();
    initObjects();
    initListeners();
    startThread();
  }
  
  private void initSounds() {
	  try {
		hitSound = AudioSystem.getClip();
		hitSound.open(AudioSystem.getAudioInputStream(new File("Music\\hit.wav")));
		pointSound =  AudioSystem.getClip();
		pointSound.open(AudioSystem.getAudioInputStream(new File("Music\\point.wav")));
	} catch (LineUnavailableException e) {
		LOGGER.error(e, e);
	} catch (IOException e) {
		LOGGER.error(e, e);
	} catch (UnsupportedAudioFileException e) {
		LOGGER.error(e, e);
	}
	  
	
}

private void startThread() {
    Runnable runner = new Runnable() {
      
      @Override
      public void run() {
        Random rand = new Random();
        int leftRight = rand.nextInt(2);
        if (leftRight == 0) {
          ball.setSpeedx(Ball.DEFAULT_SPEED);
        } else {
          ball.setSpeedx(-Ball.DEFAULT_SPEED);
        }
        while (left.getScore() < 5 && right.getScore() < 5) {
        	//1.
            // move objects
        	//
          move();
          
          //2.
          // Check collisions
          // colissions for players vs. court
          checkCollision(rand);
          
          //3. Paint!
          //paint;
          myPaint(getGraphics());
          try {
            Thread.sleep(0,10);
          } catch (InterruptedException e) {
            LOGGER.error("InterruptedException", e);
          }
        }
        
      }

      private void checkCollision(Random rand) {
        int leftRight;
        if (left.getSpeed() > 0) {
          if (left.getPosition().y >= bottomLine - Player.HEIGHT) {
            left.setPosition(new Point(left.getPosition().x, bottomLine - Player.HEIGHT));
            left.setSpeed(0);

          }
        }
        else if (left.getPosition().y <= topLine) {
          left.setPosition(new Point(left.getPosition().x, topLine));
          left.setSpeed(0);
        }

        // right
        if (right.getSpeed() > 0) {
          if (right.getPosition().y >= bottomLine - Player.HEIGHT) {
              right.setPosition(new Point(right.getPosition().x, bottomLine - Player.HEIGHT));
            right.setSpeed(0);
          }
        } else if (right.getPosition().y <= topLine) {
          right.setSpeed(0);
          right.setPosition(new Point(right.getPosition().x, topLine));
        }

        // check collision for ball vs. wall
        // bottomLine
        if (ball.getBounds().intersectsLine(leftLine, bottomLine, rightLine, bottomLine)) {
          ball.setSpeedy(-Math.abs(ball.getSpeedy()));
        }
        // topLine
        if (ball.getBounds().intersectsLine(leftLine, topLine, rightLine, topLine)) {
          ball.setSpeedy(Math.abs(ball.getSpeedy()));
        }

        // leftLine - score for right player
        if (ball.getBounds().intersectsLine(leftLine, bottomLine, leftLine, topLine)) {
          right.setScore(right.getScore() + 1);
          pointSound.stop();
          pointSound.setFramePosition(0);
          pointSound.start();
          // reset ball
          ball.setPosition(getCenter());
          leftRight = rand.nextInt(2);
          if (leftRight == 0) {
            ball.setSpeedx(Ball.DEFAULT_SPEED);
          } else {
            ball.setSpeedx(-Ball.DEFAULT_SPEED);
          }
        }
        // rightLine - score for left player.
        if (ball.getBounds().intersectsLine(rightLine, bottomLine, rightLine, topLine)) {
          left.setScore(left.getScore() + 1);
          pointSound.stop();
          pointSound.setFramePosition(0);
          pointSound.start();
          // reset ball
          ball.setPosition(getCenter());
          leftRight = rand.nextInt(2);
          if (leftRight == 0) {
            ball.setSpeedx(Ball.DEFAULT_SPEED);
            ball.setSpeedy(0);
          } else {
            ball.setSpeedx(-Ball.DEFAULT_SPEED);
            ball.setSpeedy(0);
          }
        }

        // collisions with players.
        if (ball.getBounds().intersects(left.getBounds())) {
          ball.setSpeedx(Math.abs(ball.getSpeedx()));
          hitSound.stop();
          hitSound.setFramePosition(0);
          hitSound.start();
          if (left.getSpeed() > 0) {
            ball.setSpeedy(Math.abs(Ball.DEFAULT_SPEED));
          } else if (left.getSpeed() < 0) {
            ball.setSpeedy(-Math.abs(Ball.DEFAULT_SPEED));
          }
        }

        if (ball.getBounds().intersects(right.getBounds())) {
          ball.setSpeedx(-Math.abs(ball.getSpeedx()));
          hitSound.stop();
          hitSound.setFramePosition(0);
          hitSound.start();
          if (right.getSpeed() > 0) {
            ball.setSpeedy(Math.abs(Ball.DEFAULT_SPEED));
          } else if (right.getSpeed() < 0) {
            ball.setSpeedy(-Math.abs(Ball.DEFAULT_SPEED));
          }
        }
      }

      private void move() {
        Point newBallPoint = new Point();
        newBallPoint.setLocation(ball.getPosition().getX() + ball.getSpeedx(),ball.getPosition().getY() + ball.getSpeedy() );
        ball.setPosition(newBallPoint);
        Point newLeftPoint = new Point();
        newLeftPoint.setLocation(left.getPosition().getX(), left.getPosition().getY() + left.getSpeed());
        left.setPosition(newLeftPoint);
        Point newRightPoint = new Point();
        newRightPoint.setLocation(right.getPosition().getX(), right.getPosition().getY() + right.getSpeed());
        right.setPosition(newRightPoint);
      }
    };
    
    Thread t = new Thread(runner);
    t.start();
    
  }
  
  private void initListeners() {
    addKeyListener(new PongKeyListener(this));
  }
  
  private void initObjects() {
    height = getHeight();
    width = getWidth();
    bottomLine = (int) (height - (height * 0.05));
    topLine = (int) (height * 0.05);
    leftLine = (int) (width * 0.05);
    rightLine = (int) (width - (width * 0.05));

    this.left = new Player(Color.BLUE, getLeftStartPosition());
    this.right = new Player(Color.RED, getRightStartPosition());
    this.ball = new Ball(Color.GREEN, getCenter());
    
  }
  
//  @Override
  public void myPaint(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    
    Graphics offGraph = null;
    image = createImage(width, height);
    offGraph = image.getGraphics();
    offGraph.setColor(left.getColor());
    offGraph.fillRect(left.getPosition().x, left.getPosition().y, Player.WIDTH, Player.HEIGHT);
    
    offGraph.setColor(right.getColor());
    offGraph.fillRect(right.getPosition().x, right.getPosition().y, Player.WIDTH, Player.HEIGHT);
    
    offGraph.setColor(ball.getColor());
    offGraph.fillOval(ball.getPosition().x, ball.getPosition().y, Ball.WIDTH, Ball.HEIGHT);
    
    // draw the lines for the "court"
    offGraph.setColor(Color.black);
    offGraph.drawLine(leftLine, bottomLine, rightLine, bottomLine);
    offGraph.drawLine(leftLine, bottomLine, leftLine, topLine);
    offGraph.drawLine(leftLine, topLine, rightLine, topLine);
    offGraph.drawLine(rightLine, topLine, rightLine, bottomLine);
    
    int y = (int) (topLine - topLine * 0.05);
    int x = (int) rightLine / 2;
    offGraph.setColor(Color.black);
    Font font = new Font("Arial", Font.BOLD, 16);
    offGraph.setFont(font);
    offGraph.drawString(left.getScore() + " : " + right.getScore(), x, y);
    screen = createImage(width, height);
    screen.getGraphics().drawImage(image, 0, 0, null);
    g.drawImage(image, 0, 0, null);
    
  }
  
  private Point getLeftStartPosition() {
    return new Point(leftLine + (Player.WIDTH), bottomLine / 2);
    
  }
  
  private Point getRightStartPosition() {
    return new Point(rightLine - (Player.WIDTH * 2), bottomLine / 2);
  }
  
  private Point getCenter() {
    return new Point(rightLine / 2, bottomLine / 2);
  }
  
  public Player getLeft() {
    return left;
  }
  
  public void setLeft(Player left) {
    this.left = left;
  }
  
  public Player getRight() {
    return right;
  }
  
  public void setRight(Player right) {
    this.right = right;
  }
  
  public int getTopLine() {
    return topLine;
  }
  
  public void setTopLine(int topLine) {
    this.topLine = topLine;
  }
  
  public int getBottomLine() {
    return bottomLine;
  }
  
  public void setBottomLine(int bottomLine) {
    this.bottomLine = bottomLine;
  }
  
  public int getLeftLine() {
    return leftLine;
  }
  
  public void setLeftLine(int leftLine) {
    this.leftLine = leftLine;
  }
  
  public int getRightLine() {
    return rightLine;
  }
  
  public void setRightLine(int rightLine) {
    this.rightLine = rightLine;
  }

public Image getScreen() {
	return screen;
}

public void setScreen(Image screen) {
	this.screen = screen;
}
}
