public class ComputerBuilder {
    private String CPU;
    private String RAM;
    private String storage;
    private String GPU;
    private boolean hasBluetooth;
    private boolean hasWifi;

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

    public ComputerBuilder setCPU(String CPU) {
        this.CPU = CPU;
        return this;
    }

    public ComputerBuilder setRAM(String RAM) {
        this.RAM = RAM;
        return this;
    }

    public ComputerBuilder setStorage(String storage) {
        this.storage = storage;
        return this;
    }

    public ComputerBuilder setGPU(String GPU) {
        this.GPU = GPU;
        return this;
    }

    public ComputerBuilder setBluetooth(boolean hasBluetooth) {
        this.hasBluetooth = hasBluetooth;
        return this;
    }

    public ComputerBuilder setWifi(boolean hasWifi) {
        this.hasWifi = hasWifi;
        return this;
    }

    public Computer build() {
        return new Computer(this);
    }
}
