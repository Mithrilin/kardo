CREATE TABLE users (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
  email VARCHAR(250) NOT NULL UNIQUE,
  name VARCHAR(250) NOT NULL,
  surname VARCHAR(250) NOT NULL,
  patronymic VARCHAR(250),
  birthday DATE,
  country VARCHAR(250) NOT NULL,
  region VARCHAR(250) NOT NULL,
  city VARCHAR(250) NOT NULL,
  phone_number VARCHAR(250),
  gender VARCHAR(6),
  citizenship VARCHAR(250),
  profile_photo VARCHAR(250),
  portfolio VARCHAR(250),
  about_me VARCHAR(7000),
  role VARCHAR(10)
);

CREATE TABLE user_networks (
  user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
  network VARCHAR(250) NOT NULL,
  PRIMARY KEY (user_id, network)
);