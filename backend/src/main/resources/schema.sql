create table IF not exists PET(
    id INT NOT NULL,
    name varchar(50) NOT NULL,
    type varchar(50) NOT NULL,
    description varchar(300),
    PRIMARY KEY (id)
);

create table if not exists USR(
    id INT NOT NULL,
    username varchar(50) NOT NULL,
    password varchar(50) NOT NULL,
    email varchar(150) NOT NULL,
    PRIMARY KEY (id)
);

create table if not exists ADOPT(
    id INT NOT NULL,
    pet_id INT NOT NULL,
    user_id INT NOT NULL,
    creation_time timestamp NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (pet_id) REFERENCES PET,
    FOREIGN KEY (user_id) REFERENCES USR
);
