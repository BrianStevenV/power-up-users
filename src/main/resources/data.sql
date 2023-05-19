INSERT INTO `users` (
    `id`,
    `dni_number`,
    `name`,
    `surname`,
    `mail`,
    `phone`,
    `birthdate`,
    `password`,
  )
VALUES
  (
    '1',
    '78640357',
    'Test Case ',
    'Test Case',
    'test@gmail.com',
    '+573009998749',
    '03/10/2002',
    '$2a$10$GlsGSNhkbVon6ZOSNMptOu5RikedRzlCAhMa7YpwvUSS0c88WT99S',
  );


INSERT INTO `role` (`id`, `description`, `name`) VALUES ('1', 'ROLE_ADMIN', 'ROLE_ADMIN');
INSERT INTO `role` (`id`, `description`, `name`) VALUES ('2', 'ROLE_USER', 'ROLE_USER');

INSERT INTO `user` (`id_person`, `id_role`) VALUES ('1', '1');