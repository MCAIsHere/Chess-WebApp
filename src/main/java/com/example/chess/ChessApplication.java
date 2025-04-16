package com.example.chess;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.awt.Desktop;
import java.net.URI;

@SpringBootApplication
public class ChessApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChessApplication.class, args);
        openBrowser("http://localhost:8080/lobby");
    }

    private static void openBrowser(String url) {
        try {
            String command = "cmd.exe /c start firefox " + url;
            Runtime.getRuntime().exec(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
