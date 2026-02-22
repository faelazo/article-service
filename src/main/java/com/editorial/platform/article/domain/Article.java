package com.editorial.platform.article.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class Article extends PanacheEntity {

    public String title;
    public String author;
}
