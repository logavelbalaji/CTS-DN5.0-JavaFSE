public class Computer {
    private String CPU;
    private String RAM;
    private String storage;
    private String GPU;
    private boolean hasBluetooth;
    private boolean hasWifi;

    Computer(ComputerBuilder builder) {
        this.CPU = builder.getCPU();
        this.RAM = builder.getRAM();
        this.storage = builder.getStorage();
        this.GPU = builder.getGPU();
        this.hasBluetooth = builder.hasBluetooth();
        this.hasWifi = builder.hasWifi();
    }

    public String getCPU() {
        return CPU;
    }

    public String getRAM() {
        return RAM;
    }

    public String getStorage() {
        return storage;
    }

    public String getGPU() {
        return GPU;
    }

    public boolean hasBluetooth() {
        return hasBluetooth;
    }

    public boolean hasWifi() {
        return hasWifi;
    }

    @Override
    public String toString() {
        return "Computer Configuration:\n" +
               "  CPU: " + CPU + "\n" +
               "  RAM: " + RAM + "\n" +
               "  Storage: " + storage + "\n" +
               "  GPU: " + (GPU != null ? GPU : "Integrated Graphics") + "\n" +
               "  Bluetooth: " + (hasBluetooth ? "Enabled" : "Disabled") + "\n" +
               "  WiFi: " + (hasWifi ? "Enabled" : "Disabled") + "\n";
    }
}
