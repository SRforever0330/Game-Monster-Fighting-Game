package cn.sxau.zy.memto;

public class Memento {

	private int level;
	private int grade;
    private int blood;
    private int energy;
	private int maxScore;
	private int time;

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getBlood() {
		return blood;
	}

	public int getEnergy() {
		return energy;
	}
	
	public int getGrade() {
		return grade;
	}
	
	public int getMaxScore() {
		return maxScore;
	}

	public void setBlood(int blood) {
		this.blood = blood;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	
	public void setGrade(int grade) {
		this.grade = grade;
	}

	public void setMaxScore(int maxScore) {
		this.maxScore=maxScore;
		
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	   
}
