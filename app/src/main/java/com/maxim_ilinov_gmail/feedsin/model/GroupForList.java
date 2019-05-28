package com.maxim_ilinov_gmail.feedsin.model;

import androidx.annotation.Nullable;

public class GroupForList implements RvItem{


    private int id;
    private String name;
    private Boolean checked;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {

        return 7 * (name != null ? name.hashCode() : 0);

    }

    @Override
    public boolean equals(@Nullable Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }
        if (!(obj instanceof GroupForList)) {
            return false;
        }

        GroupForList other = (GroupForList) obj;

        return this.name.equals(other.name);

    }
}
