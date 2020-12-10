-- Q2 Find total sales of product with respect to month using feature of rollup on month and
-- feature of dicing on supplier with name "3M Company" and Year as "2016". You will use
-- grouping sets feature to achieve rollup. Your output should be sequentially ordered
-- according to product and month.

select time.MONTH, product.PRODUCT_NAME, sum(sales.TOTAL_SALE)
from metro_dw.sales sales
join metro_dw.time time
join metro_dw.product product
join metro_dw.supplier supplier
on 
	sales.time_id = time.time_id and
    sales.product_id = product.product_id and
	sales.supplier_id = supplier.supplier_id and
    supplier.SUPPLIER_NAME = "3M Company" and 
    time.YEAR = 2016
group by time.MONTH, product.PRODUCT_NAME with rollup;