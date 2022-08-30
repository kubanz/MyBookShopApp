package com.example.MyBookShopApp.data.genre;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "genre")
@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class GenreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
//    @Column(columnDefinition = "INT")
//    private Integer parentId;
    @ManyToOne
    @JoinColumn(name = "parentId")
    @JsonIgnore
    private GenreEntity parent;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private String slug;
    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private String name;

    @OneToMany(mappedBy = "parent")
    private List<GenreEntity> children = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

//    public Integer getParentId() {
//        return parentId;
//    }
//
//    public void setParentId(Integer parentId) {
//        this.parentId = parentId;
//    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public GenreEntity getParent() {
        return parent;
    }

    public void setParent(GenreEntity parent) {
        this.parent = parent;
    }

    public List<GenreEntity> getChildren() {
        return children;
    }

    public void setChildren(List<GenreEntity> children) {
        this.children = children;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenreEntity that = (GenreEntity) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
