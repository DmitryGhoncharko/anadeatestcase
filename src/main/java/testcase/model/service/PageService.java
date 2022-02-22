package testcase.model.service;

import testcase.entity.Page;
import testcase.exception.ServiceError;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface PageService {
    Optional<Page> createPage(String title, String description, String slug, String menuLabel, String h1, Date publishedAt, Integer priority, String content) throws ServiceError;

    Optional<Page> updatePage(String title, String description, String slug, String menuLabel, String h1, Date publishedAt, Integer priority, String content, Long pageId) throws ServiceError;

    List<Page> findAllPublishedPagesSortedByPriority() throws ServiceError;

    List<Page> findAllNotPublishedPages() throws ServiceError;

    boolean deletePageByPageSlug(String slug) throws ServiceError;

    Optional<Page> findPageByPageSlug(String slug) throws ServiceError;
}
