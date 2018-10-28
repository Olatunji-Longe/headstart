package com.quadbaze.headstart.services;

import com.quadbaze.headstart.domain.enums.QueryField;
import com.quadbaze.headstart.domain.models.Book;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author: Olatunji O. Longe
 * @created: 18 Oct, 2018, 6:18 AM
 */
public interface BookService {
    Page<Book> findBooks(String query, QueryField queryField, Integer page);

    List<Integer> getPageNumbers(Page<Book> bookPage);
}
