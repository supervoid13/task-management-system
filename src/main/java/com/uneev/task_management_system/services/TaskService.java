package com.uneev.task_management_system.services;

import com.uneev.task_management_system.dto.*;
import com.uneev.task_management_system.entities.Task;
import com.uneev.task_management_system.entities.User;
import com.uneev.task_management_system.exceptions.TaskNotFoundException;
import com.uneev.task_management_system.exceptions.TaskPermissionDeniedException;
import com.uneev.task_management_system.repositories.TaskRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@RequiredArgsConstructor
@Validated
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;
    private final CommentService commentService;
    private final ModelMapper modelMapper;


    private List<Task> getAllOnPage(int page, int size) {
        return taskRepository.findAll(PageRequest.of(page - 1, size)).getContent();
    }

    public Task getById(Long id) {
        return taskRepository.findById(id).orElseThrow(
                () -> new TaskNotFoundException(String.format("Task with id '%d' not found", id))
        );
    }
    
    private List<Task> getAllByCreatorId(Long id, int page, int size) {
        return taskRepository.findAllByCreatorId(id, PageRequest.of(page - 1, size)).getContent();
    }

    private List<Task> getAllByPerformerId(Long id, int page, int size) {
        return taskRepository.findAllByPerformerId(id, PageRequest.of(page - 1, size)).getContent();
    }

    public List<Task> getAllByCreatorIdAndPerformerId(Long creatorId, Long performerId, int page, int size) {
        return taskRepository.findAllByCreatorIdAndPerformerId(
                creatorId,
                performerId,
                PageRequest.of(page - 1, size)
        ).getContent();
    }

    public void createTask(TaskCreationDto taskCreationDto, String email) {
        User creator = userService.getByEmail(email);

        Task task = modelMapper.map(taskCreationDto, Task.class);
        task.setCreator(creator);

        if (taskCreationDto.getPerformerEmail() != null) {
            String performerEmail = taskCreationDto.getPerformerEmail();
            task.setPerformer(userService.getByEmail(performerEmail));
        }

        taskRepository.save(task);
    }

    public void editTask(TaskEditingDto taskEditingDto, String email) {
        Task task = getById(taskEditingDto.getId());

        String creatorEmail = task.getCreator().getEmail();

        if (!creatorEmail.equals(email)) {
            throw new TaskPermissionDeniedException("You can't edit a task that you are not creator of");
        }

        task.setTitle(taskEditingDto.getTitle());
        task.setDescription(taskEditingDto.getDescription());

        taskRepository.save(task);
    }

    public void deleteById(Long id, String email) {
        Task task = getById(id);

        String creatorEmail = task.getCreator().getEmail();

        if (!creatorEmail.equals(email)) {
            throw new TaskPermissionDeniedException("You can't delete a task that you are not creator of");
        }

        taskRepository.delete(task);
    }

    public void changeStatus(ChangeStatusDto changeStatusDto, String email) {
        Task task = getById(changeStatusDto.getTaskId());

        User performer = task.getPerformer();

        if (performer == null || !performer.getEmail().equals(email)) {
            throw new TaskPermissionDeniedException("You are not task performer to change its status");
        }

        task.setStatus(changeStatusDto.getStatus());
        taskRepository.save(task);
    }

    public void assignPerformer(@Valid AssignPerformerDto assignPerformerDto, String creatorEmail) {
        Task task = getById(assignPerformerDto.getTaskId());

        User creator = task.getCreator();

        if (!creator.getEmail().equals(creatorEmail)) {
            throw new TaskPermissionDeniedException("You are not task creator to assign its performer");
        }

        User performer = userService.getByEmail(assignPerformerDto.getPerformerEmail());

        task.setPerformer(performer);
        taskRepository.save(task);
    }

    public void addComment(CommentCreationDto commentCreationDto, String email) {
        User user = userService.getByEmail(email);
        Task task = getById(commentCreationDto.getTaskId());

        commentService.createComment(commentCreationDto, task, user);
    }

    public List<TaskResponseDto> getTaskResponseDtoListOnPageByCreatorIdAndPerformerId(
            Long creatorId,
            Long performerId,
            int page,
            int size
    ){
        List<Task> tasks;

        if (creatorId == null && performerId == null) {
            tasks = getAllOnPage(page, size);
        } else if (creatorId == null) {
            tasks = getAllByPerformerId(performerId, page, size);
        } else {
            tasks = performerId == null
                    ? getAllByCreatorId(creatorId, page, size)
                    : getAllByCreatorIdAndPerformerId(creatorId, performerId, page, size);
        }

        return modelMapper.map(tasks, new TypeToken<List<TaskResponseDto>>(){}.getType());
    }

    public TaskResponseDto getTaskResponseDtoById(Long id) {
        Task task = getById(id);

        return modelMapper.map(task, TaskResponseDto.class);
    }
}
