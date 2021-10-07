package test3.collection;

import java.util.HashSet;
import java.util.Iterator;

public class SetTest {
	public static void main(String[] args) {
		HashSet set = new HashSet();
		set.add("hello");
		set.add("ho");
		set.add("asd");
		set.add("qwe");
		System.out.println(set);
		System.out.println(set.size());
		Iterator iter = set.iterator();
		while(iter.hasNext()) {
			System.out.println(iter.next());
		}
	}
}
