package mof2.net.dempareader;

import android.net.Uri;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by uutarou on 2017/07/13.
 */

public class Article {
    private int id;
    private static int lastId = 0;
    private String title, description;
    private String link;
    private Date pubDate;
    private Category category;

    public Article() {
        super();
        this.id = lastId + 1;
        lastId++;
    }

    public Article(String title, String description, String uriString, String pubDateString, Category category) {
        this();
        this.title = title;
        this.description = removeHtmlTags(description);
        this.link = uriString;
        this.pubDate = parseDateString(pubDateString);
        this.category = category;
    }

    public Article(String title, String description, String uriString, String pubDateString) {
        this();
        this.title = title;
        this.description = removeHtmlTags(description);
        this.link = uriString;
        this.pubDate = parseDateString(pubDateString);
    }

    public int getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = removeHtmlTags(description);
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = parseDateString(pubDate);
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public Category getCategory() {
        return category;
    }

    private static Date parseDateString(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
        try {
            return sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String removeHtmlTags(String base) {
        return base.replaceAll("<(\".*?\"|'.*?'|[^'\"])*?>", "");
    }
}
