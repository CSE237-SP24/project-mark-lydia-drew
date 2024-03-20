package bankapp;

public class Card {
    private String number;
    private String type;

    public Card() {
        this.number = null;
        this.type = null;
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
        } else {
            this.type = "debit";
        }
    }

    public String getNumber() {
        return this.number;
    }

    public String getType() {
        return this.type;
    }

}
