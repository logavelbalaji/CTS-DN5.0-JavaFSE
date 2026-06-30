public class CommandPatternTest {
    public static void main(String[] args) {
        Light livingRoomLight = new Light();

        Command lightOn = new LightOnCommand(livingRoomLight);
        Command lightOff = new LightOffCommand(livingRoomLight);

        RemoteControl remote = new RemoteControl();

        System.out.println("Testing Light On Command:");
        remote.setCommand(lightOn);
        remote.pressButton();

        System.out.println("Testing Light Off Command:");
        remote.setCommand(lightOff);
        remote.pressButton();
    }
}
