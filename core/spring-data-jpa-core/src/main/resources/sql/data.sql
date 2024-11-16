INSERT INTO role (name)
VALUES ('PM');
INSERT INTO role (name)
VALUES ('Developer');
INSERT INTO role (name)
VALUES ('QA');
INSERT INTO role (name)
VALUES ('DBA');
INSERT INTO role (name)
VALUES ('Administrator');
INSERT INTO role (name)
VALUES ('Analyst');
INSERT INTO role (name)
VALUES ('Product Owner');
INSERT INTO role (name)
VALUES ('Scrum Master');

INSERT INTO position (name, code)
VALUES ('Project Manager', 'PM');
INSERT INTO position (name, code)
VALUES ('Software Engineer', 'SE');
INSERT INTO position (name, code)
VALUES ('QA Engineer', 'QA');

INSERT INTO office (name)
VALUES ('Minsk');
INSERT INTO office (name, parent_id)
VALUES ('Vitebsk', 1);