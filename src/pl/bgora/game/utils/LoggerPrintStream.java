package pl.bgora.game.utils;

import java.io.OutputStream;
import java.io.PrintStream;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class LoggerPrintStream extends PrintStream {
	
	private Logger logger = Logger.getLogger(LoggerPrintStream.class);
	private Level level;
	
	public LoggerPrintStream(OutputStream out, Level level){
		super(out);
		this.level = level;
	}
	
	@Override
	public void print(String message) {
		logger.log(level, message);
		super.print(message);
	}

}
