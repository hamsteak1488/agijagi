insert member(email, nickname, password)
values ('abcd123@naver.com', '다르다', '$2a$10$TaxwwF9QrH5NEqjo8LLQCealD5OsN8d3VCrYpc5nGDNfYg7P7/1zC');

insert child(name, nickname, gender, birthday, is_deleted)
    value('test', 'test', '남아', now(), 0);

insert member_child(member_id, child_id, authority)
    value (1, 1, 'WRITE');
