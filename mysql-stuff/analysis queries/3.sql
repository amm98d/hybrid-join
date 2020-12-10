-- Q3 Find the 5 most popular products sold over the weekends.

select product.PRODUCT_NAME, sum(sales.TOTAL_SALE)
from metro_dw.sales sales
join metro_dw.time time
join metro_dw.product product
on 
	sales.time_id = time.time_id and
    sales.product_id = product.product_id and
    time.DAY_OF_WEEK IN ('Saturday', 'Sunday')
group by product.PRODUCT_NAME
order by sum(sales.TOTAL_SALE) desc
limit 5;

select product.PRODUCT_NAME, sum(sales.QUANTITY)
from metro_dw.sales sales
join metro_dw.time time
join metro_dw.product product
on 
	sales.time_id = time.time_id and
    sales.product_id = product.product_id and
    time.DAY_OF_WEEK IN ('Saturday', 'Sunday')
group by product.PRODUCT_NAME
order by sum(sales.QUANTITY) desc
limit 5;