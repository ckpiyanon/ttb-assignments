SELECT
    ROW_NUMBER() OVER (ORDER BY SUM(s.sale_amount) DESC) AS rank,
    c.id,
    c.first_name,
    c.last_name,
    c.is_vip,
    SUM(s.sale_amount) AS "year"
FROM customers c
INNER JOIN sales s ON c.id = s.customer_id
WHERE YEAR(s.sale_date) = YEAR(GETDATE() - 1)
GROUP BY c.id, c.first_name, c.last_name, c.is_vip
ORDER BY "year" DESC;