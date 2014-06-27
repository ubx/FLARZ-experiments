import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

/**
 * Created by andreas on 25.06.2014.
 */
public class InitDevice {

    static SerialPort serialPort = new SerialPort("COM19");

    public static void main(String[] args) {
        try {

            System.out.println("Port opened: " + serialPort.openPort());
            System.out.println("Params setted: " + serialPort.setParams(19200, 8, 1, 0));
            serialPort.setEventsMask(SerialPort.MASK_RXCHAR + SerialPort.MASK_CTS + SerialPort.MASK_DSR);//Set mask
            serialPort.addEventListener(new SerialPortReader());//Add SerialPortEventListener
            System.out.println("\"ER_CMD#C?\" successfully writen to port: " + serialPort.writeBytes(("ER_CMD#C?").getBytes()));

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            for (int i = 0; i < 0; i++) {
                System.out.println("\"Hello World!!!\" successfully writen to port: " + serialPort.writeBytes(("Hello World!!! ").getBytes()));
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

    private static class SerialPortReader implements SerialPortEventListener {

        public void serialEvent(SerialPortEvent event) {
            if (event.isCTS()) {//If CTS line has changed state
                if (event.getEventValue() == 1) {//If line is ON
                    System.out.println("CTS - ON");
                } else {
                    System.out.println("CTS - OFF");
                }
            } else if (event.isDSR()) {///If DSR line has changed state
                if (event.getEventValue() == 1) {//If line is ON
                    System.out.println("DSR - ON");
                } else {
                    System.out.println("DSR - OFF");
                }
            } else if (event.isRXCHAR()) {
                if (event.getEventValue() == 9)
                    try {
                        byte buffer[] = serialPort.readBytes(event.getEventValue());
                        System.out.println("Buffer=" + new String(buffer));
                    } catch (SerialPortException ex) {
                        System.out.println(ex);
                    }
            }
        }
    }


}
