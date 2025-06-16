DELIMITER //

CREATE PROCEDURE get_all_animes_ordered_by_favorites(IN user_id INT)
BEGIN
    SELECT a.*, 
           CASE 
               WHEN f.idanime IS NOT NULL THEN TRUE 
               ELSE FALSE 
           END AS favorito
    FROM animes a
    LEFT JOIN favorite f ON a.id = f.idanime AND f.iduser = user_id
    ORDER BY favorito DESC, a.name;
END //

DELIMITER ;

call get_all_animes_ordered_by_favorites(1);
drop procedure if exists get_all_animes_ordered_by_favorites;

DELIMITER //
create PROCEDURE get_favorites(IN p_user_id INT, IN p_anime_id INT)
BEGIN
    SELECT a.*, 
           CASE 
               WHEN f.idanime IS NOT NULL THEN TRUE 
               ELSE FALSE 
           END AS favorito
    FROM animes a
    LEFT JOIN favorite f ON a.id = f.idanime AND f.iduser = p_user_id
    WHERE a.id = p_anime_id;
END //

DELIMITER ;

call get_favorites(1,27);
drop procedure if exists get_favorites;