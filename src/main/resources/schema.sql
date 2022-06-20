DROP TABLE IF EXISTS books;

CREATE TABLE books
(
    id INT AUTO_INCREMENT PRIMARY KEY,
--     author VARCHAR(250) NOT NULL,
    price VARCHAR(250) DEFAULT NULL,
    priceOld VARCHAR(250) DEFAULT NULL,
    title VARCHAR(250) NOT NULL,
    authorID int NOT NULL
);

create table authors (
                         id INT,
                         first_name VARCHAR(50),
                         last_name VARCHAR(50)
);

/*DROP TABLE IF EXISTS AUTHORS;

CREATE TABLE AUTHORS
(
    ID INT AUTO_INCREMENT PRIMARY KEY,
    AUTHOR VARCHAR(255)
);
*/
