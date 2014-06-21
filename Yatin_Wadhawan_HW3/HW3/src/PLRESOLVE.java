import java.util.ArrayList;

public class PLRESOLVE {

	public static boolean PL_RESOLVE() {

		int index = 0;
		boolean in = true;
		Predicates.makeClauses();
		ArrayList<Clauses> oldClauses = SAT.sentence;
		ArrayList<Clauses> newClauses = new ArrayList<Clauses>();
		oldClauses = Predicates.checkComplimentaryandDelete(oldClauses);

		while (true) {
			for (int i = 0; i < oldClauses.size(); i++) {
				if (in)
					index = i + 1;
				{
					if (i == index)
						index = i + 1;
				}
				for (int j = index; j < oldClauses.size(); j++) {
					Clauses cl = Predicates.resolve(oldClauses.get(i),
							oldClauses.get(j));
					if (cl.getLsLiterals().size() == 0) {
						// Empty Clause
						// System.out.print(oldClauses.size());
						return false;
					} else {
						if (cl.getLsLiterals().get(0).getGuest() == -1) {
							// Nothing is performed
						} else {
							// Resolved and add in new clauses
							cl.removeDuplicateLiteralFromClause();
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
				System.gc();
			}
			System.gc();
			newClauses = Predicates.checkComplimentaryandDelete(newClauses);

			// Check for subset
			boolean[] present = new boolean[newClauses.size()];
			boolean check = false;
			int count = 0;
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
				//				System.out.print(oldClauses.size());
				return true;
			} else {
				index = oldClauses.size();
				for (int i = 0; i < newClauses.size(); i++) {
					if (!present[i])
						oldClauses.add(newClauses.get(i));
				}
				present = null;
			}
			oldClauses = Predicates.checkComplimentaryandDelete(oldClauses);

			System.gc();
			in = false;
		}
	}
}
