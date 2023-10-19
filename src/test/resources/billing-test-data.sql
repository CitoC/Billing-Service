DELETE
FROM billing.cart
WHERE user_id > 0;
DELETE
FROM billing.sale
WHERE id > 0;

ALTER TABLE billing.cart
    AUTO_INCREMENT = 1;
ALTER TABLE billing.sale
    AUTO_INCREMENT = 1;

SET @admin = 1;
SET @employee = 2;
SET @premium = 3;

INSERT INTO billing.cart (user_id, movie_id, quantity)
VALUES (@admin, 299534, 1),
       (@admin, 299536, 2),
       (@admin, 24428, 4),
       (@admin, 99861, 8),
       (@employee, 472027, 1),
       (@employee, 287757, 2),
       (@employee, 142061, 3),
       (@employee, 40662, 4),
       (@employee, 123025, 5),
       (@employee, 414906, 6),
       (@employee, 736073, 7),
       (@employee, 272, 8),
       (@employee, 736074, 3),
       (@employee, 456348, 1),
       (@premium, 634649, 1),
       (@premium, 429617, 3),
       (@premium, 315635, 9);

INSERT INTO billing.sale (id, user_id, total, order_date)
VALUES (1, @employee, 69.70, TIMESTAMP '2022-01-01 12:00:00'),
       (2, @employee, 119.70, TIMESTAMP '2022-01-02 12:00:00'),
       (3, @premium, 377.55, TIMESTAMP '2022-01-03 12:00:00'),
       (4, @admin, 79.80, TIMESTAMP '2022-01-04 12:00:00'),
       (5, @admin, 99.75, TIMESTAMP '2022-01-05 12:00:00'),
       (6, @admin, 119.70, TIMESTAMP '2022-01-06 12:00:00'),
       (7, @admin, 139.65, TIMESTAMP '2022-01-07 12:00:00'),
       (8, @admin, 105.45, TIMESTAMP '2022-01-08 12:00:00'),
       (9, @admin, 159.60, TIMESTAMP '2022-01-09 12:00:00');

INSERT INTO billing.sale_item (sale_id, movie_id, quantity)
VALUES (1, 105, 1),
       (1, 165, 2),
       (1, 196, 3),
       (2, 1893, 1),
       (2, 1894, 2),
       (2, 1895, 3),
       (3, 140607, 7),
       (3, 181808, 8),
       (3, 181812, 9),
       (4, 13804, 4),
       (5, 51497, 5),
       (6, 82992, 6),
       (7, 168259, 7),
       (8, 337339, 8),
       (9, 385128, 9);
