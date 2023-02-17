INSERT INTO users VALUES (1, 'user', '$2a$12$cj2HacZ7gnexF8HFAKFk.uAnCzPhpN.dhGyNNIIMsoqw1p1UNY7Cq', 1),
                         (2, 'admin', '$2a$12$hhUhoS2cqhEUf.WNvdPUIOpran1HJEbkuLLrX0VJ/3oFlDxkao6gO', 2)
on conflict (id) do nothing ;
INSERT INTO roles VALUES (1, array['ROLE_ALLOWED_VIEW']), (2, array['ROLE_ALLOWED_EDIT'])
on conflict (id) do nothing;

COPY cities(id, name, photo)
    FROM '/Users/i.vaspiakou/CityListApplication/src/main/resources/csv/cities.csv'
    DELIMITER ','
    CSV HEADER;
