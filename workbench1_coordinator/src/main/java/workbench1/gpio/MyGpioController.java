package workbench1.gpio;

import com.pi4j.io.gpio.*;

public class MyGpioController{

    GpioController gpio ;
    GpioPinDigitalOutput out;

    public MyGpioController( Pin mypin){
        this.gpio = GpioFactory.getInstance();
        this.out = this.gpio.provisionDigitalOutputPin(mypin, PinState.LOW);
    }


    public void turnOnPin(Pin mypin){
        if(this.out.isHigh()){
            return;
        }
        else {
            this.out.high();
            this.out.setShutdownOptions(true, PinState.LOW);
        }

    }

    public void turnOffPin(Pin mypin){
        if (this.out.isLow()) {
            return;
        }
        else {

            this.out.low();
        }

    }


}
