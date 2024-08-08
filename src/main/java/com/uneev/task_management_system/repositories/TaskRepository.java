package com.uneev.task_management_system.repositories;

import com.uneev.task_management_system.entities.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findAllByCreatorId(Long id, Pageable pageable);
    Page<Task> findAllByPerformerId(Long id, Pageable pageable);
    Page<Task> findAllByCreatorIdAndPerformerId(Long creatorId, Long performerId, Pageable pageable);
}
