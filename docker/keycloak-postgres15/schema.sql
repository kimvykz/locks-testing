CREATE TABLE customers (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT,
    email VARCHAR(100),
    created_at TIMESTAMP DEFAULT NOW()
);


INSERT INTO customers (name, age, email, created_at)
SELECT
  'User' || generate_series AS name,
  (20 + (random() * 40))::int AS age,
  'user' || generate_series || '@example.com' AS email,
  NOW() AS created_at
FROM generate_series(1, 1000);