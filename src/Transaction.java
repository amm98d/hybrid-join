import java.sql.Date;

public class Transaction {
	String TRANSACTION_ID;
	String PRODUCT_ID;
	String CUSTOMER_ID;
	String CUSTOMER_NAME;
	String STORE_ID;
	String STORE_NAME;
	Date T_DATE;
	Integer QUANTITY;

	public Transaction(String tRANSACTION_ID, String pRODUCT_ID, String cUSTOMER_ID, String cUSTOMER_NAME,
			String sTORE_ID, String sTORE_NAME, Date t_DATE, Integer qUANTITY) {
		TRANSACTION_ID = tRANSACTION_ID;
		PRODUCT_ID = pRODUCT_ID;
		CUSTOMER_ID = cUSTOMER_ID;
		CUSTOMER_NAME = cUSTOMER_NAME;
		STORE_ID = sTORE_ID;
		STORE_NAME = sTORE_NAME;
		T_DATE = t_DATE;
		QUANTITY = qUANTITY;
	}

}