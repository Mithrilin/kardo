CREATE TABLE offline_selections (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
  title VARCHAR(250) NOT NULL UNIQUE,
  selection_type VARCHAR(10) NOT NULL,
  competition_id BIGINT NOT NULL REFERENCES grand_competitions(id) ON DELETE CASCADE,
  selection_start DATE NOT NULL,
  selection_end DATE NOT NULL,
  status VARCHAR(20) NOT NULL,
  location VARCHAR(250) NOT NULL
);

CREATE TABLE offline_selection_fields (
  offline_selection_id BIGINT NOT NULL REFERENCES offline_selections(id) ON DELETE CASCADE,
  field VARCHAR(20) NOT NULL,
  PRIMARY KEY (offline_selection_id, field)
);