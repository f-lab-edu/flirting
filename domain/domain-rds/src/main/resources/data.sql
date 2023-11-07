-- 테스트 계정 생성
insert into service.company (company_id, name, domain, icon_url) values (1, 'test', 'test.com', 'https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png');
INSERT INTO service.user (user_id, email, password, status, user_profile_id, created_at, updated_at, closed_at) VALUES (0, 'test@test.com', '$2a$10$9rCCqjULWX3JpaINd0uKL.Cu5DCajW8q65nFLOgESXhVyODks6uam', 'ACTIVE', 1, '2023-11-07 15:05:17', '2023-11-07 15:05:17', null);
INSERT INTO service.user_profile (user_profile_id, username, birthdate, gender, sido, sigungu, mbti, prefer_mbti, location, company_id, created_at, updated_at, deleted_at) VALUES (1, 'test', '1998-10-28', 'MALE', '서울특별시', '강남구', 'ISTJ', 'OOOO', 0x000000000101000000000000000000F03F0000000000000040, 1, '2023-11-07 15:05:17', '2023-11-07 15:05:17', null);
INSERT INTO service.user_profile (user_profile_id, username, birthdate, gender, sido, sigungu, mbti, prefer_mbti, location, company_id, created_at, updated_at, deleted_at) VALUES (1, 'test', '1998-10-28', 'MALE', '서울특별시', '강남구', 'ISTJ', 'OOOO', 0x000000000101000000000000000000F03F0000000000000040, 1, '2023-11-07 15:05:17', '2023-11-07 15:05:17', null);
INSERT INTO service.point_wallet (point_wallet_id, user_id, point, bonus_point, version, created_at, updated_at, deleted_at) VALUES (1, 1, 990, 0, 2, '2023-11-07 14:58:55', '2023-11-07 14:58:55', null);


insert into email_verification (email, verification_number, device_id) values ('test@test.com', '1234', 'test_device_id');
insert into email_verification (email, verification_number, device_id) values ('test2@test.com', '1234', 'test_device_id');