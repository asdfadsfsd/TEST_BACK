package com.bit.boardappbackend.repository.custrom.impl;

import com.bit.boardappbackend.entity.Board;
import com.bit.boardappbackend.repository.BoardRepository;
import com.bit.boardappbackend.repository.custrom.BoardRepositoryCustom;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.bit.boardappbackend.entity.QBoard.board;

@RequiredArgsConstructor
public class BoardRepositoryCustomImpl implements BoardRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    @Override
    public Page<Board> searchAll(String searchCondition, String searchKeyword, Pageable pageable) {
        List<Board> boardList = queryFactory.selectFrom(board)
                .where(getSearch(searchCondition,searchKeyword))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.select(board.count())
                .from(board)
                .where(getSearch(searchCondition,searchKeyword))
                .fetchCount();

        return new PageImpl<>(boardList, pageable, total);
    }

    public BooleanBuilder getSearch(String searchCondition, String searchKeyword){
        BooleanBuilder builder = new BooleanBuilder();
        if(searchCondition == null || searchCondition.isEmpty()){
            return null;
        }
        if(searchCondition.equalsIgnoreCase("all")){
            builder.or(board.title.containsIgnoreCase(searchKeyword));
            builder.or(board.content.containsIgnoreCase(searchKeyword));
            builder.or(board.member.nickname.containsIgnoreCase(searchKeyword));
        }else if(searchCondition.equalsIgnoreCase("title")){
            builder.or(board.title.containsIgnoreCase(searchKeyword));
        }else if(searchCondition.equalsIgnoreCase("content")){
            builder.or(board.content.containsIgnoreCase(searchKeyword));
        }else if(searchCondition.equalsIgnoreCase("writer")){
            builder.or(board.member.nickname.containsIgnoreCase(searchKeyword));
        }

        return builder;
    }
}
