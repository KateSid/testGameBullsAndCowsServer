package ru.game.domain;

public class UserAttempt {
    private int attemptNumb;
    private int uNumber;
    private int bools=0;
    private int cows=0;

    public int getAttemptNumb() {
        return attemptNumb;
    }

    public void setAttemptNumb(int attemptNumb) {
        this.attemptNumb = attemptNumb;
    }

    public int getuNumber() {
        return uNumber;
    }

    public void setuNumber(int uNumber) {
        this.uNumber = uNumber;
    }

    public int getBools() {
        return bools;
    }

    public void setBools(int bools) {
        this.bools = bools;
    }

    public int getCows() {
        return cows;
    }

    public void setCows(int cows) {
        this.cows = cows;
    }
}
