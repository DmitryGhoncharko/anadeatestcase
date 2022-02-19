package testcase.entity;

import java.sql.Date;
import java.util.Objects;

public class Page implements Entity{
    private final String title;
    private final String description;
    private final String slug;
    private final String menuLabel;
    private final String h1;
    private final String content;
    private final Date publishedAt;
    private final int priority;

    private Page(Builder builder){
        title = builder.title;
        description = builder.description;
        slug = builder.slug;
        menuLabel = builder.menuLabel;
        h1 = builder.h1;
        content = builder.content;
        publishedAt = builder.publishedAt;
        priority = builder.priority;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getSlug() {
        return slug;
    }

    public String getMenuLabel() {
        return menuLabel;
    }

    public String getH1() {
        return h1;
    }

    public String getContent() {
        return content;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Page page = (Page) o;

        if (priority != page.priority) return false;
        if (!Objects.equals(title, page.title)) return false;
        if (!Objects.equals(description, page.description)) return false;
        if (!Objects.equals(slug, page.slug)) return false;
        if (!Objects.equals(menuLabel, page.menuLabel)) return false;
        if (!Objects.equals(h1, page.h1)) return false;
        if (!Objects.equals(content, page.content)) return false;
        return Objects.equals(publishedAt, page.publishedAt);
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (slug != null ? slug.hashCode() : 0);
        result = 31 * result + (menuLabel != null ? menuLabel.hashCode() : 0);
        result = 31 * result + (h1 != null ? h1.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (publishedAt != null ? publishedAt.hashCode() : 0);
        result = 31 * result + priority;
        return result;
    }

    @Override
    public String toString() {
        return "Page{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", slug='" + slug + '\'' +
                ", menuLabel='" + menuLabel + '\'' +
                ", h1='" + h1 + '\'' +
                ", content='" + content + '\'' +
                ", publishedAt=" + publishedAt +
                ", priority=" + priority +
                '}';
    }

    public static class Builder{
        private String title;
        private String description;
        private String slug;
        private String menuLabel;
        private String h1;
        private String content;
        private Date publishedAt;
        private int priority;

        public Builder withTitle(String title){
            this.title = title;
            return this;
        }
        public Builder withDescription(String description){
            this.description = description;
            return this;
        }
        public Builder withSlug(String slug){
            this.slug = slug;
            return this;
        }
        public Builder withMenuLabel(String menuLabel){
            this.menuLabel = menuLabel;
            return this;
        }
        public Builder withH1(String h1){
            this.h1 = h1;
            return this;
        }
        public Builder withContent(String content){
            this.content = content;
            return this;
        }
        public Builder withDatePublished(Date publishedAt){
            this.publishedAt = publishedAt;
            return this;
        }
        public Builder withPriority(int priority){
            this.priority = priority;
            return this;
        }
        public Page build(){
            return new Page(this);
        }
    }
}
