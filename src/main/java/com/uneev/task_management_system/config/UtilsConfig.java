package com.uneev.task_management_system.config;

import com.uneev.task_management_system.dto.CommentCreationDto;
import com.uneev.task_management_system.dto.TaskEditingDto;
import com.uneev.task_management_system.dto.UserRegistrationDto;
import com.uneev.task_management_system.entities.Comment;
import com.uneev.task_management_system.entities.Task;
import com.uneev.task_management_system.entities.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class UtilsConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        TypeMap<UserRegistrationDto, User> userRegMapper = modelMapper.createTypeMap(
                UserRegistrationDto.class,
                User.class
        );
        userRegMapper.addMappings(mapper -> mapper.skip(User::setPassword));

        TypeMap<CommentCreationDto, Comment> commentCreationMapper = modelMapper.createTypeMap(
                CommentCreationDto.class,
                Comment.class
        );
        commentCreationMapper.addMappings(mapper -> mapper.skip(Comment::setId));

        return modelMapper;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
