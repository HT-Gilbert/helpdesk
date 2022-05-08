package com.help.entity.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface BoardReplyRepository  extends JpaRepository<Board, Long> {
    //Flux<BoardReply> findAllByDocumentDisplayId(long documentDisplayId);
    //Mono<Long> countAllByDocumentDisplayId(long documentDisplayId);
    //Flux<BoardReply> findAllByAuthorUsernameOrderByCreatedAtDesc(String authorUsername);
    //Mono<Long> countAllByAuthorUsername(String authorUsername);
    //Mono<Void> removeAllByDocumentDisplayId(long documentDisplayId);
}