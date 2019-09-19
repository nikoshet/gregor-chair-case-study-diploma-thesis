package workbench2.gpio;

import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;

public class GpioConfig {

    public static Pin W2freepin= RaspiPin.GPIO_15;
    public static MyGpioController W2free_gpio = new MyGpioController(W2freepin);
    public static Pin W2assemblingpin= RaspiPin.GPIO_16;
    public static MyGpioController W2assembling_gpio = new MyGpioController(W2assemblingpin);
    public static Pin  W2pendingpin= RaspiPin.GPIO_01;
    public static MyGpioController W2penfing_gpio = new MyGpioController(W2pendingpin);
}
