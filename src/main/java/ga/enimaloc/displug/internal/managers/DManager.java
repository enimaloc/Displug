package ga.enimaloc.displug.internal.managers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.apache.commons.collections4.map.UnmodifiableMap;

public class DManager<K, V> {

    private Map<K, V> objects;

    public DManager() {
        this.objects = new HashMap<>();
    }

    public void add(K key, V object) {
        this.objects.put(key, object);
    }

    public void remove(K key) {
        this.objects.remove(key);
    }

    public V get(K key) {
        return this.objects.get(key);
    }

    public Map<K, V> all() {
        return UnmodifiableMap.unmodifiableMap(this.objects);
    }
}
