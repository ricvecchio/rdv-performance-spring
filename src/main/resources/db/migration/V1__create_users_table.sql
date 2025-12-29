-- Flyway migration: V1__create_users_table.sql
-- Creates the users table with enum-like check constraints, indexes and trigger to maintain updated_at

-- Enable uuid generation extension (uuid-ossp). If you prefer gen_random_uuid() from pgcrypto,
-- replace uuid_generate_v4() with gen_random_uuid() and enable pgcrypto instead.
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS public.users (
    id_user UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    user_type VARCHAR(20) NOT NULL,
    focus_area VARCHAR(30),
    status VARCHAR(20) NOT NULL,
    plan_type VARCHAR(20) NOT NULL,
    cref VARCHAR(50),
    bio VARCHAR(200),
    gym_name VARCHAR(100),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now()
);

-- Enum-like check constraints to keep values aligned with application enums
ALTER TABLE public.users
    ADD CONSTRAINT users_user_type_check CHECK (user_type IN ('TRAINER','STUDENT')),
    ADD CONSTRAINT users_focus_area_check CHECK (focus_area IS NULL OR focus_area IN ('CROSSFIT','GYM','HOME','FUNCTIONAL','OTHER')),
    ADD CONSTRAINT users_status_check CHECK (status IN ('ACTIVE','INACTIVE','CANCELED','EXPIRED')),
    ADD CONSTRAINT users_plan_type_check CHECK (plan_type IN ('FREE','BRONZE','SILVER','GOLD'));

-- Indexes for common lookups
CREATE INDEX IF NOT EXISTS idx_users_email_lower ON public.users (LOWER(email));
CREATE INDEX IF NOT EXISTS idx_users_name_lower ON public.users (LOWER(name));
CREATE INDEX IF NOT EXISTS idx_users_user_type ON public.users (user_type);

-- Trigger function to update updated_at on row changes
CREATE OR REPLACE FUNCTION public.trigger_set_timestamp()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = now();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS set_timestamp ON public.users;
CREATE TRIGGER set_timestamp
BEFORE UPDATE ON public.users
FOR EACH ROW
EXECUTE FUNCTION public.trigger_set_timestamp();

-- Optional: insert a sample admin or test user (commented out)
-- INSERT INTO public.users (email, name, password_hash, user_type, status, plan_type)
-- VALUES ('admin@example.com', 'Admin', '<bcrypt-hash-here>', 'TRAINER', 'ACTIVE', 'GOLD');

-- End of migration

