package testcase.model.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import testcase.entity.Page;
import testcase.exception.DaoException;
import testcase.exception.ServiceException;
import testcase.model.dao.PageDao;

import java.util.List;
import java.util.Optional;

public class SimplePageService implements PageService{
    private static final Logger LOG = LoggerFactory.getLogger(SimplePageService.class);
    private final PageDao pageDao;

    public SimplePageService(PageDao pageDao) {
        this.pageDao = pageDao;
    }

    @Override
    public Optional<Page> createPage(Page page) throws ServiceException {
        if(page==null){
            return Optional.empty();
        }
        try{
            return pageDao.createPage(page);
        }catch (DaoException e){
            LOG.error("Dao exception in service when try create page",e);
            throw new ServiceException("Dao exception in service when try create page",e);
        }
    }

    @Override
    public Optional<Page> updatePage(Page page) throws ServiceException {
        if(page == null){
            return Optional.empty();
        }
        try{
            return pageDao.updatePage(page);
        }catch (DaoException e){
            LOG.error("Dao exception in service when try update page",e);
            throw new ServiceException("Dao exception in service when try update page",e);
        }
    }

    @Override
    public List<Page> findAllPublishedPagesSortedByPriority() throws ServiceException {
        try{
            return pageDao.findAllPublishedPagesSortedByPriority();
        }catch (DaoException e){
            LOG.error("Dao exception in service when try find all published pages sorted by priority",e);
            throw new ServiceException("Dao exception in service when try find all published pages sorted by priority",e);
        }
    }

    @Override
    public List<Page> findAllNotPublishedPages() throws ServiceException {
        try{
          return pageDao.findAllNotPublishedPages();
        }catch (DaoException e){
            LOG.error("Dao exception in service when try find all not published pages",e);
            throw new ServiceException("Dao exception in service when try find all not published pages",e);
        }
    }

    @Override
    public boolean deletePageByPageSlug(String slug) throws ServiceException {
       if(slug==null){
           return false;
       }
       try{
         return pageDao.deletePageByPageSlug(slug);
       }catch (DaoException e){
           LOG.error("Dao exception in service when try delete page by slug",e);
           throw new ServiceException("Dao exception in service when try delete page by slug",e);
       }
    }

    @Override
    public Optional<Page> findPageByPageSlug(String slug) throws ServiceException {
        if(slug==null){
            return Optional.empty();
        }
        try{
           return pageDao.findPageByPageSlug(slug);
        }catch (DaoException e){
            LOG.error("Dao exception in service when try find page by page slug",e);
            throw new ServiceException("Dao exception in service when try find page by page slug",e);
        }
    }
}
