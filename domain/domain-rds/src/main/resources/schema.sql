create table service.user
(
    user_id         bigint auto_increment
        primary key,
    email           varchar(255)                          not null,
    password        varchar(255)                          not null,
    status          varchar(20) default 'ACTIVE'          not null comment 'ACTIVE, CLOSED, SUSPENDED, SLEEP',
    user_profile_id bigint                                not null,
    created_at      timestamp   default CURRENT_TIMESTAMP not null,
    updated_at      timestamp   default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    closed_at       timestamp                             null,
    constraint user_uq_email
        unique (email)
);

create index user_ix_email
    on service.user (email);



create table service.user_profile
(
    user_profile_id bigint auto_increment
        primary key,
    username        varchar(255)                        not null,
    birthdate       date                                not null,
    gender          varchar(5)                          not null,
    sido            varchar(255)                        not null,
    sigungu         varchar(255)                        not null,
    mbti            char(4)                             not null comment 'ISTJ,ISFJ,INFJ,INTJ,ISTP,ISFP,INFP,INTP,ESTP,ESFP,ENFP,ENTP,ESTJ,ESFJ,ENFJ,ENTJ',
    prefer_mbti     char(4)                             not null comment '선호하는 mbti 상관없으면 O으로 처리',
    location        point                               not null,
    user_company_id int                                 not null,
    created_at      timestamp default CURRENT_TIMESTAMP not null,
    updated_at      timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    deleted_at      timestamp                           null,
    constraint user_profile_uq_username
        unique (username)
);

create index user_profile_ix_birthdate
    on service.user_profile (birthdate);

create spatial index user_profile_ix_location
    on service.user_profile (location);

create table service.company
(
    company_id int auto_increment
        primary key,
    name            varchar(255)                        not null comment '회사명',
    domain          varchar(255)                        not null comment '회사 도메인',
    icon_url        varchar(1000)                       null comment '회사 아이콘 URL',
    created_at      timestamp default CURRENT_TIMESTAMP not null,
    updated_at      timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    deleted_at      timestamp                           null,
    constraint company_uq_name_domain
        unique (name, domain)
);

create table service.email_verification
(
    email_verification_id bigint auto_increment
        primary key,
    email                 varchar(255)                         not null,
    verified              tinyint(1) default 0                 not null,
    verification_number   char(4)                              not null,
    device_id             varchar(255)                         not null,
    created_at            timestamp  default CURRENT_TIMESTAMP not null
);

create index email_verification_ix_email
    on service.email_verification (email asc);