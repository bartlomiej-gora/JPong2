package pl.bgora.game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Point;

public class Player extends Canvas {
  
  private Color color;
  
  public static final int HEIGHT = 200;
  
  public static final int WIDTH = 20;
  
  private Point position;
  
  private double speed;
  
  public static final double DEFAULT_SPEED = 10;
  
  private int score;
  
  public Player(Color color, Point startPosition) {
    this.setColor(color);
    this.position = startPosition;
    setBounds(position.x, position.y, WIDTH, HEIGHT);
    score = 0;
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
  
  public synchronized Color getColor() {
    return color;
  }
  
  public synchronized void setScore(int score) {
    this.score = score;
  }
  
  public synchronized int getScore() {
    return score;
  }
  
  public double getSpeed() {
    return speed;
  }
  
  public synchronized void setSpeed(double speed) {
    this.speed = speed;
  }
  
  
}
