public class ClassroomController {
    private final DeviceRegistry reg;

    public ClassroomController(DeviceRegistry reg) { 
        this.reg = reg; 
    }

    public void startClass() {
        InputControl display = reg.getDevice(InputControl.class);
        
        if(display instanceof PowerControl){
            ((PowerControl) display).powerOn();
        }
        display.connectInput("HDMI-1");

        reg.getDevice(BrightnessControl.class).setBrightness(60);

        reg.getDevice(TemperatureControl.class).setTemperatureC(24);

        Scanner scan = reg.getDevice(Scanner.class);
        System.out.println("Attendance scanned: present=" + scan.scanAttendance());
    }

    public void endClass() {
        System.out.println("Shutdown sequence:");
        for (PowerControl device : reg.getAllDevices(PowerControl.class)) {
            device.powerOff();
        }
    }
}