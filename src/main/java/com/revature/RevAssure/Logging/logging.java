package com.revature.RevAssure.Logging;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.PatternLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class logging {

    final static Logger logger = LoggerFactory.getLogger(logging.class);

    public static void main(String[] args) {


        org.apache.log4j.FileAppender fileNote = new FileAppender();
        fileNote.setThreshold(Level.WARN);
        fileNote.setLayout(new PatternLayout("%n%p -%d -%C{1} -%n"));
        fileNote.setFile("src/main/logs/issues.txt");
        fileNote.setAppend(false);
        fileNote.activateOptions();


        org.apache.log4j.ConsoleAppender debugger = new ConsoleAppender();
        debugger.setThreshold(Level.DEBUG);
        debugger.setLayout(new org.apache.log4j.PatternLayout("%n%p -%d -%C{1} -%n"));
        debugger.activateOptions();

        org.apache.log4j.ConsoleAppender inform = new ConsoleAppender();
        inform.setThreshold(Level.INFO);
        inform.setLayout(new org.apache.log4j.PatternLayout("%n%p -%d -%C{1} -%n"));
        inform.activateOptions();

        logger.warn("potential issue");
        logger.debug("debugger");
        logger.info("information");
    }
}
