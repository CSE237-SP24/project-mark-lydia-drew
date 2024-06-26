package bankapp;

public class Card {
    private String number;
    private String type;
    private int typeNum;

    public Card() {
        this.number = null;
        this.type = null;
        this.typeNum = Integer.MIN_VALUE;
    }

    public Card(String number, int type) {
        this.setNumber(number);
        this.setType(type);
        this.typeNum = type;
    }

    // Getters and setters
    public String getNumber() {
        return this.number;
    }

    public String getType() {
        return this.type;
    }

    public int getTypeNum(){
        return this.typeNum;
    }

    public void setNumber(String number) {
        if (number.length() != 16) {
            throw new IllegalArgumentException("Card number must have 16 digits");
        }
        for (int i = 0; i < 16; i++) {
            int digit = number.charAt(i) - '0';
            if (digit > 9) {
                throw new IllegalArgumentException("Invalid card number");
            }
        }
        this.number = number;
    }

    public void setType(int type) {
        if ( type != 0 && type != 1 ) {
            throw new IllegalArgumentException("Invalid card type");
        }
        if (type == 0) {
            this.type = "credit";
            this.typeNum = 0;
        } else {
            this.type = "debit";
            this.typeNum = 1;
        }
    }


}
