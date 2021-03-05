create database ECOMMERCE;
drop database ECOMMERCE;

create table UTENTE (
id int primary key not null auto_increment,
nome varchar(20) not null,
cognome varchar(20) not null,
username varchar(20) not null unique,
`password` varchar(20) not null,
email varchar(30) not null,
citta varchar(20) not null,
strada varchar(20) not null, 
cap int not null
);

delete from utente where id=9;
update utente set permessi=true where id=1;
select * from utente;
alter table utente ADD permessi boolean default false;
drop table utente;
insert into UTENTE (nome, cognome, username, `password`, email,  citta, strada, cap )values ("admin","admin", "administrator", "administrator",  "admin@mail.it", 
					"city","street",12345 );
insert into UTENTE (nome, cognome, username, `password`, email, citta, strada, cap )values ("alfaN","alfaC", "alfa", "alfa",  "arivatest@virgilio.it",
					"city","street",12345 ); 
 insert into UTENTE (nome, cognome, username, `password`, email, citta, strada, cap )values ("betaN","betaC", "beta", "beta",  "arivatest@virgilio.it",
					"city","street",12345 );
                    
select * from utente;

create table ARTICOLO(
id int primary key not null auto_increment,
nome varchar(20) not null,
marca varchar(20) not null,
tipo varchar(20) not null,
prezzo float,
img varchar(20),
unique(marca,nome)
);

update articolo set tipo="casalinghi" where id=6;

drop table articolo;

insert into articolo (nome, marca,tipo, prezzo) values ("biro", "marca1","cancelleria",2.0), ("matita", "marca1","cancelleria", 1.0), 
					("gomma","marca1","cancelleria", 0.5), ("mouse","marca2","elettronica", 15), ("penna usb","marca3","elettronica", 8);

select * from articolo;
select count(*) from articolo where tipo='elettronica';

select * from articolo, magazzino where articolo.id=magazzino.id;

delete from articolo where id=8;

select* from articolo as a join magazzino as m on a.id=m.id where a.id=5; 
delete a,m from articolo as a join magazzino as m on a.id=m.id where a.id=8;
delete from magazzino where id=5;
delete from articolo where id=5; 

create table MAGAZZINO(
id int primary key not null,
pezzi int,
foreign key (id) references ARTICOLO(id)
);
drop table magazzino;

insert into MAGAZZINO (id, pezzi) values(1,12),(2,10),(3,15),(4,22);
insert into magazzino (id, pezzi) values (8,55);
select * from magazzino, articolo where articolo.id=magazzino.id;
select * from magazzino;
select count(*) from magazzino; 

create table ORDINE(
id int not null primary key auto_increment,
u_id int not null,
a_id int not null,
o_id int null,
pezzi int,
`data` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
foreign key (u_id) references UTENTE(id),
foreign key (a_id) references ARTICOLO(id),
foreign key (o_id) references ORDINE_FIN(id)
);

drop table ordine;
select * from ordine;
delete from ordine where id=5;

insert into ordine (u_id,a_id,pezzi) values (2,1,2), (2,2,3),(2,3,4);
insert into ordine (u_id,a_id,pezzi) values (2,1,2), (2,2,3),(2,3,4);

select * from utente, ordine, articolo where utente.id=ordine.u_id and articolo.id=ordine.a_id;

create table METODO_PAGAMENTO (
id int not null primary key auto_increment,
u_id int not null,
tipo varchar(20) not null,
codice int not null,
unique(tipo,codice),
foreign key (u_id) references UTENTE(id)
);
drop table metodo_pagamento;
update metodo_pagamento set tipo="carta di credito" where id=1;
insert into metodo_pagamento (u_id,tipo,codice) values (2,"carta di credito", 321321);
insert into metodo_pagamento (u_id,tipo,codice) values (2,"bonifico", 22233344);
select * from metodo_pagamento;
select * from metodo_pagamento, utente where utente.id=metodo_pagamento.u_id;

create table ORDINE_FIN(
id int not null primary key auto_increment,
u_id int not null,
m_id int not null,
totale float not null,
`data` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

drop table ordine_fin;
select * from ordine_fin;