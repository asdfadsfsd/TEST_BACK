package com.bit.boardappbackend.repository.custrom;

import com.bit.boardappbackend.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardRepositoryCustom {
    Page<Board> searchAll(String searchCondition, String searchKeyword, Pageable pageable);
}
