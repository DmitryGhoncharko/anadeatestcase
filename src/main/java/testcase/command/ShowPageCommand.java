package testcase.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import testcase.controller.RequestFactory;
import testcase.entity.Page;
import testcase.exception.ServiceException;
import testcase.model.service.PageService;

import java.util.Optional;

public class ShowPageCommand implements Command{
    private static final Logger LOG = LoggerFactory.getLogger(ShowPageCommand.class);
    private final PageService pageService;
    private final RequestFactory requestFactory;
    public ShowPageCommand(PageService pageService, RequestFactory requestFactory) {
        this.pageService = pageService;
        this.requestFactory = requestFactory;
    }

    @Override
    public CommandResponse execute(CommandRequest request) throws ServiceException {
        final String pageSlug = request.getParameter("pageSlug");
        try{
           final Optional<Page> pageFromDB = pageService.findPageByPageSlug(pageSlug);
           if(pageFromDB.isPresent()){
               request.addAttributeToJsp("page",pageFromDB);
               return requestFactory.createForwardResponse(PagePath.MENU.getPath());
           }
        }catch (ServiceException e){
            LOG.error("Service exception when try show page command",e);
            throw e;
        }
        return requestFactory.createForwardResponse(PagePath.ERROR.getPath());
    }
}
