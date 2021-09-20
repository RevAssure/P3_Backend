package com.revature.RevAssure.Logging;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.FileAppender;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.PatternLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.util.logging.resources.logging;

public class Logging {

    final static Logger logger = LoggerFactory.getLogger(Logging.class);

    public static void main(String[] args) {

        ConsoleAppender debugger = new ConsoleAppender();
        debugger.setThreshold(Level.DEBUG);
        debugger.setLayout(new PatternLayout("%n%p -%d -%C{1} -%n"));
        debugger.activateOptions();

        FileAppender fileNote = new FileAppender();
        fileNote.setThreshold(Level.WARN);
        fileNote.setLayout(new PatternLayout("%n%p -%d -%C{1} -%n"));
        fileNote.setFile("src/main/logs/issues.txt");
        fileNote.setAppend(false);
        fileNote.activateOptions();

        logger.warn("potential issue");
        logger.debug("debugger");
    }
}
