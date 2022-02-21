package testcase.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import testcase.controller.RequestFactory;
import testcase.entity.Page;
import testcase.exception.ServiceException;
import testcase.model.service.PageService;

import java.sql.Date;
import java.util.Optional;

public class CreatePageCommand implements Command{
    private static final Logger LOG = LoggerFactory.getLogger(CreatePageCommand.class);
    private final PageService pageService;
    private final RequestFactory requestFactory;

    public CreatePageCommand(PageService pageService, RequestFactory requestFactory) {
        this.pageService = pageService;
        this.requestFactory = requestFactory;
    }

    @Override
    public CommandResponse execute(CommandRequest request) throws ServiceException {
        final String pageTitleForCrate = request.getParameter("pageTitleForCreate");
        final String pageDescriptionForCreate = request.getParameter("pageDescriptionForCreate");
        final String pageSlugForCreate = request.getParameter("pageSlugForCreate");
        final String pageMenuLabelForCreate = request.getParameter("pageMenuLabelForCreate");
        final String pageH1ForCreate = request.getParameter("pageH1ForCreate");
        final String pageContentForCreate = request.getParameter("pageContentForCreate");
        final Date pageDatePublishedForCreate = Date.valueOf(request.getParameter("pageDatePublishedForCreate"));
        final Integer pagePriorityForCreate = Integer.parseInt(request.getParameter("pagePriorityForCreate"));
        final Optional<Page> createdPage = pageService.createPage(pageTitleForCrate, pageDescriptionForCreate, pageSlugForCreate, pageMenuLabelForCreate, pageH1ForCreate, pageDatePublishedForCreate, pagePriorityForCreate , pageContentForCreate);
        if(createdPage.isPresent()){
            request.addAttributeToJsp("success", "Page was created");
            return requestFactory.createRedirectResponse("/controller?command=menuPage");
        }
        request.addAttributeToJsp("fail","Page not created, please try to create page with new params, slug may be unique");
        return requestFactory.createForwardResponse(PagePath.CREATE_PAGE.getPath());
    }
}
