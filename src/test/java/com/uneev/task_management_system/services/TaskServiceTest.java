package com.uneev.task_management_system.services;

import com.uneev.task_management_system.dto.CommentResponseDto;
import com.uneev.task_management_system.dto.TaskResponseDto;
import com.uneev.task_management_system.dto.UserInfoDto;
import com.uneev.task_management_system.entities.Comment;
import com.uneev.task_management_system.entities.Task;
import com.uneev.task_management_system.entities.User;
import com.uneev.task_management_system.enums.Priority;
import com.uneev.task_management_system.enums.Status;
import com.uneev.task_management_system.repositories.TaskRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private TaskService taskService;

    private User creator;
    private User performer;
    private User commentator;
    private List<Comment> comments;
    private Task task;


    @BeforeEach
    public void setup() {
        creator = new User(
                1L,
                "creator@gmail.com",
                "qwerty123",
                "Creator first name",
                "Creator second name",
                null,
                null
        );

        performer = new User(
                2L,
                "performer@gmail.com",
                "qwerty123",
                "Performer first name",
                "Performer second name",
                null,
                null
        );

        commentator = new User(
                3L,
                "commentator@gmail.com",
                "qwerty123",
                "Commentator first name",
                "Commentator second name",
                null,
                null
        );

        comments = List.of(new Comment(
                1L,
                "Comment content",
                LocalDateTime.of(2023, 8, 30, 12, 30),
                null,
                commentator
        ));

        task = new Task(
                1L,
                "Task 1 title",
                "Task 1 description",
                Status.PROCESSING,
                Priority.HIGH,
                creator,
                performer,
                comments
        );
    }

    @Test
    public void shouldReturnEmptyList() {
        int page = 1, size = 5;
        Long creatorId = 1L, performerId = 2L;

        PageRequest pageRequest = PageRequest.of(page, size);

        Mockito.when(taskRepository.findAll(ArgumentMatchers.isA(Pageable.class))).thenReturn(Page.empty());

        Mockito.when(taskRepository.findAllByCreatorId(ArgumentMatchers.anyLong(), ArgumentMatchers.isA(Pageable.class)))
                .thenReturn(Page.empty());

        Mockito.when(taskRepository.findAllByPerformerId(ArgumentMatchers.anyLong(), ArgumentMatchers.isA(Pageable.class)))
                .thenReturn(Page.empty());

        Mockito.when(taskRepository.findAllByCreatorIdAndPerformerId(
                ArgumentMatchers.anyLong(),
                ArgumentMatchers.anyLong(),
                ArgumentMatchers.isA(Pageable.class)
                ))
                .thenReturn(Page.empty());

        Page<TaskResponseDto> allTasks = taskService
                .getTasksOnPageByCreatorIdAndPerformerId(null, null, page, size);

        Page<TaskResponseDto> tasksByCreatorId = taskService
                .getTasksOnPageByCreatorIdAndPerformerId(creatorId, null, page, size);

        Page<TaskResponseDto> tasksByPerformerId = taskService
                .getTasksOnPageByCreatorIdAndPerformerId(null, performerId, page, size);

        Page<TaskResponseDto> tasksByCreatorIdAndPerformerId = taskService
                .getTasksOnPageByCreatorIdAndPerformerId(creatorId, performerId, page, size);

        Assertions.assertTrue(allTasks.isEmpty());
        Assertions.assertTrue(tasksByCreatorId.isEmpty());
        Assertions.assertTrue(tasksByPerformerId.isEmpty());
        Assertions.assertTrue(tasksByCreatorIdAndPerformerId.isEmpty());
    }

    @Test
    public void shouldReturnTaskById() {
        Mockito.when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        TaskResponseDto dto = new TaskResponseDto(
                1L,
                "Task 1 title",
                "Task 1 description",
                Status.PROCESSING,
                Priority.HIGH,
                new UserInfoDto(
                        "creator@gmail.com",
                        "Creator first name",
                        "Creator second name"
                ),
                new UserInfoDto(
                        "performer@gmail.com",
                        "Performer first name",
                        "Performer second name"
                ),
                List.of(new CommentResponseDto(
                        "Comment content",
                        LocalDateTime.of(2023, 8, 30, 12, 30),
                        new UserInfoDto(
                                "commentator@gmail.com",
                                "Commentator first name",
                                "Commentator second name"
                        )
                ))
        );
        TaskResponseDto actualDto = taskService.getTaskResponseDtoById(1L);
        Assertions.assertEquals(dto, actualDto);
    }
}
