CREATE TABLE users(
  id IDENTITY,
  email VARCHAR(100) NOT NULL,
  first_name VARCHAR(100) NOT NULL,
  last_name VARCHAR(100) NOT NULL,
  password VARCHAR(255)NOT NULL,
  role VARCHAR(20) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE upload_files(
  id IDENTITY,
  file_name VARCHAR(100),
  upload_comment VARCHAR(255),
  file_type VARCHAR(100),
  file_size BIGINT,
  uploader BIGINT,
  PRIMARY KEY (id),
  FOREIGN KEY(uploader) REFERENCES users(id)
);
