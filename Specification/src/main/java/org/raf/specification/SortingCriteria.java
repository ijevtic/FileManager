package org.raf.specification;

public class SortingCriteria {
    Boolean  name;
    Boolean dateCreated;
    Boolean dateModified;

    @Override
    public String toString() {
        return "SortingCriteria{" +
                "name=" + name +
                ", dateCreated=" + dateCreated +
                ", dateModified=" + dateModified +
                ", sortingOrder=" + sortingOrder +
                '}';
    }

    ESortingOrder sortingOrder;

    public SortingCriteria(Boolean name, Boolean dateCreated, Boolean dateModified, ESortingOrder sortingOrder) {
        this.name = name;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
        this.sortingOrder = sortingOrder;
    }

    public Boolean getName() {
        return name;
    }

    public void setName(Boolean name) {
        this.name = name;
    }

    public Boolean getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Boolean dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Boolean getDateModified() {
        return dateModified;
    }

    public void setDateModified(Boolean dateModified) {
        this.dateModified = dateModified;
    }

    public ESortingOrder getSortingOrder() {
        return sortingOrder;
    }

    public void setSortingOrder(ESortingOrder sortingOrder) {
        this.sortingOrder = sortingOrder;
    }
}
