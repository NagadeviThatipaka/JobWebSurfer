CREATE TABLE job_detail(
    id varchar(200) PRIMARY key,
    date_of_posting varchar(50),
    company varchar(50),
    title varchar(100),
    location varchar(100),
    title_location JSON,
    basic_details JSON,
    qualifications JSON,
    full_job_description JSON
);