-- This user is a warehouse admin so they have a warehouse associated
INSERT INTO `user` (id, username, password, role, warehouse_id) VALUES (1, "jean", "1234", 0, 1);

-- This user is a buyer so they have not a warehouse associated
INSERT INTO `user` (id, username, password, role, warehouse_id) VALUES (2, "franco", "1234", 1, null);