package ga.enimaloc.displug.internal.managers;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.list.UnmodifiableList;

public class SManager<T> {

    private List<T> objects;

    public SManager() {
        this.objects = new ArrayList<>();
    }

    public void add(T object) {
        this.objects.add(object);
    }

    public void remove(int i) {
        this.objects.remove(i);
    }

    public T get(int i) {
        return this.objects.get(i);
    }

    public List<T> all() {
        return UnmodifiableList.unmodifiableList(this.objects);
    }
}
