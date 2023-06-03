package proj1;

import org.apache.log4j.*;

public class Log4JApp {
    
  public static void main(String[] args) {
    // Konfiguracja loggera
    Logger logger = Logger.getLogger(Log4JApp.class);
    logger.setLevel(Level.INFO);

    // Tworzenie konsolowego Appendera
    ConsoleAppender consoleAppender = new ConsoleAppender();
    consoleAppender.setEncoding("utf-8");
    consoleAppender.setLayout(new PatternLayout("%-5p %d{yyyy-MM-dd HH:mm:ss} [%t] %c:%L - %m%n"));
    consoleAppender.activateOptions();

    // Tworzenie plikowego Appendera
    FileAppender fileAppender = new FileAppender();
    fileAppender.setFile("logs/application.log");
    fileAppender.setEncoding("windows-1250");
    fileAppender.setLayout(new PatternLayout("%-5p %d{yyyy-MM-dd HH:mm:ss} [%t] %c:%L - %m%n"));
    fileAppender.activateOptions();

    // Dodawanie Appenderów do loggera
    logger.addAppender(consoleAppender);
    logger.addAppender(fileAppender);

    // Przykładowe logowanie
    logger.debug("To jest wiadomość debug");
    logger.info("To jest wiadomość info");
    logger.warn("To jest wiadomość ostrzeżenia");
    logger.error("To jest wiadomość błędu");
    logger.fatal("To jest wiadomość krytyczna");

    // Zamykanie loggera
    logger.removeAllAppenders();
  }
}

