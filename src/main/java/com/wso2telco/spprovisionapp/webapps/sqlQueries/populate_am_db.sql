delimiter //

drop procedure if exists populate_am_db;
create procedure populate_am_db (IN app_name varchar(255),OUT app_id int)
    BEGIN
        select AM_APPLICATION.APPLICATION_ID from AM_APPLICATION where AM_APPLICATION.NAME= app_name into app_id;

        update AM_APPLICATION set AM_APPLICATION.APPLICATION_STATUS='APPROVED123' where AM_APPLICATION.APPLICATION_ID=app_id;
        update AM_APPLICATION set AM_APPLICATION.APPLICATION_TIER='Unlimited123' where AM_APPLICATION.APPLICATION_ID=app_id;

    END //

delimiter ;