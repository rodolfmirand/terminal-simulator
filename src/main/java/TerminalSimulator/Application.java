package TerminalSimulator;

import TerminalSimulator.repository.Database;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static Database database;

    public static void main(String[] args) {
        database = new Database();
        SpringApplication.run(Application.class, args);
    }

}