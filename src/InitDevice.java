import jssc.SerialPort;
import jssc.SerialPortException;

/**
 * Created by andreas on 25.06.2014.
 */
public class InitDevice {


    public static void main(String[] args) {
        SerialPort serialPort = new SerialPort("COM19");
        try {
            System.out.println("Port opened: " + serialPort.openPort());
            System.out.println("Params setted: " + serialPort.setParams(19200, 8, 1, 0));
            for (int i = 0; i < 20; i++) {
                System.out.println("\"Hello World!!!\" successfully writen to port: " + serialPort.writeBytes(("Hello World!!! " + i).getBytes()));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println("Port closed: " + serialPort.closePort());
        } catch (SerialPortException ex) {
            System.out.println(ex);
        }
    }

}
