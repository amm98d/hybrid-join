-- Q1 Present total sales of all products supplied by each supplier with respect to quarter and
-- month using drill down concept.

-- Both Quarter and Month Wise
select supplier.SUPPLIER_NAME, product.PRODUCT_NAME, time.QUARTER, time.MONTH, sum(sales.TOTAL_SALE)
from metro_dw.sales sales
join metro_dw.time time
join metro_dw.product product
join metro_dw.supplier supplier
on 
	sales.time_id = time.time_id and
    sales.product_id = product.product_id and
    sales.supplier_id = supplier.supplier_id
group by supplier.SUPPLIER_NAME, product.PRODUCT_NAME, time.QUARTER, time.MONTH
order by supplier.SUPPLIER_NAME, product.PRODUCT_NAME, time.QUARTER, time.MONTH;

-- Only Quarter Wise
select supplier.SUPPLIER_NAME, product.PRODUCT_NAME, time.QUARTER, sum(sales.TOTAL_SALE)
from metro_dw.sales sales
join metro_dw.time time
join metro_dw.product product
join metro_dw.supplier supplier
on 
	sales.time_id = time.time_id and
    sales.product_id = product.product_id and
    sales.supplier_id = supplier.supplier_id
group by supplier.SUPPLIER_NAME, product.PRODUCT_NAME, time.QUARTER
order by supplier.SUPPLIER_NAME, product.PRODUCT_NAME, time.QUARTER;

-- Only Month Wise
select supplier.SUPPLIER_NAME, product.PRODUCT_NAME, time.MONTH, sum(sales.TOTAL_SALE)
from metro_dw.sales sales
join metro_dw.time time
join metro_dw.product product
join metro_dw.supplier supplier
on 
	sales.time_id = time.time_id and
    sales.product_id = product.product_id and
    sales.supplier_id = supplier.supplier_id
group by supplier.SUPPLIER_NAME, product.PRODUCT_NAME, time.MONTH
order by supplier.SUPPLIER_NAME, product.PRODUCT_NAME, time.MONTH;