package net.etum.shattered.players.Builder;

public class Money {
    private int amount;

    public Money(int initialAmount) {
        this.amount = initialAmount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void add(int amount) {
        this.amount += amount;
    }

    public void remove(int amount) {
        int temp = this.amount;

        try {
            temp = Math.max(0, temp - amount);
        }catch (Exception e) {
            System.out.println("Failed to remove " + amount + " from money.");
            return;
        }

        this.amount = temp;
    }

    public boolean hasEnough(int amount) {
        return this.amount >= amount;
    }

}
