package org.haw.cas.GlobalTypes.MessageInfo;

/**
 * Created with IntelliJ IDEA.
 * User: Guest1
 * Date: 10.11.13
 * Time: 14:09
 * To change this template use File | Settings | File Templates.
 */
public enum TypeOfNeed {
    Food(1),
    Water(2),
    Shelter(3),
    MedicalCare(4),
    Helper(5);

    private  int number;
    private TypeOfNeed(int number){
        this.number = number;
    }

    public int getNumber() {
        return number;
    }


}
