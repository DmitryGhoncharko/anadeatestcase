package testcase.command;

public enum PagePath {
    MENU("/WEB-INF/jsp/menu.jsp"), ERROR("/WEB-INF/jsp/error.jsp");
    private final String path;
     PagePath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
