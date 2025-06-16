DELIMITER //
CREATE PROCEDURE get_videos_id_anime(v_id_anime INT)
BEGIN
	SELECT * FROM videos where idanime = v_id_anime ORDER BY episode;
END //
DELIMITER ;