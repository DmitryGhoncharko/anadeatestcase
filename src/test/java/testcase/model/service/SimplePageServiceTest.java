package testcase.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import testcase.entity.Page;
import testcase.exception.DaoException;
import testcase.model.dao.PageDao;
import testcase.validator.PageServiceValidator;

import java.sql.Date;
import java.util.*;


public class SimplePageServiceTest {
    private final Date date = (Date) new GregorianCalendar().getTime();
    private PageDao pageDao;
    private final PageService pageService;
    private PageServiceValidator pageServiceValidator;
    private final Page page = new Page.Builder().
            withId(1).
            withTitle("title").
            withDescription("description").
            withSlug("slug").
            withMenuLabel("menuLabel").
            withH1("h1").
            withDatePublished(date).
            withPriority(2).
            withContent("content").
            build();

    public SimplePageServiceTest() {
        pageService = new SimplePageService(pageDao, pageServiceValidator);
    }

    @Test
    public void createPageTest() throws DaoException {
        BDDMockito.given(pageDao.createPage(page)).willReturn(Optional.of(page));
        BDDMockito.given(pageServiceValidator.validateCreateOrUpdatePage(page.getTitle(), page.getDescription(), page.getSlug(), page.getMenuLabel(), page.getH1(), page.getPublishedAt(), page.getPriority(), page.getContent())).willReturn(true);
        final Optional<Page> createdPage = pageService.createPage(page.getTitle(), page.getDescription(), page.getSlug(), page.getMenuLabel(), page.getH1(), page.getPublishedAt(), page.getPriority(), page.getContent());
        createdPage.ifPresent(value -> Assertions.assertEquals(value, page));
    }
    @Test
    public void updatePageByIdTest() throws DaoException {
        BDDMockito.given(pageDao.updatePageById(page)).willReturn(Optional.of(page));
        BDDMockito.given(pageServiceValidator.validateCreateOrUpdatePage(page.getTitle(), page.getDescription(), page.getSlug(), page.getMenuLabel(), page.getH1(), page.getPublishedAt(), page.getPriority(), page.getContent())).willReturn(true);
        final Optional<Page> updatedPage = pageService.updatePageById(page.getTitle(), page.getDescription(), page.getSlug(), page.getMenuLabel(), page.getH1(), page.getPublishedAt(), page.getPriority(), page.getContent(), page.getId());
        updatedPage.ifPresent(value -> Assertions.assertEquals(value, page));
    }
    @Test
    public void findAllPublishedPagesSortedByPriorityTest() throws DaoException {
        BDDMockito.given(pageDao.findAllPublishedPagesSortedByPriority()).willReturn(Collections.singletonList(page));
        final List<Page> findedPagesList = pageService.findAllPublishedPagesSortedByPriority();
        final Page singlePage = findedPagesList.get(0);
        Assertions.assertEquals(singlePage,page);
    }
    @Test
    public void findAllNotPublishedPagesTest() throws DaoException {
        BDDMockito.given(pageDao.findAllNotPublishedPages()).willReturn(Collections.singletonList(page));
        final List<Page> findedPagesList = pageService.findAllNotPublishedPages();
        final Page singlePage = findedPagesList.get(0);
        Assertions.assertEquals(singlePage,page);
    }
    @Test
    public void deletePageByPageSlugTest() throws DaoException {
        BDDMockito.given(pageDao.deletePageByPageSlug(page.getSlug())).willReturn(true);
        final boolean pageIsDeleted = pageService.deletePageByPageSlug(page.getSlug());
        Assertions.assertTrue(pageIsDeleted);
    }
    @Test
    public void findPageByPageSlugTest() throws DaoException {
        BDDMockito.given(pageDao.findPageByPageSlug(page.getSlug())).willReturn(Optional.of(page));
        final Optional<Page> findedPage = pageService.findPageByPageSlug(page.getSlug());
        findedPage.ifPresent(value -> Assertions.assertEquals(value, page));
    }
}
