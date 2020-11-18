import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

public class HashTable {
	Integer size;
	MultiValuedMap<String, Transaction> map;

	public HashTable(Integer size) {
		this.size = size;
		this.map = new ArrayListValuedHashMap<String, Transaction>(this.size);
	}
}
