package com.uneev.task_management_system.entities;

import com.uneev.task_management_system.enums.Priority;
import com.uneev.task_management_system.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.util.List;

@Entity
@Data
@Table(name = "tasks")
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Enumerated
    @Column(name = "current_status")
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private Status status = Status.CREATED;

    @Enumerated
    @Column(name = "current_priority")
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private Priority priority;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    @ManyToOne
    @JoinColumn(name = "performer_id")
    private User performer;

    @OneToMany(mappedBy = "task")
    private List<Comment> comments;
}
