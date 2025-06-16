USE anime;

DELIMITER //
CREATE PROCEDURE obtenerUserByProcedure(
    IN p_id INT
)
BEGIN
    SELECT id, name, email, password, phone
    FROM users
    WHERE id = p_id;
END //
DELIMITER ;

CALL obtenerUserByProcedure(1);

delimiter //
CREATE PROCEDURE InsertarUser (
	IN name VARCHAR(50),
	IN email VARCHAR(30),
	IN password VARCHAR(30),
	IN phone Varchar(30))
BEGIN
    INSERT INTO users (name, email, password, phone)
    VALUES (name, email, password, phone);
END //
delimiter ;

delimiter //
CREATE PROCEDURE Login(
    IN Email VARCHAR(30),
    IN Password VARCHAR(30)
)
BEGIN
    SELECT COUNT(*) FROM users
    WHERE email = Email AND password = Password;
END //
delimiter ;

delimiter //
CREATE PROCEDURE updateUser(
	IN p_id int,
	IN p_name VARCHAR(50),
	IN p_email VARCHAR(30),
	IN p_password VARCHAR(30),
	IN p_phone Varchar(30)
)
BEGIN
	update users set id = p_id,
	name = p_name,
	email = p_email,
	password = p_password,
	phone = p_phone
	WHERE id = p_id;

    SELECT ROW_COUNT() AS rows_affected;

END //
delimiter ;

SELECT * FROM users WHERE id = 1;
