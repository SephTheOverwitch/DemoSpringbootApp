ALTER TABLE "supervises" ADD FOREIGN KEY ("employee") REFERENCES "employee" ("employee_id");

ALTER TABLE "supervises" ADD FOREIGN KEY ("supervisor") REFERENCES "employee" ("employee_id");