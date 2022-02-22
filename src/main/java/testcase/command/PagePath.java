package testcase.command;

public enum PagePath {
    MENU("/WEB-INF/jsp/menu.jsp"), ERROR("/WEB-INF/jsp/error.jsp"), CREATE_PAGE("/WEB-INF/jsp/createpage.jsp"),
    PAGE("/WEB-INF/jsp/page.jsp"), UPDATE_PAGE("/WEB-INF/jsp/updatepage.jsp");
    private final String path;

    PagePath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
