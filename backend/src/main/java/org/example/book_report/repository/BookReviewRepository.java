package org.example.book_report.repository;

import org.example.book_report.entity.BookReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookReviewRepository extends JpaRepository<BookReview, Long> {

    @Query("""
            SELECT br FROM BookReview br
            JOIN FETCH br.image i
            LEFT JOIN FETCH UserImage ui ON i.id = ui.image.id
            JOIN br.book b
            WHERE b.title LIKE %:bookTitle%
            """)
    Page<BookReview> getBookReviews(@Param("bookTitle") String bookTitle, Pageable pageable);

    @Query("""
            SELECT br FROM BookReview br
            JOIN FETCH br.image i
            LEFT JOIN FETCH i.userImage ui
            JOIN br.user u
            WHERE u.name =:username
            ORDER BY br.createdAt DESC
            """)
    Page<BookReview> getUserBookReviews(@Param("username") String username, Pageable pageable);
}
