DELIMITER //
CREATE PROCEDURE get_favorito_by_id_user(v_id_user INT)
BEGIN
	SELECT a.*, TRUE as favorito FROM animes a
	INNER JOIN favorite f on a.id = f.idanime
	WHERE f.iduser = v_id_user;
END //
DELIMITER ;


DELIMITER //
CREATE PROCEDURE get_favorito_by_ids(v_id_user INT, v_id_anime INT)
BEGIN
	SELECT * FROM favorite WHERE idanime = v_id_anime AND iduser = v_id_user;
END //
DELIMITER ;


DELIMITER //
CREATE PROCEDURE insert_favorito(v_id_user INT, v_id_anime INT)
BEGIN
	INSERT INTO favorite VALUES (v_id_user, v_id_anime);
END //
DELIMITER ;


DELIMITER //
CREATE PROCEDURE delete_favorito(v_id_user INT, v_id_anime INT)
BEGIN
	DELETE FROM favorite WHERE idanime = v_id_anime AND iduser = v_id_user;
END //
DELIMITER ;
