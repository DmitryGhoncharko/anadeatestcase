package testcase.model.dao;

import jdk.nashorn.internal.runtime.regexp.joni.encoding.ObjPtr;
import testcase.entity.Page;
import testcase.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface PageDao {
    Optional<Page> createPage(Page page) throws DaoException;

    Optional<Page> updatePage(Page page)throws DaoException;

    List<Page> findAllPublishedPagesSortedByPriority() throws DaoException;

    List<Page> findAllNotPublishedPages() throws DaoException;

    boolean deletePageByPageSlug(String slug)throws DaoException;

    Optional<Page> findPageByPageSlug(String slug) throws DaoException;
}
