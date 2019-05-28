package com.maxim_ilinov_gmail.feedsin.model;


public class GroupForDrawerMenu {


    private int id;

    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GroupForDrawerMenu)) return false;
        GroupForDrawerMenu that = (GroupForDrawerMenu) o;
        return getId() == that.getId() &&
                getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        // return Objects.hash(getId(), getName());

        return 7 * (name != null ? name.hashCode() : 0) ;

    }
}
