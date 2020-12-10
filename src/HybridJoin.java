import java.sql.SQLException;
import java.util.ArrayList;

public class HybridJoin {

	public static void run(DBHandler dbHandler) throws SQLException {
		HashTableQueue hashTableQueue = new HashTableQueue(1000);
		ArrayList<Transaction> streamBuffer;
		ArrayList<MasterData> diskBuffer;
		int count = 0;
		while (true) {
			// base-condition
			if (dbHandler.endOfTransactions() && hashTableQueue.isEmpty()) {
				break;
			}
			// Step-1: Data Extraction
			streamBuffer = dbHandler.getTransactions(hashTableQueue.getCapacity());
			hashTableQueue.addTransactions(streamBuffer);
			// Step-2: Data Transformation
			diskBuffer = dbHandler.getMasterData(hashTableQueue.getOldestEntry());
			for (MasterData masterDataTuple : diskBuffer) {
				ArrayList<HashTableQueue.Value> joined = hashTableQueue.join(masterDataTuple.PRODUCT_ID);
				for (HashTableQueue.Value tuple : joined) {
					tuple.transaction.addAttributes(masterDataTuple);
					// Step-3: Data Loading
					dbHandler.shipToDW(tuple.transaction);					
					if (count<50) {
						System.out.println((count+1)+". "+tuple.transaction);
						count++;
					}
				}
				hashTableQueue.discard(masterDataTuple.PRODUCT_ID);
			}
		}
	}

}
