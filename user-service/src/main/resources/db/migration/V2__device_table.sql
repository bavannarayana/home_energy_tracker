CREATE TABLE `device` (
                        `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                        `device_name` VARCHAR(100) NOT NULL,
                        `type` VARCHAR(50),
                        `location` VARCHAR(250),
                        `user_id` BIGINT,
                        KEY `idx_device_user-id` (`user_id`),
                        CONSTRAINT `fk_device_user`
                                FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
                                ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;