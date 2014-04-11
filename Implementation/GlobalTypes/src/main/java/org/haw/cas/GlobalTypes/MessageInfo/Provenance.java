package org.haw.cas.GlobalTypes.MessageInfo;

/**
 * Created with IntelliJ IDEA.
 * User: Nils
 * Date: 11.11.13
 * Time: 14:31
 * To change this template use File | Settings | File Templates.
 */
public enum Provenance {
    Twitter(1),
    Unknown(2);

    private  int number;
    private Provenance(int number){
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
