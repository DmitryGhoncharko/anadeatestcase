package testcase.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import testcase.controller.RequestFactory;
import testcase.entity.Page;
import testcase.exception.ServiceError;
import testcase.model.service.PageService;

import java.sql.Date;
import java.util.Optional;

public class CreatePageCommand implements Command {
    private static final Logger LOG = LoggerFactory.getLogger(CreatePageCommand.class);
    private static final String PAGE_TITLE_FOR_CREATE_PARAM_NAME = "pageTitleForCreate";
    private static final String PAGE_DESCRIPTION_FOR_CREATE_PARAM_NAME = "pageDescriptionForCreate";
    private static final String PAGE_SLUG_FOR_CREATE_PARAM_NAME = "pageSlugForCreate";
    private static final String PAGE_MENU_LABEL_FOR_CREATE_PARAM_NAME = "pageMenuLabelForCreate";
    private static final String PAGE_H_1_FOR_CREATE_PARAM_NAME = "pageH1ForCreate";
    private static final String PAGE_CONTENT_FOR_CREATE_PARAM_NAME = "pageContentForCreate";
    private static final String PAGE_DATE_PUBLISHED_FOR_CREATE_PARAM_NAME = "pageDatePublishedForCreate";
    private static final String PAGE_PRIORITY_FOR_CREATE_PARAM_NAME = "pagePriorityForCreate";
    private static final String CONTROLLER_COMMAND_MENU_PAGE = "/controller?command=menuPage";
    private final PageService pageService;
    private final RequestFactory requestFactory;

    public CreatePageCommand(PageService pageService, RequestFactory requestFactory) {
        this.pageService = pageService;
        this.requestFactory = requestFactory;
    }

    @Override
    public CommandResponse execute(CommandRequest request) throws ServiceError {
        final String pageTitleForCrate = request.getParameter(PAGE_TITLE_FOR_CREATE_PARAM_NAME);
        final String pageDescriptionForCreate = request.getParameter(PAGE_DESCRIPTION_FOR_CREATE_PARAM_NAME);
        final String pageSlugForCreate = request.getParameter(PAGE_SLUG_FOR_CREATE_PARAM_NAME);
        final String pageMenuLabelForCreate = request.getParameter(PAGE_MENU_LABEL_FOR_CREATE_PARAM_NAME);
        final String pageH1ForCreate = request.getParameter(PAGE_H_1_FOR_CREATE_PARAM_NAME);
        final String pageContentForCreate = request.getParameter(PAGE_CONTENT_FOR_CREATE_PARAM_NAME);
        final Date pageDatePublishedForCreate = Date.valueOf(request.getParameter(PAGE_DATE_PUBLISHED_FOR_CREATE_PARAM_NAME));
        final Integer pagePriorityForCreate = Integer.parseInt(request.getParameter(PAGE_PRIORITY_FOR_CREATE_PARAM_NAME));
        try {
            final Optional<Page> createdPage = pageService.createPage(pageTitleForCrate, pageDescriptionForCreate, pageSlugForCreate, pageMenuLabelForCreate, pageH1ForCreate, pageDatePublishedForCreate, pagePriorityForCreate, pageContentForCreate);
            if (createdPage.isPresent()) {
                LOG.debug("page was created pageSlug:" + createdPage.get().getSlug());
                return requestFactory.createRedirectResponse(CONTROLLER_COMMAND_MENU_PAGE);
            }
        } catch (ServiceError e) {
            LOG.error("Service exception when we try create page", e);
            throw e;
        }
        LOG.info("Page dont created");
        return requestFactory.createForwardResponse(PagePath.ERROR.getPath());
    }
}
