package testcase.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import testcase.controller.RequestFactory;
import testcase.entity.Page;
import testcase.exception.ServiceException;
import testcase.model.service.PageService;

import java.util.List;

public class ShowMenuPageCommand implements Command{
    private static final Logger LOG = LoggerFactory.getLogger(ShowMenuPageCommand.class);
    private final PageService pageService;
    private final RequestFactory requestFactory;

    public ShowMenuPageCommand(PageService pageService, RequestFactory requestFactory) {
        this.pageService = pageService;
        this.requestFactory = requestFactory;
    }

    @Override
    public CommandResponse execute(CommandRequest request) throws ServiceException {
        try{
            final List<Page> allPublishedPagesSortedByPriority = pageService.findAllPublishedPagesSortedByPriority();
            final List<Page> allNotPublishedPages = pageService.findAllNotPublishedPages();
            request.addAttributeToJsp("publishedPages",allPublishedPagesSortedByPriority);
            request.addAttributeToJsp("notPublishedPages",allNotPublishedPages);
        }catch (ServiceException e){
            LOG.error("Service exception when try show menu page",e);
            throw e;
        }
        return requestFactory.createForwardResponse(PagePath.MENU.getPath());
    }
}
