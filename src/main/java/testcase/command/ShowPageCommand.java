package testcase.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import testcase.controller.RequestFactory;
import testcase.entity.Page;
import testcase.exception.ServiceError;
import testcase.model.service.PageService;

import java.util.Optional;

public class ShowPageCommand implements Command {
    private static final Logger LOG = LoggerFactory.getLogger(ShowPageCommand.class);
    private static final String PAGE_ATTRIBUTE_NAME = "page";
    private static final String PAGE_SLUG_PARAM_NAME = "pageSlug";
    private final PageService pageService;
    private final RequestFactory requestFactory;

    public ShowPageCommand(PageService pageService, RequestFactory requestFactory) {
        this.pageService = pageService;
        this.requestFactory = requestFactory;
    }

    @Override
    public CommandResponse execute(CommandRequest request) throws ServiceError {
        final String pageSlug = request.getParameter(PAGE_SLUG_PARAM_NAME);
        try {
            final Optional<Page> pageFromDB = pageService.findPageByPageSlug(pageSlug);
            if (pageFromDB.isPresent()) {
                LOG.info("Page was successful find by page slug. PageSlug: " + pageSlug);
                final Page page = pageFromDB.get();
                request.addAttributeToJsp(PAGE_ATTRIBUTE_NAME, page);
                return requestFactory.createForwardResponse(PagePath.PAGE.getPath());
            }
        } catch (ServiceError e) {
            LOG.error("Service exception when try show page command by page slug. PageSlug: " + pageSlug, e);
            throw e;
        }
        LOG.info("Not found page by page slug. PageSlug: " + pageSlug);
        return requestFactory.createForwardResponse(PagePath.ERROR.getPath());
    }
}
