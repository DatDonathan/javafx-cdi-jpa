package at.ac.htl.util;

public interface Observer<T> {

    void onChange(T changed);

}
