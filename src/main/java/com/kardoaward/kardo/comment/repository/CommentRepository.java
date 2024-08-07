package com.kardoaward.kardo.comment.repository;

import com.kardoaward.kardo.comment.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
