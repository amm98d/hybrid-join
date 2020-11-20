public class Queue {

	class Node {
		String data;
		Node prev;
		Node next;

		public Node(String data) {
			this.data = data;
		}
	}

	private Node head;

	// returns node if already exists else creates a new one and returns that
	public Node getNode(String PRODUCT_ID) {
		if (this.head == null) {
			this.head = new Node(PRODUCT_ID);
			this.head.next = null;
			this.head.prev = null;
			return head;
		}
		Node iteratorNode = head;
		while (true) {
			if (iteratorNode.data.equals(PRODUCT_ID))
				return iteratorNode;
			if (iteratorNode.next != null)
				iteratorNode = iteratorNode.next;
			else
				break;
		}
		Node newNode = new Node(PRODUCT_ID);
		newNode.next = null;
		newNode.prev = iteratorNode;
		iteratorNode.next = newNode;
		return newNode;
	}

	public int totalSize() {
		int res = 0;
		Node node = this.head;
		while (node != null) {
			res++;
			node = node.next;
		}
		return res;
	}

//	public void checkDuplicates() {
//		Node head_ref = this.head;
//		if ((head_ref) == null || (head_ref).next == null)
//			System.out.println("Khali!");
//		Node ptr1, ptr2;
//		for (ptr1 = head_ref; ptr1 != null; ptr1 = ptr1.next) {
//			ptr2 = ptr1.next;
//			while (ptr2 != null) {
//				if (ptr1.data.equals(ptr2.data)) {
//					System.out.println("PROBLEMMMM !");
//					System.out.println(ptr1.data);
//					System.exit(1);
//				} else
//					ptr2 = ptr2.next;
//			}
//		}
//		System.out.println("ALL CLEAR!");
//	}

	public String getHeadData() {
		if (this.head != null)
			return head.data;
		return null;
	}

	public void deleteNode(Node toDelete) {
		if (this.head == null || toDelete == null)
			return;
		if (toDelete == this.head)
			this.head = toDelete.next;
		if (toDelete.next != null)
			toDelete.next.prev = toDelete.prev;
		if (toDelete.prev != null)
			toDelete.prev.next = toDelete.next;
		toDelete = null;
		return;
	}

//	public void display() {
////		System.out.println("\n--------------------------------------------------------------------------------------");
//		System.out.println("QUEUE:");
//		Node node = head;
//		while (node != null) {
//			if (node.data.equals("P-1071")) {
//				System.out.println("\n\t===>\tYESSS P-1071");
//			}
//			System.out.print(node.data + " , ");
//			node = node.next;
//		}
//		System.out.println();
//	}

}