package testcase.validator;

import java.sql.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimplePageServiceValidator implements PageServiceValidator {
    private static final String REGEXP_PATTERN_FOR_VARCHAR_FIELDS = "^.{1,200}$";
    private static final String REGEXP_PATTERN_FOR_TEXT_FIELDS = ".{1,}";
    private static final String REGEXP_PATTERN_FOR_INTEGER_FIELDS = "^[0-9]{1,2}$";

    @Override
    public boolean validateCreateOrUpdatePage(String title, String description, String slug, String menuLabel, String h1, Date publishedAt, Integer priority, String content, Long... pageId) {
        if (title == null || description == null || slug == null || menuLabel == null || h1 == null || publishedAt == null || priority == null || content == null || pageId == null) {

            return false;

        }
        final String priorityAsString = String.valueOf(priority);

        return validateFieldsByPattern(REGEXP_PATTERN_FOR_VARCHAR_FIELDS, title, slug, menuLabel, h1) &&
                validateFieldsByPattern(REGEXP_PATTERN_FOR_TEXT_FIELDS, description, content) &&
                validateFieldsByPattern(REGEXP_PATTERN_FOR_INTEGER_FIELDS, priorityAsString);
    }

    private boolean validateFieldsByPattern(String regexpPattern, String... varchar) {
        final Pattern pattern = Pattern.compile(regexpPattern);
        for (String currentVarchar : varchar) {
            final Matcher matcher = pattern.matcher(currentVarchar.trim());
            if (!matcher.find()) {
                return false;
            }
        }
        return true;
    }

}
