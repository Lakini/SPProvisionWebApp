delimiter //

drop procedure if exists populate_sp_config;
create procedure populate_sp_config (IN consumer_key varchar(255))
    BEGIN
        insert into sp_configuration (client_id, config_key, config_value) values (consumer_key,'scope','openid');
        insert into sp_configuration (client_id, config_key, config_value) values (consumer_key,'scope','mnv');
        insert into sp_configuration (client_id, config_key, config_value) values (consumer_key,'scope','mc_mnv_validate');
        insert into sp_configuration (client_id, config_key, config_value) values (consumer_key,'scope','mc_mnv_validate_plus');
        insert into sp_configuration (client_id, config_key, config_value) values (consumer_key,'scope','mc_india_tc');
        insert into sp_configuration (client_id, config_key, config_value) values (consumer_key,'scope','email');
        insert into sp_configuration (client_id, config_key, config_value) values (consumer_key,'scope','address');
        insert into sp_configuration (client_id, config_key, config_value) values (consumer_key,'scope','mc_identity_phonenumber_hashed');
        insert into sp_configuration (client_id, config_key, config_value) values (consumer_key,'scope','profile');

    END //

delimiter ;