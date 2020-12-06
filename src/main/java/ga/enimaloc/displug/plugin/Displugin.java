package ga.enimaloc.displug.plugin;

import ga.enimaloc.displug.api.Displug;

public abstract class Displugin {

    private Displug displug;

    public Displugin(Displug displug) {
        this.displug = displug;
    }

    protected Displug getDisplug() {
        return displug;
    }

    public abstract void onLoad();
    public abstract void onEnable();
    public abstract void onDisable();
}
