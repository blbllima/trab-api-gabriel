create table if not exists animals(
    id_animal serial,
    animal_name varchar(50) not null,
    description varchar(50) not null,
    species int not null,
    constraint fk_species foreign key (species) references species(id_species),
    constraint pk_animals primary key(id_animal)
);