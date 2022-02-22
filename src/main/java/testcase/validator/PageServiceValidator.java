package testcase.validator;

import java.sql.Date;

public interface PageServiceValidator {

    boolean validateCreateOrUpdatePage(String title, String description, String slug, String menuLabel, String h1, Date publishedAt, Integer priority, String content, Long... pageId);

}
