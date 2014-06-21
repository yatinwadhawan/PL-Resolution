public class Literal {
	private int guest;
	private int table;
	private boolean negated;

	public Literal(int g, int t, boolean bool) {
		this.guest = g;
		this.table = t;
		this.negated = bool;
	}

	public int getGuest() {
		return guest;
	}

	public void setGuest(int guest) {
		this.guest = guest;
	}

	public int getTable() {
		return table;
	}

	public void setTable(int table) {
		this.table = table;
	}

	public void setNegated(boolean negated) {
		this.negated = negated;
	}

	public Boolean getNegated() {
		return negated;
	}

	public void setNegated(Boolean negated) {
		this.negated = negated;
	}
}
