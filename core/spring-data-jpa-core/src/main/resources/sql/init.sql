CREATE TABLE IF NOT EXISTS position
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS role
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS office
(
    id         SERIAL PRIMARY KEY,
    name       VARCHAR(50) NOT NULL,
    parent_id  INTEGER     NOT NULL,
    manager_id INTEGER     NOT NULL
);

CREATE TABLE IF NOT EXISTS employee
(
    id          SERIAL PRIMARY KEY,
    status      CHAR(1)      not null,
    login       VARCHAR(50)  NOT NULL,
    first_name  VARCHAR(50)  NOT NULL,
    last_name   VARCHAR(50)  NOT NULL,
    email       VARCHAR(255) NOT NULL,
    start_date  DATE         NOT NULL,
    office_id   INTEGER      NOT NULL,
    position_id INTEGER      NOT NULL,
    manager_id  INTEGER      NOT NULL
);

CREATE TABLE IF NOT EXISTS project
(
    id         SERIAL PRIMARY KEY,
    name       VARCHAR(50) NOT NULL,
    start_date DATE        NOT NULL,
    end_date   DATE        NULL,
    manager_id INTEGER     NOT NULL
);

CREATE TABLE IF NOT EXISTS project_employee
(
    project_id  INTEGER NOT NULL,
    employee_id INTEGER NOT NULL,
    role_id     INTEGER NOT NULL,
    start_date  DATE    NOT NULL,
    end_date    DATE    NULL,
    CONSTRAINT pk_project_employee PRIMARY KEY (project_id, employee_id)
);

-- alter office
ALTER TABLE office
    ADD CONSTRAINT fk_parent_id
        FOREIGN KEY (parent_id) REFERENCES office (id),
    ADD CONSTRAINT fk_manager_id
        FOREIGN KEY (manager_id) REFERENCES employee (id);

-- alter employee
ALTER TABLE employee
    ADD CONSTRAINT fk_office_id
        FOREIGN KEY (office_id) REFERENCES office (id),
    ADD CONSTRAINT fk_position_id
        FOREIGN KEY (position_id) REFERENCES position (id),
    ADD CONSTRAINT fk_manager_id
        FOREIGN KEY (manager_id) REFERENCES employee (id);

-- alter project
ALTER TABLE project
    ADD CONSTRAINT fk_manager_id
        FOREIGN KEY (manager_id) REFERENCES employee (id);

-- alter project_employee
ALTER TABLE project_employee
    ADD CONSTRAINT fk_project_id
        FOREIGN KEY (project_id) REFERENCES project (id),
    ADD CONSTRAINT fk_employee_id
        FOREIGN KEY (employee_id) REFERENCES employee (id),
    ADD CONSTRAINT fk_role_id
        FOREIGN KEY (role_id) REFERENCES role (id);
