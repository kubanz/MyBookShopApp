package com.example.MyBookShopApp.dtos;

import com.example.MyBookShopApp.data.genre.GenreEntity;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.*;


//@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class GenreDto {

    private List<GenreEntity> genreEntities;

    public GenreDto(List<GenreEntity> genreEntities) {
        this.genreEntities = genreEntities;
    }

    public List<GenreEntity> getGenreEntities() {
        return genreEntities;
    }

    public void setGenreEntities(List<GenreEntity> genreEntities) {
        this.genreEntities = genreEntities;
    }
//    private int id;
//    //    @Column(columnDefinition = "INT")
////    private Integer parentId;
//    @ManyToOne
//    @JoinColumn(name = "parentId")
//    @JsonIgnore
//    private Integer parent;
//    @JsonProperty("slug")
//    private String slug;
//    @JsonProperty("name")
//    private String name;
//    @OneToMany(mappedBy = "parent")
//    private List<GenreEntity> children = new ArrayList<>();
//
//     public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }

//    public Integer getParentId() {
//        return parentId;
//    }
//
//    public void setParentId(Integer parentId) {
//        this.parentId = parentId;
//    }


//    public String getSlug() {
//        return slug;
//    }
//
//    public void setSlug(String slug) {
//        this.slug = slug;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//
//    public Integer getParent() {
//        return parent;
//    }
//
//    public void setParent(Integer parent) {
//        this.parent = parent;
//    }
//
//    public List<GenreEntity> getChildren() {
//        return children;
//    }
//
//    public void setChildren(List<GenreEntity> children) {
//        this.children = children;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        GenreDto that = (GenreDto) o;
//        return id == that.id;
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id);
//    }
}
