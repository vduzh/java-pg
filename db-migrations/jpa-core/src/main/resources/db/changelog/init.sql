-- master-detail relationship
CREATE TABLE IF NOT EXISTS master
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS detail
(
    id        SERIAL PRIMARY KEY,
    name      VARCHAR(50) NOT NULL,
    master_id INTEGER     NOT NULL,
    FOREIGN KEY (master_id) REFERENCES master (id)
);

-- many-to-many relationship
CREATE TABLE IF NOT EXISTS foo
(
    id    SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS bar
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS foo_bar
(
    foo_id   INTEGER NOT NULL,
    bar_id INTEGER NOT NULL,
    CONSTRAINT pk_foo_bar PRIMARY KEY (foo_id, bar_id),
    FOREIGN KEY (foo_id) REFERENCES foo (id),
    FOREIGN KEY (bar_id) REFERENCES bar (id)
);

CREATE TABLE IF NOT EXISTS position
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(50)       NOT NULL,
    code VARCHAR(4) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS role
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS country
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS office
(
    id         SERIAL PRIMARY KEY,
    name       VARCHAR(50) NOT NULL,
    parent_id  INTEGER     NULL,
    manager_id INTEGER     NULL
);

CREATE TABLE IF NOT EXISTS employee
(
    id          BIGSERIAL PRIMARY KEY,
    status      CHAR(1)             not null,
    login       VARCHAR(50) UNIQUE  NOT NULL,
    first_name  VARCHAR(50)         NOT NULL,
    last_name   VARCHAR(50)         NOT NULL,
    birth_date  DATE                NOT NULL,
    email       VARCHAR(255) UNIQUE NOT NULL,
    start_date  DATE                NOT NULL,
    office_id   INTEGER             NOT NULL,
    position_id INTEGER             NOT NULL,
    manager_id  INTEGER             NULL
);

CREATE TABLE IF NOT EXISTS profile
(
    id          BIGSERIAL PRIMARY KEY,
    employee_id BIGINT UNIQUE,
    language    VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS employee_team
(
    employee_id BIGINT UNIQUE,
    team_id     INT UNIQUE
);

CREATE TABLE IF NOT EXISTS project
(
    id         SERIAL PRIMARY KEY,
    name       VARCHAR(50)       NOT NULL,
    code       VARCHAR(4) UNIQUE NOT NULL,
    start_date DATE              NOT NULL,
    end_date   DATE              NULL,
    manager_id INTEGER           NOT NULL
);

CREATE TABLE IF NOT EXISTS project_employee
(
    project_id  INTEGER NOT NULL,
    employee_id BIGINT  NOT NULL,
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

-- alter profile
ALTER TABLE profile
    ADD CONSTRAINT fk_employee_id
        FOREIGN KEY (employee_id) REFERENCES employee (id);

-- alter employee_team
ALTER TABLE employee_team
    ADD CONSTRAINT fk_employee_id
        FOREIGN KEY (employee_id) REFERENCES employee (id),
    ADD CONSTRAINT fk_skill_id
        FOREIGN KEY (team_id) REFERENCES country (id);

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
