package com.example.demo.repository;

import com.example.demo.entity.LoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<LoanEntity, Long> {

    boolean existsByBookIdAndReturnedAtIsNull(Long bookId);

    boolean existsByUserIdAndReturnedAtIsNull(Long userId);

    long countByUserIdAndReturnedAtIsNull(Long userId);

    List<LoanEntity> findByUserIdOrderByBorrowedAtDesc(Long userId);

    List<LoanEntity> findByBookIsbnOrderByBorrowedAtDesc(String isbn);

    List<LoanEntity> findByBookId(Long bookId);

    List<LoanEntity> findByUserId(Long userId);

}
