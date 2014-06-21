import java.util.ArrayList;
import java.util.List;

public class Predicates {

	public static boolean PL_RESOLVE() {

		// ArrayList<Literal> ls3=new ArrayList<Literal>();
		// ArrayList<Literal> ls4=new ArrayList<Literal>();
		// ls3.add(new Literal(1, 1, false));
		// ls3.add(new Literal(1, 2, false));
		// ls3.add(new Literal(2, 1, false));
		// ls4.add(new Literal(1, 1, true));
		// ls4.add(new Literal(1, 2, true));
		// ls4.add(new Literal(2, 2, false));
		// resolve(ls3, ls4);

		int index = 0;
		boolean in = true;
		if (SAT.TOTAL_GUEST == 1 && SAT.TOTAL_TABLE >= 1)
			return true;
		makeClauses();
		ArrayList<Clauses> oldClauses = SAT.sentence;
		ArrayList<Clauses> newClauses = new ArrayList<Clauses>();
		oldClauses = checkComplimentaryandDelete(oldClauses);
		while (true) {
			for (int i = 0; i < oldClauses.size(); i++) {
				ArrayList<Literal> ls1 = oldClauses.get(i).getLsLiterals();
				if (in)
					index = i + 1;
				{
					if (i == index)
						index = i + 1;
				}
				for (int j = index; j < oldClauses.size(); j++) {
					Clauses cl = resolve(oldClauses.get(i), oldClauses.get(j));
					// Empty
					if (cl.getLsLiterals().size() == 0) {
						System.out.print(oldClauses.size());
						return false;
					} else {
						// Not resolved
						if (cl.getLsLiterals().get(0).getGuest() == -1) {
						} else {
							// resolved and we get a new resolvent
							// checking Duplicate literal in a clause and
							// removing
							// those
							for (int x = 0; x < cl.getLsLiterals().size(); x++) {
								Literal l1 = cl.getLsLiterals().get(x);
								for (int y = x + 1; y < cl.getLsLiterals()
										.size(); y++) {
									Literal l2 = cl.getLsLiterals().get(y);
									if (l1.getGuest() == l2.getGuest()
											&& l1.getTable() == l2.getTable()
											&& l1.getNegated() == l2
													.getNegated()) {
										cl.getLsLiterals().remove(y);
									}
								}
							}
							if (newClauses.size() == 0)
								newClauses.add(cl);
							else {
								boolean y = true;
								for (int d = 0; d < newClauses.size(); d++) {
									if (cl.checkForSameClause(newClauses.get(d))) {
										y = false;
										break;
									}
								}
								if (y)
									newClauses.add(cl);
							}
						}
					}
				}
			}
			System.gc();
			newClauses = checkComplimentaryandDelete(newClauses);
			boolean[] present = new boolean[newClauses.size()];
			boolean check = false;
			int count = 0;
			// Check newKB is subset of OldKB
			for (int a = 0; a < newClauses.size(); a++) {
				boolean flag = false;
				for (int b = 0; b < oldClauses.size(); b++) {
					if (newClauses.get(a).checkForSameClause(oldClauses.get(b))) {
						flag = true;
						present[count] = true;
						count++;
						break;
					}
				}
				if (flag) {
					check = true;
					continue;
				} else {
					check = false;
					break;
				}
			}
			if (check) {
				System.out.print(oldClauses.size());
				return true;
			} else {
				index = oldClauses.size();
				for (int i = 0; i < newClauses.size(); i++) {
					if (!present[i])
						oldClauses.add(newClauses.get(i));
				}
				present = null;
				System.gc();
			}
			 oldClauses = checkComplimentaryandDelete(oldClauses);

			System.gc();
			in = false;

		}
	}

	public static ArrayList<Clauses> checkComplimentaryandDelete(
			ArrayList<Clauses> oldClauses) {
		for (int g = 0; g < oldClauses.size(); g++) {
			ArrayList<Literal> ls = oldClauses.get(g).getLsLiterals();
			for (int h = 0; h < ls.size(); h++) {
				boolean f = false;
				for (int k = h + 1; k < ls.size(); k++) {
					if (ls.get(h).getTable() == ls.get(k).getTable()
							&& ls.get(h).getTable() == ls.get(k).getTable()
							&& ls.get(h).getNegated() != ls.get(k).getNegated()) {
						oldClauses.remove(g);
						f = true;
						break;
					}
				}
				if (f)
					break;
			}
		}
		return oldClauses;
	}

	public static Clauses resolve(Clauses cs1, Clauses cs2) {
		int count = 0, x = 0;
		ArrayList<Literal> ls1 = cs1.getLsLiterals();
		ArrayList<Literal> ls2 = cs2.getLsLiterals();
		int[] indexofOne = new int[ls1.size()];
		int[] indexofTwo = new int[ls2.size()];
		for (int i = 0; i < ls1.size(); i++) {
			Literal l1 = ls1.get(i);
			for (int j = 0; j < ls2.size(); j++) {
				Literal l2 = ls2.get(j);
				if (l1.getGuest() == l2.getGuest()
						&& l1.getTable() == l2.getTable()
						&& l1.getNegated() != l2.getNegated()) {
					count++;
					indexofOne[x] = i;
					indexofTwo[x] = j;
					x++;
					break;
				}
			}
		}
		Clauses c = new Clauses();
		if (count == 1) {
			ArrayList<Literal> ls = new ArrayList<Literal>();
			for (int i = 0; i < ls1.size(); i++) {
				if (indexofOne[0] != i) {
					ls.add(ls1.get(i));
				}
			}
			for (int i = 0; i < ls2.size(); i++) {
				if (indexofTwo[0] != i) {
					ls.add(ls2.get(i));
				}
			}
			c.setLsLiterals(ls);
			indexofOne = null;
			indexofTwo = null;
			System.gc();
			return c;
		}
		// When not resolved
		Literal l = new Literal(-1, -1, false);
		ArrayList<Literal> ls = new ArrayList<Literal>();
		ls.add(l);
		c.setLsLiterals(ls);
		indexofOne = null;
		indexofTwo = null;
		System.gc();
		return c;
	}

	public static void makeClauses() {

		// First Constraint:Single Table
		for (int g = 0; g < SAT.TOTAL_GUEST; g++) {
			Clauses clauses = new Clauses();
			for (int t = 0; t < SAT.TOTAL_TABLE; t++) {
				Literal literal = new Literal(g, t, false);
				clauses.getLsLiterals().add(literal);
			}
			SAT.sentence.add(clauses);
		}
		for (int g = 0; g < SAT.TOTAL_GUEST; g++) {
			for (int t = 0; t < SAT.TOTAL_TABLE; t++) {
				for (int x = t + 1; x < SAT.TOTAL_TABLE; x++) {
					Clauses clauses = new Clauses();
					Literal literal1 = new Literal(g, t, true);
					Literal literal2 = new Literal(g, x, true);
					clauses.getLsLiterals().add(literal1);
					clauses.getLsLiterals().add(literal2);
					SAT.sentence.add(clauses);
				}
			}
		}

		// Second Constraint:Friends
		for (int g1 = 0; g1 < SAT.TOTAL_GUEST; g1++) {
			for (int g2 = g1 + 1; g2 < SAT.TOTAL_GUEST; g2++) {
				if (friends(g1, g2)) {
					for (int x = 0; x < SAT.TOTAL_TABLE; x++) {
						Clauses clause1 = new Clauses();
						Literal literal1 = new Literal(g1, x, true);
						Literal literal2 = new Literal(g2, x, false);
						clause1.getLsLiterals().add(literal1);
						clause1.getLsLiterals().add(literal2);
						SAT.sentence.add(clause1);

						Clauses clause2 = new Clauses();
						Literal literal3 = new Literal(g1, x, false);
						Literal literal4 = new Literal(g2, x, true);
						clause2.getLsLiterals().add(literal3);
						clause2.getLsLiterals().add(literal4);
						SAT.sentence.add(clause2);

					}
				}
			}
		}

		// Third Constraint:Enemies
		for (int g1 = 0; g1 < SAT.TOTAL_GUEST; g1++) {
			for (int g2 = g1 + 1; g2 < SAT.TOTAL_GUEST; g2++) {
				if (enemies(g1, g2)) {
					for (int x = 0; x < SAT.TOTAL_TABLE; x++) {
						Clauses clause = new Clauses();
						Literal literal1 = new Literal(g1, x, true);
						Literal literal2 = new Literal(g2, x, true);
						clause.getLsLiterals().add(literal1);
						clause.getLsLiterals().add(literal2);
						SAT.sentence.add(clause);
					}
				}
			}
		}
		// printing sentences
		// for (int i = 0; i < SAT.sentence.size(); i++) {
		// Clauses c = SAT.sentence.get(i);
		// ArrayList<Literal> ls = c.getLsLiterals();
		// for (int j = 0; j < ls.size(); j++) {
		// if (ls.get(j).getNegated())
		// System.out.print("-X" + ls.get(j).getGuest()
		// + ls.get(j).getTable() + " ");
		// else
		// System.out.print("X" + ls.get(j).getGuest()
		// + ls.get(j).getTable() + " ");
		// }
		// System.out.print("\n");
		// }
		// System.out.print("Size-" + SAT.sentence.size());

	}

	public static boolean friends(int g1, int g2) {
		if (SAT.matrix[g1][g2] == 1)
			return true;
		return false;
	}

	public static boolean enemies(int g1, int g2) {
		if (SAT.matrix[g1][g2] == -1)
			return true;
		return false;
	}

	public static boolean indifferent(int g1, int g2) {
		if (SAT.matrix[g1][g2] == 0)
			return true;
		return false;
	}
}
