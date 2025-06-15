drop table if exists authors;
drop table if exists books;

create table authors (
    id bigint not null default nextval('authors_id_seq'),
    name text,
    age integer,
    constraint authors_pkey primary key (id)
)

create table books (
    isbn text not null,
    title text,
    author_id bigint,
    constraint books_pkey primary key (isbn),
    constraint books_author_id_fkey foreign key (author_id) references authors (id)
);