-- Q6 Create a materialised view with name “STOREANALYSIS_MV” that presents the productwise sales analysis for each store. 

CREATE OR REPLACE VIEW metro_dw.STOREANALYSIS_MV AS
select store.STORE_ID, product.PRODUCT_ID, sum(sales.TOTAL_SALE) as STORE_TOTAL
from metro_dw.sales sales
join metro_dw.product product
join metro_dw.store store
on 
    sales.product_id = product.product_id and
    sales.STORE_ID = store.STORE_ID
group by store.STORE_ID, product.PRODUCT_ID with rollup;

select * from metro_dw.STOREANALYSIS_MV;