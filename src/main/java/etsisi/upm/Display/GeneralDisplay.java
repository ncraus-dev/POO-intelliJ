package etsisi.upm.Display;
import etsisi.upm.Logic.*;

public class GeneralDisplay {
    GeneralLogic generalLogic;

    public GeneralDisplay() {
        this.generalLogic = new GeneralLogic();
    }

    public String displayEcho(String input) {
        return generalLogic.commandEcho(input);
    }

    public String displayHelp() {
        return generalLogic.showHelp();
    }

}
