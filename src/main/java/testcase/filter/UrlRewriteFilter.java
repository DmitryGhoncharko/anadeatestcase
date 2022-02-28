package testcase.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebFilter(filterName = "urlRewriteFilter")
public class UrlRewriteFilter implements Filter {
    private static final Logger LOG = LoggerFactory.getLogger(UrlRewriteFilter.class);
    private static final String REGEXP_FOR_FIND_COMMAND_FOR_SERVLET = "^http://.+/controller\\?command=[a-zA-Z0-9]+([&][a-zA-Z0-9]+[=].+)*$";
    private static final String REGEXP_FOR_STATIC_CONTENT = "^http://.+/static/images.*";
    private static final String CONTROLLER_COMMAND_SHOW_PAGE_BY_PAGE_SLUG = "/controller?command=showPage&pageSlug=";
    private static final String REGEXP_FOR_EXTRACT_SLUG_FROM_URL = "^http://.+/";
    private static final String REPLACEMENT_ON_EMPTY_TEXT = "";
    private static final String URL_PARAM_DEFINITOIN_SYMBOL = "?";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        final StringBuffer url = httpServletRequest.getRequestURL();
        final String queryString = httpServletRequest.getQueryString();
        final StringBuffer urlAndQueryString = createUrlAndQueryStringWithQueryStringValidation(url, queryString);
        if (urlIsCommandForServlet(urlAndQueryString) || urlRequestToStaticContent(urlAndQueryString)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            final Optional<String> pageSlug = extractPageSlugFromUrl(urlAndQueryString);
            if (pageSlug.isPresent()) {
                final RequestDispatcher dispatcher = servletRequest.getRequestDispatcher(CONTROLLER_COMMAND_SHOW_PAGE_BY_PAGE_SLUG + pageSlug.get());
                dispatcher.forward(servletRequest, servletResponse);
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }
    }

    private boolean urlIsCommandForServlet(StringBuffer urlAndQueryString) {
        LOG.info("Check that url was command for servlet");
        final Pattern pattern = Pattern.compile(REGEXP_FOR_FIND_COMMAND_FOR_SERVLET);
        final Matcher matcher = pattern.matcher(urlAndQueryString);
        return matcher.find();
    }

    private boolean urlRequestToStaticContent(StringBuffer urlAndQueryString) {
        LOG.info("Check that url go to the static content");
        final Pattern pattern = Pattern.compile(REGEXP_FOR_STATIC_CONTENT);
        final Matcher matcher = pattern.matcher(urlAndQueryString);
        return matcher.find();
    }

    private Optional<String> extractPageSlugFromUrl(StringBuffer urlAndQueryString) {
        final Pattern pattern = Pattern.compile(REGEXP_FOR_EXTRACT_SLUG_FROM_URL);
        final Matcher matcher = pattern.matcher(urlAndQueryString);
        if (matcher.find()) {
            final String pageSlug = matcher.replaceFirst(REPLACEMENT_ON_EMPTY_TEXT);
            LOG.debug("Page slug after extracted from url: " + pageSlug);
            return pageSlug.length() > 0 ? Optional.of(pageSlug) : Optional.empty();
        }
        return Optional.empty();
    }

    private StringBuffer createUrlAndQueryStringWithQueryStringValidation(StringBuffer url, String queryString) {
        if (queryString == null) {
            return url;
        }
        return url.append(URL_PARAM_DEFINITOIN_SYMBOL).append(queryString);
    }
}
