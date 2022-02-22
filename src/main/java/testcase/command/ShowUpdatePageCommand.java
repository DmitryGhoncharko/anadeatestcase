package testcase.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import testcase.controller.RequestFactory;
import testcase.entity.Page;
import testcase.exception.ServiceError;
import testcase.model.service.PageService;

import java.util.Optional;

public class ShowUpdatePageCommand implements Command {
    private static final Logger LOG = LoggerFactory.getLogger(ShowCreateNewPageCommand.class);
    private static final String PAGE_SLUG_PARAM_NAME = "pageSlug";
    private static final String PAGE_FOR_UPDATE_ATTRIBUTE_NAME = "pageforupdate";
    private final PageService pageService;
    private final RequestFactory requestFactory;

    public ShowUpdatePageCommand(PageService pageService, RequestFactory requestFactory) {
        this.pageService = pageService;
        this.requestFactory = requestFactory;
    }

    @Override
    public CommandResponse execute(CommandRequest request) throws ServiceError {
        final String pageSlug = request.getParameter(PAGE_SLUG_PARAM_NAME);
        try {
            final Optional<Page> pageFromDb = pageService.findPageByPageSlug(pageSlug);
            if (pageFromDb.isPresent()) {
                final Page page = pageFromDb.get();
                request.addAttributeToJsp(PAGE_FOR_UPDATE_ATTRIBUTE_NAME, page);
                return requestFactory.createForwardResponse(PagePath.UPDATE_PAGE.getPath());
            }
        } catch (ServiceError e) {
            LOG.error("Service exception when try update page by slug. PageSlug: " + pageSlug, e);
            throw e;
        }
        return requestFactory.createForwardResponse(PagePath.ERROR.getPath());
    }
}
