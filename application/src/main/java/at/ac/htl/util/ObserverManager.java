package at.ac.htl.util;

import java.util.ArrayList;
import java.util.List;

public class ObserverManager<T extends Observable> implements Observable<T> {

    private List<Observer<T>> listeners = new ArrayList<>();

    @Override
    public void addListener(Observer<T> listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    @Override
    public void removeListener(Observer<T> listener) {
        listeners.remove(listener);
    }

    public void notifyObservers (T t) {
        for (Observer<T> observer : listeners) {
            observer.onChange(t);
        }
    }

}
