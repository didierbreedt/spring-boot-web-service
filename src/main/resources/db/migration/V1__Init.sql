CREATE TABLE user (
    id int auto_increment,
    username varchar(128) not null,
    f_name varchar(128) not null,
    l_name varchar(128) not null,
    created_at timestamp not null,
    modified_at timestamp not null,
    primary key (id),
    unique key unq_username (username)
);

CREATE type USER_TASK_STATUS as enum('PENDING', 'PROCESSING', 'DONE', 'FAILED');
CREATE TABLE user_task (
    id int auto_increment,
    name varchar(128) not null,
    user_id int not null,
    status USER_TASK_STATUS,
    process_at timestamp not null,
    process_started_at timestamp,
    process_completed_at timestamp,
    description varchar(255),
    created_at timestamp not null,
    modified_at timestamp not null,
    primary key (id)
)