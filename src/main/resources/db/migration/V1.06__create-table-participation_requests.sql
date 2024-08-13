CREATE TABLE participation_requests (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
  creation_time TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  selection_id BIGINT NOT NULL REFERENCES offline_selections(id) ON DELETE CASCADE,
  requester_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
  status VARCHAR(20) NOT NULL
);

CREATE TABLE participation_request_fields (
  participation_request_id BIGINT NOT NULL REFERENCES participation_requests(id) ON DELETE CASCADE,
  field VARCHAR(20) NOT NULL,
  PRIMARY KEY (participation_request_id, field)
);