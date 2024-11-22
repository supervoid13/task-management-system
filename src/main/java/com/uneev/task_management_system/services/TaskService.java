package com.uneev.task_management_system.services;

import com.uneev.task_management_system.dto.AssignPerformerDto;
import com.uneev.task_management_system.dto.ChangeStatusDto;
import com.uneev.task_management_system.dto.CommentCreationDto;
import com.uneev.task_management_system.dto.TaskCreationDto;
import com.uneev.task_management_system.dto.TaskEditingDto;
import com.uneev.task_management_system.dto.TaskResponseDto;
import com.uneev.task_management_system.entities.Task;
import com.uneev.task_management_system.entities.User;
import com.uneev.task_management_system.exceptions.TaskNotFoundException;
import com.uneev.task_management_system.exceptions.TaskPermissionDeniedException;
import com.uneev.task_management_system.repositories.TaskRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * Service for task management.
 */
@Service
@RequiredArgsConstructor
@Validated
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;
    private final CommentService commentService;
    private final ModelMapper modelMapper;


    private Page<Task> getAllOnPage(int page, int size) {
        return taskRepository.findAll(PageRequest.of(page - 1, size));
    }

    private Task getById(Long id) {
        return taskRepository.findById(id).orElseThrow(
                () -> new TaskNotFoundException(String.format("Task with id '%d' not found", id))
        );
    }
    
    private Page<Task> getAllByCreatorId(Long id, int page, int size) {
        return taskRepository.findAllByCreatorId(id, PageRequest.of(page - 1, size));
    }

    private Page<Task> getAllByPerformerId(Long id, int page, int size) {
        return taskRepository.findAllByPerformerId(id, PageRequest.of(page - 1, size));
    }

    private Page<Task> getAllByCreatorIdAndPerformerId(Long creatorId, Long performerId, int page, int size) {
        return taskRepository.findAllByCreatorIdAndPerformerId(
                creatorId,
                performerId,
                PageRequest.of(page - 1, size)
        );
    }

    /**
     * Creating new task.
     * @param taskCreationDto new task data.
     * @param email task creator email.
     */
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
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

    /**
     * Editing existing task.
     * @param taskEditingDto existing task info.
     */
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void editTask(TaskEditingDto taskEditingDto) {
        Task task = getById(taskEditingDto.getId());

        task.setTitle(taskEditingDto.getTitle());
        task.setDescription(taskEditingDto.getDescription());

        taskRepository.save(task);
    }

    /**
     * Deleting task.
     * @param id id of the task to delete.
     */
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }

    /**
     * Changing task status.
     * @param changeStatusDto data for changing task status.
     * @param email email of the requesting user.
     */
    @Transactional
    public void changeStatus(ChangeStatusDto changeStatusDto, String email) {
        Task task = getById(changeStatusDto.getTaskId());

        User performer = task.getPerformer();

        UserDetails requester = userService.loadUserByUsername(email);
        boolean isAdmin = requester.getAuthorities().stream()
                .anyMatch(user -> "ROLE_ADMIN".equals(user.getAuthority()));

        if (!isAdmin) {
            if (performer == null || !performer.getEmail().equals(email)) {
                throw new TaskPermissionDeniedException("You are not task performer to change its status");
            }
        }

        task.setStatus(changeStatusDto.getStatus());
        taskRepository.save(task);
    }

    /**
     * Assigning performer for the task.
     * @param assignPerformerDto data for assigning performer.
     * @param email email of the requesting user.
     */
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void assignPerformer(@Valid AssignPerformerDto assignPerformerDto, String email) {
        Task task = getById(assignPerformerDto.getTaskId());

        User creator = task.getCreator();

        if (!creator.getEmail().equals(email)) {
            throw new TaskPermissionDeniedException("You are not task creator to assign its performer");
        }

        User performer = userService.getByEmail(assignPerformerDto.getPerformerEmail());

        task.setPerformer(performer);
        taskRepository.save(task);
    }

    /**
     * Adding comment to the task.
     * @param commentCreationDto data for adding comment.
     * @param email email of the requesting user.
     */
    @Transactional
    public void addComment(CommentCreationDto commentCreationDto, String email) {
        Task task = getById(commentCreationDto.getTaskId());

        User performer = task.getPerformer();

        UserDetails requesterDetails = userService.loadUserByUsername(email);
        boolean isAdmin = requesterDetails.getAuthorities().stream()
                .anyMatch(details -> "ROLE_ADMIN".equals(details.getAuthority()));

        if (!isAdmin) {
            if (performer == null || !performer.getEmail().equals(email)) {
                throw new TaskPermissionDeniedException("You are not task performer to add a comment");
            }
        }

        commentService.createComment(commentCreationDto, task, performer);
    }

    /**
     * Retrieving Page of tasks by filters.
     * @param creatorId creator id.
     * @param performerId performer id.
     * @param page requested page.
     * @param size amount of elements on page.
     * @return Page with metainfo and tasks.
     */
    public Page<TaskResponseDto> getTasksOnPageByCreatorIdAndPerformerId(
            Long creatorId,
            Long performerId,
            int page,
            int size
    ){
        Page<Task> tasks;

        if (creatorId == null && performerId == null) {
            tasks = getAllOnPage(page, size);
        } else if (creatorId == null) {
            tasks = getAllByPerformerId(performerId, page, size);
        } else {
            tasks = performerId == null
                    ? getAllByCreatorId(creatorId, page, size)
                    : getAllByCreatorIdAndPerformerId(creatorId, performerId, page, size);
        }
        return tasks.map(task -> modelMapper.map(task, TaskResponseDto.class));
    }

    /**
     * Retrieving task by id.
     * @param id task id.
     * @return task data.
     */
    public TaskResponseDto getTaskResponseDtoById(Long id) {
        Task task = getById(id);

        return modelMapper.map(task, TaskResponseDto.class);
    }
}
