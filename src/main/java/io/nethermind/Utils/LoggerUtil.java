package io.nethermind.Utils;

import com.github.dzieciou.testing.curl.CurlHandler;
import com.github.dzieciou.testing.curl.Options;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @Author: jk
 * @Date: 19/01/22
 */

public class LoggerUtil implements CurlHandler {

  private static final String givenLogLevel = "DEBUG";
  private final Logger LOGGER;
  private final Level logLevel;

  public <T> LoggerUtil(Class<T> className) {
    LOGGER = LogManager.getLogger(className);
    if (givenLogLevel == null || givenLogLevel.isEmpty()) {
      logLevel = Level.INFO;
    } else {
      switch (givenLogLevel.toUpperCase()) {
        case "TRACE":
          logLevel = Level.TRACE;
          break;
        case "DEBUG":
          logLevel = Level.DEBUG;
          break;
        case "WARN":
          logLevel = Level.WARN;
          break;
        case "FATAL":
          logLevel = Level.FATAL;
          break;
        case "ERROR":
          logLevel = Level.ERROR;
          break;
        default:
          logLevel = Level.INFO;
      }
    }
  }

  @Override
  public void handle(String message, Options options) {
    switch (options.logLevel()) {
      case DEBUG:
        LOGGER.debug(message);
        break;
      case ERROR:
        LOGGER.error(message);
        break;
      case INFO:
        LOGGER.info(message);
        break;
      case TRACE:
        LOGGER.trace(message);
        break;
      case WARN:
        LOGGER.warn(message);
        break;
      default:
        throw new IllegalArgumentException("Unknown log level: " + options.logLevel());
    }
  }

  /**
   * Write a TRACE Log
   *
   * @param message
   */
  public void trace(String message) {
    LOGGER.trace(message);
  }

  /**
   * Write a DEBUG Log
   *
   * @param message
   */
  public void debug(String message) {
    if ((logLevel.equals(Level.TRACE)) || (logLevel.equals(Level.DEBUG))) {
      LOGGER.debug(message);
    }
  }

  /**
   * Write an INFO log
   *
   * @param message
   */
  public void info(String message) {
    if ((logLevel.equals(Level.TRACE)) || (logLevel.equals(Level.DEBUG)) || (logLevel.equals(
        Level.INFO))) {
      LOGGER.info(message);
    }
  }

  /**
   * Write an WARN log
   *
   * @param message
   */
  public void warn(String message) {
    if ((logLevel.equals(Level.TRACE)) || (logLevel.equals(Level.DEBUG)) || (logLevel.equals(
        Level.INFO))
        || (logLevel.equals(Level.WARN))) {
      LOGGER.warn(message);
    }
  }

  /**
   * Write an ERROR log
   *
   * @param message
   */
  public void error(String message) {
    if ((logLevel.equals(Level.TRACE)) || (logLevel.equals(Level.DEBUG)) || (logLevel.equals(
        Level.WARN))) {
      LOGGER.error(message);
    }
  }

  /**
   * Write an FAIL log
   *
   * @param message
   */
  public void fail(String message) {
    LOGGER.error(message);
  }

  /**
   * Write an ERROR log
   *
   * @param message
   */
  public void error(String message, Exception e) {
    LOGGER.error(message, e);

  }

  /**
   * Write a FATAL Log
   *
   * @param message
   */
  public void fatal(String message) {
    LOGGER.fatal(message);
  }
}
