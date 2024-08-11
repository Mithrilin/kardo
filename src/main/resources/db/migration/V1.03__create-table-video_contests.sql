CREATE TABLE video_contests (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
  title VARCHAR(250) NOT NULL UNIQUE,
  hashtag VARCHAR(20) NOT NULL UNIQUE,
  contest_start DATE NOT NULL,
  contest_end DATE NOT NULL,
  status VARCHAR(20) NOT NULL,
  description VARCHAR(1000) NOT NULL
);