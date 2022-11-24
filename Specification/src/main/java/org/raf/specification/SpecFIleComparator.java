package org.raf.specification;

import java.util.Comparator;

public class SpecFIleComparator implements Comparator<SpecFile> {
    SortingCriteria sc;
    public SpecFIleComparator(SortingCriteria sortingCriteria) {
        sc = sortingCriteria;
    }

    @Override
    public int compare(SpecFile o1, SpecFile o2) {
        int name = o1.getFileName().compareTo(o2.getFileName());
        int created = o1.getDateCreated().compareTo(o2.getDateCreated());
        int modified = o1.getDateModified().compareTo(o2.getDateModified());
        if(name != 0 && sc.getName()) return name;
        else if(sc.getDateCreated() && created != 0) return created;
        else if(sc.getDateModified() && modified != 0) return modified;
        return 0;
    }
}
