package workbench1.gpio;

import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;

public class GpioConfig {


    public static Pin W1free1pin= RaspiPin.GPIO_03;
    public static MyGpioController W1free1_gpio = new MyGpioController(W1free1pin);
    public static Pin W1working1pin= RaspiPin.GPIO_00;
    public static MyGpioController W1working1_gpio = new MyGpioController(W1working1pin);
    public static Pin  W1completed1pin= RaspiPin.GPIO_02;
    public static MyGpioController W1completed1_gpio = new MyGpioController(W1completed1pin);



    public static Pin W1free2pin= RaspiPin.GPIO_10;
    public static MyGpioController W1free2_gpio = new MyGpioController(W1free2pin);
    public static Pin W1working2pin= RaspiPin.GPIO_04;
    public static MyGpioController W1working2_gpio = new MyGpioController(W1working2pin);
    public static Pin W1completed2pin= RaspiPin.GPIO_06;
    public static MyGpioController W1completed2_gpio = new MyGpioController(W1completed2pin);
    public static Pin W1pending2pin= RaspiPin.GPIO_05;
    public static MyGpioController W1pending2_gpio = new MyGpioController(W1pending2pin);



    public static Pin W1free3pin= RaspiPin.GPIO_13;
    public static MyGpioController W1free3_gpio = new MyGpioController(W1free3pin);
    public static Pin W1working3pin= RaspiPin.GPIO_11;
    public static MyGpioController W1working3_gpio = new MyGpioController(W1working3pin);
    public static Pin  W1pending3pin= RaspiPin.GPIO_12;
    public static MyGpioController W1pending3_gpio = new MyGpioController(W1pending3pin);


}
