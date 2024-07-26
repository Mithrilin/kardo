CREATE TABLE offline_competitions (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
  title VARCHAR(250) NOT NULL UNIQUE,
  hashtag VARCHAR(20) NOT NULL UNIQUE,
  competition_start DATE NOT NULL,
  competition_end DATE NOT NULL,
  status VARCHAR(20) NOT NULL UNIQUE,
  location VARCHAR(250) NOT NULL,
  description VARCHAR(10000) NOT NULL
);

CREATE TABLE offline_competition_fields (
  offline_competition_id BIGINT NOT NULL REFERENCES offline_competitions(id) ON DELETE CASCADE,
  field VARCHAR(20) NOT NULL,
  PRIMARY KEY (offline_competition_id, field)
);