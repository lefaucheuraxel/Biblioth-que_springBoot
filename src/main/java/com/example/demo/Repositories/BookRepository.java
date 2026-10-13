package com.example.demo.Repositories;

import com.example.demo.Entity.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookRepository extends JpaRepository<Books, Long> {

    public  Books findByIsbn(String isbn);

}
