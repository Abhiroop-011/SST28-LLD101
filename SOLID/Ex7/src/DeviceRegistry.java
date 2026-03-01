import java.util.*;

public class DeviceRegistry {
    private final List<Object> devices = new ArrayList<>();

    public void add(Object d) {devices.add(d);}

    public <T> T getDevice(Class<T> capability){
        for(Object d : devices){
            if(capability.isInstance(d)){
                return capability.cast(d);
            }
        }
        throw new IllegalStateException("Missing device with capability: " + capability.getSimpleName());
    }

    public <T> List<T> getAllDevices(Class<T> capability){
        List<T> result = new ArrayList<>();
        for(Object d : devices){
            if(capability.isInstance(d)){
                result.add(capability.cast(d));
            }
        }
        return result;
    }
}
