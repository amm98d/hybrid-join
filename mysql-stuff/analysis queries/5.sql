-- Q5 Find an anomaly in the data warehouse dataset. write a query to show the anomaly and
-- explain the anomaly in your project report.

select count(distinct t.CUSTOMER_ID, t.STORE_ID, t.PRODUCT_ID, m.SUPPLIER_ID, t.T_DATE)
from metro_db.transactions t join metro_db.masterdata m
on
	t.PRODUCT_ID = m.PRODUCT_ID;