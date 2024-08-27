package com.example.passwordvg;

import com.example.passwordvg.services.ManageFiles;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class PasswordVgApplication {

    @Value("${input.file.path}")
    private String inputFilePath;

    @Value("${output.file.path}")
    private String outputFilePath;

    public static void main(String[] args) {
        SpringApplication.run(PasswordVgApplication.class, args);
    }


    @Bean
    public CommandLineRunner createHashFile() {
        return args -> {
            ManageFiles manage = new ManageFiles();

            manage.createHashTextFile();

            List<String> hashedText = manage.readFileAndHashContent(inputFilePath);

            manage.writeToFile(hashedText, outputFilePath);

            System.out.println("finished hashing");
        };
    }
}
