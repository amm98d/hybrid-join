import java.util.ArrayList;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

public class HashTableQueue {

	class Value {
		Queue.Node queueNode;
		Transaction transaction;

		public Value(Queue.Node queueNode, Transaction transaction) {
			this.queueNode = queueNode;
			this.transaction = transaction;
		}
	}

	private Integer size;
	private MultiValuedMap<String, Value> hashTable;
	private Queue queue;

	public HashTableQueue(Integer size) {
		this.size = size;
		this.hashTable = new ArrayListValuedHashMap<String, Value>(this.size);
		this.queue = new Queue();
	}

	public void addTransactions(ArrayList<Transaction> transactions) {
		for (Transaction transaction : transactions) {
			this.hashTable.put(transaction.PRODUCT_ID,
					new Value(this.queue.getNode(transaction.PRODUCT_ID), transaction));
		}
	}

	public String getOldestEntry() {
		return this.queue.getHeadData();
	}

	public int getCapacity() {
		return 100 - this.hashTable.size();
	}

	public ArrayList<Value> join(String PRODUCT_ID) {
		return new ArrayList<Value>(hashTable.get(PRODUCT_ID));
	}

	public void discard(String PRODUCT_ID) {
		if (hashTable.containsKey(PRODUCT_ID)) {
			this.queue.deleteNode(new ArrayList<Value>(hashTable.get(PRODUCT_ID)).get(0).queueNode);
			this.hashTable.remove(PRODUCT_ID);
		}
	}

	public boolean isEmpty() {
		return this.hashTable.size() == 0;
	}
	
	public void sizes() {
		System.out.println("hash table: "+this.hashTable.size());
		System.out.println("queue: "+this.queue.totalSize());
		System.out.println("============================================================");
	}

//	public void display() {
////		System.out.println("--------------------------------------------------------------------------------------");
////		System.out.println("HashTable:");
////		SortedSet<String> keys = new TreeSet<>(this.hashTable.keySet());
////		for (String key : keys) {
////			ArrayList<Value> values = new ArrayList<Value>(this.hashTable.get(key));
////			for (Value v : values) {
//////				System.out.println(v.queueNode + " " + v.transaction.TRANSACTION_ID + " " + v.transaction.PRODUCT_ID);
////				System.out.print("{" + v.transaction.TRANSACTION_ID + ", " + v.transaction.PRODUCT_ID + "}, ");
////			}
////		}
//		this.queue.display();
//	}
}
