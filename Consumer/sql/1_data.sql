

INSERT INTO "user" (id, full_name, password, username, role)
VALUES (1, 'user1', '$2a$12$nrBC3rNm1J39A5vkSCjy/eZH4vACmwFv31ngFrlrqP73Dp6rUxP3m', 'usernou1@gmail.com', 'ROLE_ADMIN');

INSERT INTO "user" (id, full_name, password, username, role)
VALUES (2, 'rares', '$2a$12$Z8wVQfLLHLndub50oJjEIuDphkN2r6OQbNQmqm3xiFWf/Szj9PsDa', 'rares@gmail.com', 'ROLE_USER');


INSERT INTO device(id, address, description, max_consumption_hourly, user_id)
VALUES (1, 'str. Plopilor', 'aragaz', 5.2, '2');

INSERT INTO device(id, address, description, max_consumption_hourly, user_id)
VALUES (2, 'str. Plopilor', 'aragaz2', 8.3, '2');

INSERT INTO backlog(id, device_id)
VALUES (1, 1);

INSERT INTO backlog(id, device_id)
VALUES (2, 2);
