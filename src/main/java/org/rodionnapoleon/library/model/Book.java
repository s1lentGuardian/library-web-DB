package org.rodionnapoleon.library.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Year;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "author")
    private String author;
    @Column(name = "book_name")
    private String bookName;
    @Column(name = "year_of_publishing")
    private Year yearOfPublishing;
    @Column(name = "cost")
    private BigDecimal cost;
}
