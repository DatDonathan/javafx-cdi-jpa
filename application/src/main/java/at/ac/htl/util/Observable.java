package at.ac.htl.util;

public interface Observable<T> {

    void addListener(Observer<T> listener);

    void removeListener(Observer<T> listener);

}
