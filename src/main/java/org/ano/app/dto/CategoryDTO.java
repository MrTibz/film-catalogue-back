package org.ano.app.dto;

public class CategoryDTO {
    private short categoryId;
    private String name;

    public CategoryDTO(short categoryId, String name) {
        this.categoryId = categoryId;
        this.name = name;
    }

    public short getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(short categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
