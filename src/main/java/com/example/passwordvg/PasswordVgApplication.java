package com.example.passwordvg;

import com.example.passwordvg.services.ManageFiles;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class PasswordVgApplication {



    public static void main(String[] args) {
        SpringApplication.run(PasswordVgApplication.class, args);
    }


    @Bean
    public CommandLineRunner createHashFile() {
        return args -> createHashTextFile();
    }

    private void createHashTextFile(){
        ManageFiles manage = new ManageFiles();

        manage.createMD5andSHA256TextFiles();

        System.out.println("finished hashing");
    }
}
