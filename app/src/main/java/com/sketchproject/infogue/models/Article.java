package com.sketchproject.infogue.models;

import java.util.List;

/**
 * Article model data.
 * <p>
 * Sketch Project Studio
 * Created by Angga on 12/04/2016 22.09.
 */
public class Article {
    public static final String TABLE = "articles";
    public static final String ID = "id";
    public static final String SLUG = "slug";
    public static final String TITLE = "title";
    public static final String FEATURED = "featured";
    public static final String FEATURED_REF = "featured_ref";
    public static final String CATEGORY = "category";
    public static final String CATEGORY_ID = "category_id";
    public static final String SUBCATEGORY = "subcategory";
    public static final String SUBCATEGORY_ID = "subcategory_id";
    public static final String CONTENT = "content";
    public static final String CONTENT_UPDATE = "content_update";
    public static final String EXCERPT = "excerpt";
    public static final String PUBLISHED_AT = "published_at";
    public static final String CREATED_AT = "created_at";
    public static final String TAGS = "tags";
    public static final String TAG = "tag";
    public static final String VIEW = "view";
    public static final String RATING_TOTAL = "total_rating";
    public static final String RATING = "rating";
    public static final String STATUS = "status";
    public static final String FOREIGN = "article_id";
    public static final String RATE = "rate";
    public static final String DATA = "article";
    public static final String COMMENT = "comment";
    public static final String RELATED = "related";

    public static final String STATUS_PENDING = "pending";
    public static final String STATUS_UPDATED = "updated";
    public static final String STATUS_REJECTED = "reject";
    public static final String STATUS_PUBLISHED = "published";
    public static final String STATUS_DRAFT = "draft";

    private int id;
    private String slug;
    private String title;
    private String featured;
    private int categoryId;
    private String category;
    private int subcategoryId;
    private String subcategory;
    private String content;
    private String contentUpdate;
    private String excerpt;
    private String publishedAt;
    private List<String> tags;
    private int view;
    private int rating;
    private String status;
    private int authorId;

    public Article() {

    }

    public Article(int id, String slug) {
        this.id = id;
        this.slug = slug;
    }

    public Article(int id, String slug, String title) {
        this.id = id;
        this.slug = slug;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFeatured() {
        return featured;
    }

    public void setFeatured(String featured) {
        this.featured = featured;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(int subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    @SuppressWarnings("unused")
    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getContentUpdate() {
        return contentUpdate;
    }

    public void setContentUpdate(String contentUpdate) {
        this.contentUpdate = contentUpdate;
    }
}
