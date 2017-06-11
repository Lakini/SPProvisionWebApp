delimiter //

drop procedure if exists update_subscription;
create procedure update_subscription (IN app_id INT)
    BEGIN

        UPDATE AM_SUBSCRIPTION SET AM_SUBSCRIPTION.SUB_STATUS='UNBLOCKED' WHERE AM_SUBSCRIPTION.APPLICATION_ID = app_id;
        UPDATE AM_SUBSCRIPTION SET AM_SUBSCRIPTION.TIER_ID='Unlimited' WHERE AM_SUBSCRIPTION.APPLICATION_ID = app_id;

    END //

delimiter ;