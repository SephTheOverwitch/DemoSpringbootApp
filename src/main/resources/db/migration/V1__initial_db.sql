CREATE TABLE "employee" (
  "employee_id" integer PRIMARY KEY,
  "name" varchar
);

CREATE TABLE "supervises" (
  "id" integer PRIMARY KEY,
  "employee" integer,
  "supervisor" integer
);

CREATE TABLE "app_user" (
  "id" integer PRIMARY KEY,
  "username" text
);
