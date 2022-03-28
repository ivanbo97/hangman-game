CREATE TABLE user (
  `user_id` VARCHAR(255) NOT NULL, 
  `first_name` VARCHAR(64) NOT NULL, 
  `last_name` VARCHAR(64) NOT NULL, 
  `username` VARCHAR(64) NOT NULL, 
  `password` VARCHAR(64) NOT NULL,
  CONSTRAINT `pk_user` PRIMARY KEY (`user_id`)
);

CREATE TABLE role (
  `role_id` VARCHAR(255) NOT NULL, 
  `name` VARCHAR(255) NOT NULL, 
  `description` VARCHAR(255) NOT NULL, 
  CONSTRAINT `pk_role` PRIMARY KEY (`role_id`)
);

CREATE TABLE role_permission (
  `role_permission_id` VARCHAR(255) NOT NULL, 
  `permission` VARCHAR(255) NOT NULL, 
  `role_id` VARCHAR(255) NOT NULL, 
  CONSTRAINT `pk_role_permission` PRIMARY KEY (`role_permission_id`),
  CONSTRAINT `fk_role_permission_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`)
  );
  
  CREATE TABLE user_role (
  `user_id` VARCHAR(255) NOT NULL, 
  `role_id` VARCHAR(255) NOT NULL, 
  CONSTRAINT `pk_user_role` PRIMARY KEY (`user_id`,`role_id`),
  CONSTRAINT `user_role_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `user_role_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`)
  );
