package org.raf.specification;

import java.util.Comparator;

public class SpecFIleComparator implements Comparator {
    SortingCriteria sc;
    public SpecFIleComparator(SortingCriteria sortingCriteria) {
        sc = sortingCriteria;
    }

    @Override
    public int compare(Object o1, Object o2) {
        SpecFile sf1 = (SpecFile)o1;
        SpecFile sf2 = (SpecFile)o2;
        int name = sf1.getFileName().compareTo(sf2.getFileName());
        int created = sf1.getDateCreated().compareTo(sf2.getDateCreated());
        int modified = sf1.getDateModified().compareTo(sf2.getDateModified());
        if(name != 0 && sc.getName()) return name;
        else if(sc.getDateCreated() && created != 0) return created;
        else if(sc.getDateModified() && modified != 0) return modified;
        return 0;
    }
}
