package org.haw.cas.GlobalTypes.MessageInfo;

/**
 * Created with IntelliJ IDEA.
 * User: Nils
 * Date: 11.11.13
 * Time: 18:56
 * To change this template use File | Settings | File Templates.
 */
public enum TypeOfInfo {
    Location(1),
    Need(2),
    Crevasse(3);

    private int number;

    private TypeOfInfo(int number) {
        //To change body of created methods use File | Settings | File Templates.
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
