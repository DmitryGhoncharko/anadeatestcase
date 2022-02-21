package testcase.validator;

import testcase.entity.Page;

import java.sql.Date;

public interface PageServiceValidator {

   boolean validateCreateAndUpdatePage(String title, String description, String slug, String menuLabel, String h1, Date publishedAt, Integer priority, String content, Integer ... pageId);

}
