package com.arrisdev.blogspringbootproject;

import net.bytebuddy.matcher.StringMatcher;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BlogSpringbootProjectApplication {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
    public static void main(String[] args) {
        SpringApplication.run(BlogSpringbootProjectApplication.class, args);
    }

}
