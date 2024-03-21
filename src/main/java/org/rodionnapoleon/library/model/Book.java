package org.rodionnapoleon.library.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;
import java.time.Year;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Book {

    @Id
    @GeneratedValue
    private Long id;
    private String author;
    private String bookName;
    private Year yearOfPublishing;
    private BigDecimal cost;
}
