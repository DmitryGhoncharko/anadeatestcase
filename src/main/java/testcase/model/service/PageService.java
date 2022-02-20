package testcase.model.service;

import testcase.entity.Page;
import testcase.exception.DaoException;
import testcase.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface PageService {
    Optional<Page> createPage(Page page) throws ServiceException;

    Optional<Page> updatePage(Page page) throws ServiceException;

    List<Page> findAllPublishedPagesSortedByPriority() throws ServiceException;

    List<Page> findAllNotPublishedPages() throws ServiceException;

    boolean deletePageByPageSlug(String slug) throws ServiceException;

    Optional<Page> findPageByPageSlug(String slug) throws ServiceException;
}
