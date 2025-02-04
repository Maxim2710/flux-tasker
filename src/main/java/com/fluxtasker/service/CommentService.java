package com.fluxtasker.service;

import com.fluxtasker.exception.CommentCreationException;
import com.fluxtasker.model.Comment;
import com.fluxtasker.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public Mono<Comment> createComment(Long taskId, String text) {
        if (taskId == null || text == null || text.trim().isEmpty()) {
            return Mono.error(new CommentCreationException("Некорректные данные: taskId и text не могут быть пустыми"));
        }

        Comment comment = new Comment();
        comment.setTaskId(taskId);
        comment.setText(text);

        return commentRepository.save(comment)
                .onErrorMap(error -> new CommentCreationException("Ошибка при создании комментария"));
    }

    public Flux<Comment> getCommentsByTaskId(Long taskId) {
        return commentRepository.findByTaskId(taskId);
    }
}
