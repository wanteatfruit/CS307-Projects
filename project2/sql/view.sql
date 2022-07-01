--按公司的购入账单
create or replace view orderCostGroupByEnterprise as
select enterprise, sum(total_price)
from (
         select enterprise,quantity * model.unit_price as total_price
         from contract c
                  join model on model.product_model = c.product_model) sub
group by enterprise;

--按model的售出账单
create or replace view orderCostGroupByModel as
select product_model, sum(total_price)
from (
         select c.product_model, quantity * model.unit_price as total_price
         from contract c
                  join model on model.product_model = c.product_model) sub
group by product_model;


--按地区的进价账单
create or replace view  storageCostGroupByCenter as
select supply_center, sum(purchase_price * quantity )as total_price
from store_record group by supply_center;

--按model的金价账单
create or replace view storageCostGroupByModel as
select product_model, sum( purchase_price * quantity )as total_price
from store_record group by product_model;