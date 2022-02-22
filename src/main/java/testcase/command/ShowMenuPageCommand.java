package testcase.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import testcase.controller.RequestFactory;
import testcase.entity.Page;
import testcase.exception.ServiceError;
import testcase.model.service.PageService;

import java.util.List;

public class ShowMenuPageCommand implements Command {
    private static final Logger LOG = LoggerFactory.getLogger(ShowMenuPageCommand.class);
    private static final String PUBLISHED_PAGES_ATTRIBUTE_NAME = "publishedPages";
    private static final String NOT_PUBLISHED_PAGES_ATTRIBUTE_NAME = "notPublishedPages";
    private final PageService pageService;
    private final RequestFactory requestFactory;

    public ShowMenuPageCommand(PageService pageService, RequestFactory requestFactory) {
        this.pageService = pageService;
        this.requestFactory = requestFactory;
    }

    @Override
    public CommandResponse execute(CommandRequest request) throws ServiceError {
        try {
            final List<Page> allPublishedPagesSortedByPriority = pageService.findAllPublishedPagesSortedByPriority();
            final List<Page> allNotPublishedPages = pageService.findAllNotPublishedPages();
            request.addAttributeToJsp(PUBLISHED_PAGES_ATTRIBUTE_NAME, allPublishedPagesSortedByPriority);
            request.addAttributeToJsp(NOT_PUBLISHED_PAGES_ATTRIBUTE_NAME, allNotPublishedPages);
        } catch (ServiceError e) {
            LOG.error("Service exception when try show menu page", e);
            throw e;
        }
        LOG.info("Show menu page method complete successful");
        return requestFactory.createForwardResponse(PagePath.MENU.getPath());
    }
}
