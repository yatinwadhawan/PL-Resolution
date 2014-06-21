import java.util.ArrayList;

public class Clauses {

	private ArrayList<Literal> lsLiterals;

	public Clauses() {
		lsLiterals = new ArrayList<Literal>();
	}

	public boolean checkForSameClause(Clauses cl) {
		if (this.getLsLiterals().size() != cl.getLsLiterals().size()) {
			return false;
		} else {
			int count = 0;
			ArrayList<Literal> ls1 = this.getLsLiterals();
			ArrayList<Literal> ls2 = cl.getLsLiterals();
			for (int i = 0; i < ls1.size(); i++) {
				for (int j = 0; j < ls2.size(); j++) {
					if (ls1.get(i).getGuest() == ls2.get(j).getGuest()
							&& ls1.get(i).getTable() == ls2.get(j).getTable()
							&& ls1.get(i).getNegated() == ls2.get(j)
									.getNegated()) {
						count++;
						break;
					}
				}
			}
			if (count == ls1.size()) {
				return true;
			}
		}
		return false;
	}

	public void removeDuplicateLiteralFromClause() {
		for (int x = 0; x < this.getLsLiterals().size(); x++) {
			Literal l1 = this.getLsLiterals().get(x);
			for (int y = x + 1; y < this.getLsLiterals().size(); y++) {
				Literal l2 = this.getLsLiterals().get(y);
				if (l1.getGuest() == l2.getGuest()
						&& l1.getTable() == l2.getTable()
						&& l1.getNegated() == l2.getNegated()) {
					this.getLsLiterals().remove(y);
				}
			}
		}
	}

	public ArrayList<Literal> getLsLiterals() {
		return lsLiterals;
	}

	public void setLsLiterals(ArrayList<Literal> lsLiterals) {
		this.lsLiterals = lsLiterals;
	}

}
