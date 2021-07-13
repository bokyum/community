create table keyword (
    keyword_id bigint not null,
    word varchar(255),
    primary key (keyword_id)
)

create table user (
    member_id bigint not null,
    authority varchar(255),
    create_at timestamp,
    email varchar(30),
    last_login_at timestamp,
    login_count integer,
    password varchar(50),
    user_name varchar(20),
    primary key (member_id)
)

create table post (
    post_id bigint not null,
    created_at timestamp,
    title varchar(255),
    member_member_id bigint,
    primary key (post_id)
    add constraint fk_post_to_member key (member_member_id) references user
)

create table post_review (
    post_review_id bigint not null,
    comment varchar(255),
    last_comment_at varchar(255),
    num_of_dislike integer,
    num_of_like integer,
    member_member_id bigint,
    post_post_id bigint,
    primary key (post_review_id)
    add constraint fk_post_review_to_member foreign key (member_member_id) references user
    add constraint fk_post_review_to_post foreign key (post_post_id) references post
)

create table tags (
    tag_id bigint not null,
    keyword_id bigint,
    post_id bigint,
    primary key (tag_id)
    add constraint tags_to_keyword foreign key (keyword_id) references keyword
    add constraint tags_to_post foreign key (post_id) references post
)





