package red_flexible;

import java.util.Comparator;

public class SortMyList implements Comparator<Node>{

	@Override
	public int compare(Node o1, Node o2) {
		return new Integer(o1.getTasks()).compareTo(new Integer(o2.getTasks()));
	}

}
