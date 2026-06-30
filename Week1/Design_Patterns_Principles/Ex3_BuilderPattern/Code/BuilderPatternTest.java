public class BuilderPatternTest {
    public static void main(String[] args) {
        Computer gamingComputer = new ComputerBuilder()
                .setCPU("Intel Core i9-13900K")
                .setRAM("32GB DDR5")
                .setStorage("2TB NVMe SSD")
                .setGPU("NVIDIA RTX 4090")
                .setWifi(true)
                .setBluetooth(true)
                .build();

        Computer officeComputer = new ComputerBuilder()
                .setCPU("Intel Core i5-13400")
                .setRAM("16GB DDR4")
                .setStorage("512GB SATA SSD")
                .setWifi(true)
                .setBluetooth(false)
                .build();

        Computer budgetPC = new ComputerBuilder()
                .setCPU("AMD Ryzen 3 4100")
                .setRAM("8GB DDR4")
                .setStorage("256GB SSD")
                .build();

        System.out.println(gamingComputer);
        System.out.println(officeComputer);
        System.out.println(budgetPC);
    }
}
