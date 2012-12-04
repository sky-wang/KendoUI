#!/bin/sh
#The username of postgres super user
POSTGRES_USER=sincej

sudo su $POSTGRES_USER << EOF

	psql postgres

    --
    -- Drop all database Only used for development
    --

    drop database if exists kendo;

	--
    -- Only used for development
    --
    revoke all PRIVILEGES on tablespace pg_default from kendo;
    drop user if exists kendo;
    create user kendo with password 'kendo';
    grant all on tablespace pg_default to kendo;
    alter user kendo createdb;

EOF
