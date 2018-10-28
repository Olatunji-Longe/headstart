package com.quadbaze.headstart.domain.models;

import com.quadbaze.headstart.domain.base.HashHandler;
import com.quadbaze.headstart.utils.json.GsonUtil;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * @author: Olatunji O. Longe
 * @created: 18 Oct, 2018, 6:07 AM
 */

public class Book {

    public Book(){}

    public Book(String imageUrl, String title, String author) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.author = author;
    }

    private String imageUrl;

    private String title;

    private String author;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public int hashCode() {
        HashHandler hashHandler = new HashHandler(Integer.valueOf(Book.class.getSimpleName().length()).longValue());
        return new HashCodeBuilder(hashHandler.PRIME, hashHandler.ID_PRIME).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Book)) return false;
        if (obj == this) return true;
        return new EqualsBuilder()
                .append(this.getTitle(), ((Book)obj).getTitle())
                .append(this.getAuthor(), ((Book)obj).getAuthor())
                .isEquals();
    }

    @Override
    public String toString() {
        return GsonUtil.toString(this);
    }

}
