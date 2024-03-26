CREATE TABLE text_file_info (
    id VARCHAR(255) PRIMARY KEY,
    original_bucket_key VARCHAR(255),
    transformed_bucket_key VARCHAR(255),
    creation_datetime TIMESTAMP WITH TIME ZONE  DEFAULT CURRENT_TIMESTAMP NOT NULL,
    update_datetime TIMESTAMP WITH TIME ZONE  DEFAULT CURRENT_TIMESTAMP NOT NULL
);
