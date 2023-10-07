-- 테스트 계정 생성
insert into service.user (email, password, user_profile_id) values ('test@gmail.com', '123456', 1);

insert into service.user_profile
(user_profile_id, username, birthdate, gender, sido, sigungu, mbti, prefer_mbti, location, user_company_id)
values (1, 'test', '1990-01-01', 'M', '서울', '강남구', 'INTJ', 'ENFP', point(37.496486063, 127.028361548), 1);

insert into service.user_company (user_company_id, name, domain, icon_url) values (1, 'google', 'gmail.com', 'https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png');

insert into email_verification (email, verification_number, device_id) values ('test@test.com', '1234', 'test_device_id');
insert into email_verification (email, verification_number, device_id) values ('test2@test.com', '1234', 'test_device_id');