-- master-detail relationship
INSERT INTO master (name)
VALUES ('Departments');
INSERT INTO master (name)
VALUES ('Subjects');
INSERT INTO master (name)
VALUES ('Colors');
INSERT INTO master (name)
VALUES ('Cars');
INSERT INTO master (name)
VALUES ('Phones');
INSERT INTO master (name)
VALUES ('Green School');
INSERT INTO master (name)
VALUES ('Countries');
INSERT INTO master (name)
VALUES ('Cities');

INSERT INTO detail (name, master_id)
VALUES ('John Smith', 1);
INSERT INTO detail (name, master_id)
VALUES ('Andy Ness', 1);

INSERT INTO detail (name, master_id)
VALUES ('Algebra', 2);
INSERT INTO detail (name, master_id)
VALUES ('Geometry', 2);

INSERT INTO detail (name, master_id)
VALUES ('Red', 3);
INSERT INTO detail (name, master_id)
VALUES ('Green', 3);
INSERT INTO detail (name, master_id)
VALUES ('Blue', 3);

INSERT INTO detail (name, master_id)
VALUES ('BMW', 4);
INSERT INTO detail (name, master_id)
VALUES ('Mazda', 4);
INSERT INTO detail (name, master_id)
VALUES ('Audi', 4);
INSERT INTO detail (name, master_id)
VALUES ('Volvo', 4);

INSERT INTO detail (name, master_id)
VALUES ('IPhone', 5);
INSERT INTO detail (name, master_id)
VALUES ('Galaxy S8 Plus', 5);

INSERT INTO detail (name, master_id)
VALUES ('Mike White', 6);
INSERT INTO detail (name, master_id)
VALUES ('Robert Green', 6);

INSERT INTO detail (name, master_id)
VALUES ('Belarus', 7);
INSERT INTO detail (name, master_id)
VALUES ('Poland', 7);
INSERT INTO detail (name, master_id)
VALUES ('Germany', 7);

INSERT INTO detail (name, master_id)
VALUES ('Minsk', 8);
INSERT INTO detail (name, master_id)
VALUES ('Vitebsk', 8);

-- many-to-many both unidirectional and bidirectional association
INSERT INTO foo (name)
VALUES ('History of Arts');
INSERT INTO foo (name)
VALUES ('About All');
INSERT INTO foo (name)
VALUES ('Heroes');

INSERT INTO bar (name)
VALUES ('Mike Fox');
INSERT INTO bar (name)
VALUES ('Andy Ness');

INSERT INTO foo_bar (foo_id, bar_id)
VALUES (1, 1);
INSERT INTO foo_bar (foo_id, bar_id)
VALUES (2, 1);
INSERT INTO foo_bar (foo_id, bar_id)
VALUES (3, 2);

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

INSERT INTO country (name)
VALUES ('White');
INSERT INTO country (name)
VALUES ('Black');

INSERT INTO office (name)
VALUES ('Minsk');
INSERT INTO office (name, parent_id)
VALUES ('Vitebsk', 1);