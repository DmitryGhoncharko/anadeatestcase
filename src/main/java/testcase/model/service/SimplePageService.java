package testcase.model.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import testcase.entity.Page;
import testcase.exception.DaoException;
import testcase.exception.ServiceError;
import testcase.model.dao.PageDao;
import testcase.validator.PageServiceValidator;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public class SimplePageService implements PageService {
    private static final Logger LOG = LoggerFactory.getLogger(SimplePageService.class);
    private final PageDao pageDao;
    private final PageServiceValidator pageServiceValidator;

    public SimplePageService(PageDao pageDao, PageServiceValidator pageServiceValidator) {
        this.pageDao = pageDao;
        this.pageServiceValidator = pageServiceValidator;
    }

    @Override
    public Optional<Page> createPage(String title, String description, String slug, String menuLabel, String h1, Date publishedAt, Integer priority, String content) throws ServiceError {
        if (!pageServiceValidator.validateCreateOrUpdatePage(title, description, slug, menuLabel, h1, publishedAt, priority, content)) {
            return Optional.empty();
        }
        try {
            final Page page = createPageByParam(title, description, slug, menuLabel, h1, publishedAt, priority, content);
            return pageDao.createPage(page);
        } catch (DaoException e) {
            LOG.error("Dao exception in service when try create page", e);
            throw new ServiceError("Dao exception in service when try create page", e);
        }
    }

    @Override
    public Optional<Page> updatePage(String title, String description, String slug, String menuLabel, String h1, Date publishedAt, Integer priority, String content, Long pageId) throws ServiceError {
        if (!pageServiceValidator.validateCreateOrUpdatePage(title, description, slug, menuLabel, h1, publishedAt, priority, content, pageId)) {
            return Optional.empty();
        }
        try {
            final Page page = createPageByParam(title, description, slug, menuLabel, h1, publishedAt, priority, content, pageId);
            return pageDao.updatePage(page);
        } catch (DaoException e) {
            LOG.error("Dao exception in service when try update page", e);
            throw new ServiceError("Dao exception in service when try update page", e);
        }
    }

    @Override
    public List<Page> findAllPublishedPagesSortedByPriority() throws ServiceError {
        try {
            return pageDao.findAllPublishedPagesSortedByPriority();
        } catch (DaoException e) {
            LOG.error("Dao exception in service when try find all published pages sorted by priority", e);
            throw new ServiceError("Dao exception in service when try find all published pages sorted by priority", e);
        }
    }

    @Override
    public List<Page> findAllNotPublishedPages() throws ServiceError {
        try {
            return pageDao.findAllNotPublishedPages();
        } catch (DaoException e) {
            LOG.error("Dao exception in service when try find all not published pages", e);
            throw new ServiceError("Dao exception in service when try find all not published pages", e);
        }
    }

    @Override
    public boolean deletePageByPageSlug(String slug) throws ServiceError {
        if (slug == null) {
            return false;
        }
        try {
            return pageDao.deletePageByPageSlug(slug);
        } catch (DaoException e) {
            LOG.error("Dao exception in service when try delete page by slug", e);
            throw new ServiceError("Dao exception in service when try delete page by slug", e);
        }
    }

    @Override
    public Optional<Page> findPageByPageSlug(String slug) throws ServiceError {
        if (slug == null) {
            return Optional.empty();
        }
        try {
            return pageDao.findPageByPageSlug(slug);
        } catch (DaoException e) {
            LOG.error("Dao exception in service when try find page by page slug", e);
            throw new ServiceError("Dao exception in service when try find page by page slug", e);
        }
    }

    private Page createPageByParam(String title, String description, String slug, String menuLabel, String h1, Date publishedAt, Integer priority, String content) {
        return new Page.Builder().
                withTitle(title).
                withDescription(description).
                withSlug(slug).
                withMenuLabel(menuLabel).
                withH1(h1).
                withDatePublished(publishedAt).
                withPriority(priority).
                withContent(content).
                build();
    }

    private Page createPageByParam(String title, String description, String slug, String menuLabel, String h1, Date publishedAt, Integer priority, String content, Long pageId) {
        return new Page.Builder().
                withId(pageId).
                withTitle(title).
                withDescription(description).
                withSlug(slug).
                withMenuLabel(menuLabel).
                withH1(h1).
                withDatePublished(publishedAt).
                withPriority(priority).
                withContent(content).
                build();
    }
}
