# CityApplicationTestTask

Before launching the application you should:
  1. run sql-script:
    create database app_database;
    create user custom_user;
    alter user rmn_mobile with encrypted password 'user';
    alter database app_database owner to custom_user;
  2. Specify your own absolute path to project directory in file "V0.4__init_after_migrate.sql"
  3. Run the application.

To get access to enpoints you should authorize. There are two roles in app:
  1. User (username:"user", password:"user")
  2. Admin (username:"admin", password:"admin")
  
After authorization you will get tolen. Provide it with all your future requests.

Endpoints:
  1. api/v1/auth - Authorization.
  2. api/v1/city/{cityName} - get city by provided name.
  3. api/v1/city/editing - edit city. Should provide with RequestBody(id, name, photo).
  2. api/v1/getCities/{page} - get selected page of cities. 
  
