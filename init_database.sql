------------------------------------------------------------------------------------------------------------------------
-- Database
------------------------------------------------------------------------------------------------------------------------
DROP
DATABASE IF EXISTS provider;

CREATE
DATABASE provider;

------------------------------------------------------------------------------------------------------------------------
-- User
------------------------------------------------------------------------------------------------------------------------

DROP
USER IF EXISTS provider;

CREATE
USER provider WITH ENCRYPTED PASSWORD 'provider';

GRANT ALL PRIVILEGES ON DATABASE
provider TO provider;

ALTER DEFAULT PRIVILEGES IN SCHEMA public
GRANT ALL ON TABLES TO provider;