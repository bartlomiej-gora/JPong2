package pl.bgora.game;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.PrintStream;

import javax.swing.SwingUtilities;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import pl.bgora.game.utils.LoggerPrintStream;



public class MainGame {
  
  private static Logger LOGGER = Logger.getLogger(MainGame.class);
  /**
   * @param args
   */
  public static void main(String[] args) {
    
    DOMConfigurator.configure("log4j.xml");
    System.setOut(createLogStream(System.out, Level.ALL));
    System.setErr(createLogStream(System.err, Level.ERROR));
    SwingUtilities.invokeLater(new Runnable() {
      
      @Override
      public void run() {
        MainFrame mainframe = new MainFrame();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice myDevice = ge.getDefaultScreenDevice();
        if(myDevice.isFullScreenSupported()){
          myDevice.setFullScreenWindow(mainframe);
          mainframe.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
              if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
            	  System.exit(0);
              }
            }
          });
        }else{
          return;
        }
        mainframe.startGame();
      }
    });

  }
  
  
  private static PrintStream createLogStream(PrintStream realPrintStream, Level level) {
    return new LoggerPrintStream(realPrintStream, level);
}
}
