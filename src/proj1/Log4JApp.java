package proj1;

import org.apache.log4j.*;

/**
 * Klasa Log4JApp zawiera metodę formatLogger, która konfiguruje logger.
 */
public class Log4JApp {
  /**
 * Metoda formatLogger konfiguruje logger z podanymi ustawieniami.
 * Ustawia poziom logowania na INFO.
 * Tworzy ConsoleAppender i ustawia kodowanie na "utf-8".
 * Ustawia układ wiadomości na "PatternLayout" z określonym formatem.
 * Aktywuje opcje konfiguracji dla ConsoleAppender.
 * Tworzy FileAppender i ustawia ścieżkę pliku logów na "logs/application.log".
 * Ustawia kodowanie na "windows-1250".
 * Ustawia układ wiadomości na "PatternLayout" z określonym formatem.
 * Aktywuje opcje konfiguracji dla FileAppender.
 * Dodaje ConsoleAppender i FileAppender do loggera.
 * Zwraca skonfigurowany logger.
 */
  public Logger formatLogger(Logger logger)
  {
    logger.setLevel(Level.INFO);
    ConsoleAppender consoleAppender = new ConsoleAppender();
    consoleAppender.setEncoding("utf-8");
    consoleAppender.setLayout(new PatternLayout("%-5p %d{yyyy-MM-dd HH:mm:ss} [%t] %c:%L - %m%n"));
    consoleAppender.activateOptions();
    FileAppender fileAppender = new FileAppender();
    fileAppender.setFile("logs/application.log");
    fileAppender.setEncoding("windows-1250");
    fileAppender.setLayout(new PatternLayout("%-5p %d{yyyy-MM-dd HH:mm:ss} [%t] %c:%L - %m%n"));
    fileAppender.activateOptions();

    logger.addAppender(consoleAppender);
    logger.addAppender(fileAppender);
    return logger;
  }
}

