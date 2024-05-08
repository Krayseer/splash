CREATE DATABASE "configuration-db";
CREATE DATABASE "order-db";
CREATE DATABASE "service-db";

GRANT ALL PRIVILEGES ON DATABASE "configuration-db" TO postgres;
GRANT ALL PRIVILEGES ON DATABASE "order-db" TO postgres;
GRANT ALL PRIVILEGES ON DATABASE "service-db" TO postgres;