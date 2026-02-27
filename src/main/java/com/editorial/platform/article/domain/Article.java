package com.editorial.platform.article.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;

@Entity
public class Article extends PanacheEntity {

    @NotNull
    public String title;
    public String author;
}
