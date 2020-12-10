-- Q4 Present the quarterly sales of each product for year 2016 along with its total yearly sales.
-- Note: each quarter sale must be a column and yearly sale as well. Order result according
-- to product

select q1.PRODUCT_NAME as Product, q1.sales as Q1, q2.sales as Q2, q3.sales as Q3, q4.sales as Q4, y.sales as Year
from
(select product.PRODUCT_NAME, product.PRODUCT_ID, sum(sales.TOTAL_SALE) as sales
from metro_dw.sales sales
join metro_dw.time time
join metro_dw.product product
on 
	sales.time_id = time.time_id and
    sales.product_id = product.product_id and
    time.QUARTER = 1 and
    time.YEAR = 2016
group by product.PRODUCT_NAME, time.QUARTER) as q1
join
(select product.PRODUCT_ID, sum(sales.TOTAL_SALE) as sales
from metro_dw.sales sales
join metro_dw.time time
join metro_dw.product product
on 
	sales.time_id = time.time_id and
    sales.product_id = product.product_id and
    time.QUARTER = 2 and
    time.YEAR = 2016
group by product.PRODUCT_NAME, time.QUARTER) as q2
join
(select product.PRODUCT_ID, sum(sales.TOTAL_SALE) as sales
from metro_dw.sales sales
join metro_dw.time time
join metro_dw.product product
on 
	sales.time_id = time.time_id and
    sales.product_id = product.product_id and
    time.QUARTER = 3 and
    time.YEAR = 2016
group by product.PRODUCT_NAME, time.QUARTER) as q3
join
(select product.PRODUCT_ID, sum(sales.TOTAL_SALE) as sales
from metro_dw.sales sales
join metro_dw.time time
join metro_dw.product product
on 
	sales.time_id = time.time_id and
    sales.product_id = product.product_id and
    time.QUARTER = 4 and
    time.YEAR = 2016
group by product.PRODUCT_NAME, time.QUARTER) as q4
join
(select product.PRODUCT_ID, sum(sales.TOTAL_SALE) as sales
from metro_dw.sales sales
join metro_dw.time time
join metro_dw.product product
on 
	sales.time_id = time.time_id and
    sales.product_id = product.product_id and
    time.YEAR = 2016
group by product.PRODUCT_NAME, time.YEAR) as y
on
	q1.PRODUCT_ID = q2.PRODUCT_ID and
    q1.PRODUCT_ID = q3.PRODUCT_ID and
    q1.PRODUCT_ID = q4.PRODUCT_ID and
    q1.PRODUCT_ID = y.PRODUCT_ID
order by q1.PRODUCT_NAME;