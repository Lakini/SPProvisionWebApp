delimiter //

drop procedure if exists populate_subscription_validator;
create procedure populate_subscription_validator (IN app_id INT)
    BEGIN
        insert into subscription_validator (application_id, api_id, validator_id) values (app_id, 22, 1);
        insert into subscription_validator (application_id, api_id, validator_id) values (app_id, 36, 1);
        insert into subscription_validator (application_id, api_id, validator_id) values (app_id, 41, 1);
        insert into subscription_validator (application_id, api_id, validator_id) values (app_id, 42, 1);

    END //

delimiter ;