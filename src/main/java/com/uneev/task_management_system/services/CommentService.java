package com.uneev.task_management_system.services;

import com.uneev.task_management_system.dto.CommentCreationDto;
import com.uneev.task_management_system.entities.Comment;
import com.uneev.task_management_system.entities.Task;
import com.uneev.task_management_system.entities.User;
import com.uneev.task_management_system.repositories.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;


    public void createComment(CommentCreationDto commentCreationDto, Task task, User user) {
        Comment comment = modelMapper.map(commentCreationDto, Comment.class);

        comment.setTask(task);
        comment.setUser(user);

        commentRepository.save(comment);
    }
}
