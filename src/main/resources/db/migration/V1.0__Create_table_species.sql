create table if not exists species(
    id_species serial,
    species_name varchar(50) not null,
    natural_range varchar(20) not null,
    average_size int not null,
    constraint pk_species primary key(id_species)
);