package testcase.model.service;

import testcase.entity.Page;
import testcase.exception.DaoException;
import testcase.exception.ServiceException;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface PageService {
    Optional<Page> createPage(String title, String description, String slug, String menuLabel, String h1, Date publishedAt, Integer priority, String content) throws ServiceException;

    Optional<Page> updatePage(String title, String description, String slug, String menuLabel, String h1, Date publishedAt, Integer priority, String content, Integer ... pageId) throws ServiceException;

    List<Page> findAllPublishedPagesSortedByPriority() throws ServiceException;

    List<Page> findAllNotPublishedPages() throws ServiceException;

    boolean deletePageByPageSlug(String slug) throws ServiceException;

    Optional<Page> findPageByPageSlug(String slug) throws ServiceException;
}
