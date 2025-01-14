--liquibase formatted sql

--changeset vduzh:20241121-1
CREATE TABLE artvacation.template
(
  id           SERIAL,
  name         VARCHAR(255) NOT NULL,
  display_name VARCHAR(255) NOT NULL,
  file_name    VARCHAR(255) NOT NULL,
  CONSTRAINT pk_template PRIMARY KEY (id)
);
--rollback DROP TABLE artvacation.template;

--changeset vduzh:20241121-2
grant all on table artvacation.template to artvacation_user;
--rollback REVOKE ALL ON TABLE artvacation.template FROM artvacation_user;

--changeset vduzh:20241121-3
grant select on table artvacation.template to artvacation_readonly;
--rollback REVOKE ALL ON TABLE artvacation.template FROM artvacation_readonly;

--changeset vduzh:20241121-4
grant select, insert, update, delete on table artvacation.template to integr;
--rollback REVOKE ALL ON TABLE artvacation.template FROM integr;

--changeset vduzh:20241121-5
CREATE TABLE artvacation.office_template
(
  id               SERIAL,
  office_id        INTEGER NOT NULL,
  vacation_type_id INTEGER NOT NULL,
  template_id      INTEGER NOT NULL,
  CONSTRAINT pk_office_template PRIMARY KEY (id)
);
--rollback DROP TABLE artvacation.office_template;

--changeset vduzh:20241121-6
create index idx_office_id on artvacation.office_template (office_id);
--rollback drop index artvacation.idx_office_id

--changeset vduzh:20241121-7
alter table artvacation.office_template
  add constraint fk_template foreign key (template_id)
    references artvacation.template (id);
--rollback ALTER TABLE artvacation.office_template DROP CONSTRAINT fk_template;

--changeset vduzh:20241121-8
alter table artvacation.office_template
  add constraint fk_vacation_type foreign key (vacation_type_id)
    references artvacation.vacation_type (vacation_type_id);
--rollback ALTER TABLE artvacation.office_template DROP CONSTRAINT fk_vacation_type;

--changeset vduzh:20241121-9
grant all on table artvacation.office_template to artvacation_user;
--rollback REVOKE ALL ON TABLE artvacation.office_template FROM artvacation_user;

--changeset vduzh:20241121-10
grant select on table artvacation.template to artvacation_readonly;
--rollback REVOKE ALL ON TABLE artvacation.office_template FROM artvacation_readonly;

--changeset vduzh:20241121-11
grant select, insert, update, delete on table artvacation.office_template to integr;
--rollback REVOKE ALL ON TABLE artvacation.office_template FROM integr;

--changeset vduzh:20241121-12
INSERT INTO artvacation.template (name, display_name, file_name)
VALUES
  -- Оплачиваемый отпуск
  ('Заявление на отпуск ООО "Артезио-Саратов"', 'Шаблон заявления ООО "Артезио-Саратов"',
   'artezio_saratov_paid_vac_app.pdf'),
  -- Отпуск за свой счёт
  ('Заявление на отпуск без сохранения заработной платы ООО "Артезио-Саратов"',
   'Шаблон заявления ООО "Артезио-Саратов"',
   'artezio_saratov_no_paid_vac_app.pdf'),
  ('Заявление на отпуск без сохранения заработной платы "ООО ЦБИТ"', 'Шаблон заявления ООО "ЦБИТ"',
   'cbit_russia_no_paid_vac_app.pdf'),
  ('Заявление на отпуск без сохранения заработной платы "ООО Артезио"', 'Шаблон заявления ООО "АРТЕЗИО"',
   'artezio_no_paid_vac_app.pdf'),
  -- Донорство
  ('Заявление на доп отпуск за донорские дни ООО "Артезио-Саратов"', 'Шаблон заявления ООО "Артезио-Саратов"',
   'artezio_saratov_donorship_vac_app.pdf'),
  ('Заявление на доп отпуск за донорские дни "ООО ЦБИТ"', 'Шаблон заявления ООО "ЦБИТ"',
   'cbit_russia_donorship_vac_app.pdf'),
  ('Заявление на доп отпуск за донорские дни "ООО Артезио"', 'Шаблон заявления ООО "АРТЕЗИО"',
   'artezio_donorship_vac_app.pdf');
--rollback delete from artvacation.template;

--changeset vduzh:20241121-13
INSERT INTO artvacation.office_template(office_id, vacation_type_id, template_id)
VALUES
  -- Оплачиваемый отпуск: SARATOV
  (2, 1, 1),
  -- Отпуск за свой счёт ЦБИТ: SARATOV
  (2, 3, 2),
  -- Отпуск за свой счёт ЦБИТ: MOSCOW, NIZHNY NOVGOROD, SPB и  REMOTE_RUSSIA
  (1, 3, 3),
  (3, 3, 3),
  (12, 3, 3),
  (8, 3, 3),
  -- Отпуск за свой счёт АРТЕЗИО: MOSCOW, NIZHNY NOVGOROD, SPB и  REMOTE_RUSSIA
  (1, 3, 4),
  (3, 3, 4),
  (12, 3, 4),
  (8, 3, 4),
  -- Отпуск за свой счёт ЦБИТ: SARATOV
  (2, 4, 5),
  -- Отпуск за свой счёт ЦБИТ: MOSCOW, NIZHNY NOVGOROD, SPB и  REMOTE_RUSSIA
  (1, 4, 6),
  (3, 4, 6),
  (12, 4, 6),
  (8, 4, 6),
  -- Отпуск за свой счёт АРТЕЗИО: SARATOV, MOSCOW, NIZHNY NOVGOROD, SPB и  REMOTE_RUSSIA
  (1, 4, 7),
  (3, 4, 7),
  (12, 4, 7),
  (8, 4, 7);
--rollback delete from artvacation.office_template;
