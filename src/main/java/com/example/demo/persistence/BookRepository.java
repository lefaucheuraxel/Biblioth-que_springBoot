package com.example.demo.persistence;

import com.example.demo.model.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {

    public  BookEntity findByIsbn(String isbn);

}
