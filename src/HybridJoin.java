import java.sql.SQLException;
import java.util.ArrayList;

public class HybridJoin {

	public static void run(DBHandler dbHandler, HashTableQueue hashTableQueue) throws SQLException {
		ArrayList<Transaction> streamBuffer;
		ArrayList<MasterData> diskBuffer;
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
				}
				hashTableQueue.discard(masterDataTuple.PRODUCT_ID);
			}
		}
	}

}
