package com.example.chess;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChessApplication {
    public static void main(String[] args){
        SpringApplication.run(ChessApplication.class, args);
        try {
            String command = "cmd.exe /c start firefox http://localhost:8080/lobby";
            Runtime.getRuntime().exec(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
