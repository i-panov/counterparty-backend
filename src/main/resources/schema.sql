create table if not exists legal_entity (
  id int primary key auto_increment,
  name varchar(50) not null,
  inn varchar(50) not null
);

create table if not exists person (
  id int primary key auto_increment,
  name varchar(50) not null,
  phone varchar(12),
  email varchar(50)
);

create table if not exists counterparty (
  id int primary key auto_increment,
  legal_entity_id int not null,
  foreign key(legal_entity_id) references legal_entity(id),
  person_id int not null,
  foreign key(person_id) references person(id),
  bailee_id int not null,
  foreign key(bailee_id) references person(id)
);

create table if not exists counterparty_person (
  counterparty_id int not null,
  foreign key(counterparty_id) references counterparty(id),
  person_id int not null,
  foreign key(person_id) references person(id)
);

