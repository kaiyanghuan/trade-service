CREATE TABLE IF NOT EXISTS `products` (
  `product_id` bigint(20) NOT NULL DEFAULT 0,
  `created_by` varchar(255) NOT NULL,
  `created_timestamp` datetime NOT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_timestamp` datetime DEFAULT NULL,
  `product_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`product_id`)
);