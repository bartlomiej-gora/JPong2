package pl.bgora.game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Point;


public class Ball extends Canvas{
  
  private Point position;
  private Color color;
  private double speedx;
  private double speedy;
  
  public static final double DEFAULT_SPEED = 10;
  
  public static final int HEIGHT = 20;
  public static final int WIDTH = 20;
  
  public Ball(Color color, Point startPosition){
    this.setColor(color);
    this.setPosition(startPosition);
    setBounds(startPosition.x, startPosition.y, WIDTH, HEIGHT);
  }

  public synchronized void setPosition(Point position) {
    this.position = position;
    setBounds(position.x, position.y, WIDTH, HEIGHT);
  }

  public Point getPosition() {
    return position;
  }

  public synchronized void setColor(Color color) {
    this.color = color;
  }

  public Color getColor() {
    return color;
  }

public double getSpeedx() {
	return speedx;
}

public synchronized void setSpeedx(double speedx) {
	this.speedx = speedx;
}

public double getSpeedy() {
	return speedy;
}

public synchronized void setSpeedy(double speedy) {
	this.speedy = speedy;
}


  
  
}
