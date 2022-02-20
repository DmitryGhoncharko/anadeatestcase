package testcase.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebFilter(filterName = "urlRewriteFilter")
public class UrlRewriteFilter implements Filter {

    private static final String REGEXP_FOR_FIND_COMMAND_FOR_SERVLET = "^http://.+:[0-9]+/controller\\?command=[a-zA-Z0-9]+([&][a-zA-Z0-9]+[=][a-zA-Z0-9]+)*$";
    private static final String CONTROLLER_COMMAND_SHOW_PAGE_BY_PAGE_SLUG = "/controller?command=showPage&pageSlug=";
    private static final String REGEXP_FOR_EXTRACT_SLUG_FROM_URL = "^http://.+:[0-9]+/$";
    private static final String REPLACEMENT_ON_EMPTY_TEXT = "";
    private static final String URL_PARAM_DEFINITOIN_SYMBOL = "?";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
       final HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
       final StringBuffer url = httpServletRequest.getRequestURL();
       final String queryString = httpServletRequest.getQueryString();
       final StringBuffer urlAndQueryString = createUrlAndQueryStringWithQueryStringValidation(url,queryString);
       if(urlIsCommandForServlet(urlAndQueryString)){
           filterChain.doFilter(servletRequest, servletResponse);
       }else {
           final Optional<String> pageSlug = extractPageSlugFromUrl(urlAndQueryString);
           if(pageSlug.isPresent()){
               final RequestDispatcher dispatcher = servletRequest.getRequestDispatcher(CONTROLLER_COMMAND_SHOW_PAGE_BY_PAGE_SLUG + pageSlug);
               dispatcher.forward(servletRequest, servletResponse);
           }else {
               filterChain.doFilter(servletRequest,servletResponse);
           }
       }
    }
    
    private boolean urlIsCommandForServlet(StringBuffer urlAndQueryString){
        final Pattern pattern = Pattern.compile(REGEXP_FOR_FIND_COMMAND_FOR_SERVLET);
        final Matcher matcher = pattern.matcher(urlAndQueryString);
        return matcher.find();
    }
    private Optional<String> extractPageSlugFromUrl(StringBuffer urlAndQueryString){
        final Pattern pattern = Pattern.compile(REGEXP_FOR_EXTRACT_SLUG_FROM_URL);
        final Matcher matcher = pattern.matcher(urlAndQueryString);
        final String pageSlug = matcher.replaceFirst(REPLACEMENT_ON_EMPTY_TEXT);
        return pageSlug.length()>0?Optional.of(pageSlug):Optional.empty();
    }
    private StringBuffer createUrlAndQueryStringWithQueryStringValidation(StringBuffer url, String queryString){
        if(queryString==null){
            return url;
        }
        return url.append(URL_PARAM_DEFINITOIN_SYMBOL).append(queryString);
    }
}
