package bankapp;

public class CreditScore {
	
	private int score;
	
	public CreditScore() {
		this.score = 0;
	}
	
	public CreditScore(int score) {
		this.score = score;
	}
	
	public void setScore(int score) {
		if (score < 300 || score > 850) {
			throw new IllegalArgumentException("Invalid credit score value");
		}
		this.score = score;
	}
	
	public String getScoreRange() {
		String range = "";
		if (score > 799) {
			range = "Perfect";
		} else if (score > 739) {
			range = "Excellent";
		} else if (score > 669) {
			range = "Good";
		} else if (score > 579) {
			range = "Fair";
		} else {
			range = "Poor";
		}
		return range;
	}
	
	public void display() {
		System.out.println("Credit Score: " + score + " (" + getScoreRange() + ")");
		System.out.println();
	}

}
