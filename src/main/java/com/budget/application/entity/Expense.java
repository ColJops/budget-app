package com.budget.application.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
//@Table(name = "expenses")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDateTime creationDate;

    @Column()
    private Double value;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "expenses_tags",
    joinColumns = { @JoinColumn(name = "expense_id", referencedColumnName = "id",
        nullable = false, unique = false )}, inverseJoinColumns = {
            @JoinColumn(name = "tag_id", referencedColumnName = "id",
            nullable = false, unique = false )}
    )
    private List<Tag> tags = new ArrayList<>();
}
